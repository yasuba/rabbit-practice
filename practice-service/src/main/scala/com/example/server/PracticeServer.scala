package com.example.server

import com.example.config.{Amqp, Settings}
import com.example.config.Settings.MessageBroker
import com.example.rabbitmq.Publisher
import com.itv.bucky.taskz.TaskAmqpClient
import org.http4s.HttpService
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.server.{Server, ServerApp}

import scalaz.concurrent.Task

object PracticeServer extends ServerApp {
  val amqp: Amqp = MessageBroker.amqp
  val amqpClient: TaskAmqpClient = Publisher.createAmqpClient(amqp)

  Publisher.createNotificationExchange(amqp, amqpClient)

  val services: HttpService = PracticeService.create(
    Publisher.createPublisher(amqp, amqpClient))

  override def server(args: List[String]): Task[Server] = {
    BlazeBuilder
      .bindHttp(8080, "localhost")
      .mountService(services, "/")
      .start
  }
}

