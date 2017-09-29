package com.example.rabbitmq

import com.example.config.Amqp
import com.itv.bucky.PayloadMarshaller.StringPayloadMarshaller
import com.itv.bucky._
import com.itv.bucky.Monad.Id
import com.itv.bucky.decl.{DeclarationExecutor, Exchange, Fanout, Queue}
import com.itv.bucky.taskz.TaskAmqpClient
import io.circe.syntax._
import org.slf4j.LoggerFactory

import scalaz.concurrent.Task

object Publisher {

  private val log = LoggerFactory.getLogger(getClass)

  private val retries = List.tabulate(4)(i => (i * 1000 * 1.2).toInt)

  def createAmqpClient(amqpConfig: Amqp): TaskAmqpClient =
    TaskAmqpClient.fromConfig(AmqpClientConfig(amqpConfig.messageBroker.host, amqpConfig.messageBroker.port, amqpConfig.messageBroker.username, amqpConfig.messageBroker.password))

  def createNotificationExchange(amqpConfig: Amqp, amqpClient: TaskAmqpClient): Unit = {
    val declarations = List(
      Queue(QueueName(amqpConfig.queueName)),
      Exchange(ExchangeName(amqpConfig.exchangeName), Fanout)
        .binding(RoutingKey("") -> QueueName(amqpConfig.queueName))
    )

    DeclarationExecutor(declarations, amqpClient)
  }

  def createPublisher(amqpConfig: Amqp, amqpClient: TaskAmqpClient): String => Task[PublishResult] = {
    val publisherConfig = PublishCommandBuilder.publishCommandBuilder(StringPayloadMarshaller) using ExchangeName(amqpConfig.exchangeName) using RoutingKey("")

    val publisher = amqpClient.publisherOf(publisherConfig)

    publishWithRetries(publisher)
  }

  private[rabbitmq] def publishWithRetries(publisher: Id[Publisher[Task, String]]): String => Task[PublishResult] =
    string => {
      retry(retries)(publisher)(string.asJson.spaces2)
    }

  private def retry(backedOffRetries: Seq[Int])(publishFunc: Id[Publisher[Task, String]])(adReplacementSettings: String): Task[PublishResult] = {
    val future = publishFunc(adReplacementSettings)
      .map(_ => PublishSuccess)

    future handleWith {
      case t =>
        backedOffRetries match {
          case Nil =>
            log.warn(s"Retries exhausted publishing settings:\n$adReplacementSettings", t)
            Task.now(PublishFailure)

          case x :: xs =>
            Thread.sleep(x)
            retry(xs)(publishFunc)(adReplacementSettings)
        }
    }
  }

  sealed trait PublishResult

  case object PublishSuccess extends PublishResult

  case object PublishFailure extends PublishResult

}
