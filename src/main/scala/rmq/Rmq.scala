package rmq

import java.util.concurrent.TimeUnit

import akka.actor.{ActorRef, ActorSystem}
import akka.pattern.ask
import akka.util.Timeout
//import com.github.sstone.amqp.Amqp._
//import com.github.sstone.amqp.RpcClient.{Response, Request}
//import com.github.sstone.amqp.RpcServer.{ProcessResult, IProcessor}
//import com.github.sstone.amqp._
import com.rabbitmq.client.Connection
import com.rabbitmq.client.Channel
import com.rabbitmq.client.ConnectionFactory
import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._
import scala.util.{Try, Failure, Success}
import PdfBuilder._


object Producer {
//  implicit val system = ActorSystem("mySystem")
//  implicit val ec = ExecutionContext.global

  // create an AMQP connection
  val connFactory = new ConnectionFactory()
  connFactory.setHost("localhost")
  val connection = connFactory.newConnection()
  val channel = connection.createChannel()

  val queueName = "my_queue"
//  val connFactory = new ConnectionFactory()
//  connFactory.setUri("amqp://guest:guest@localhost/%2F")
//  val conn = system.actorOf(ConnectionOwner.props(connFactory, 1 second))
//  val producer = ConnectionOwner.createChildActor(conn, ChannelOwner.props())

  channel.queueDeclare(queueName, false, false, false, null)

  // wait till everyone is actually connected to the broker
//  waitForConnection(system, conn, producer).await(5, TimeUnit.SECONDS)

  def sendMessage(message: Array[Byte]) = {
    channel.basicPublish("", queueName, null, message)
    println("Sent " + message)
  }
//    Future(producer ! Publish("amq.direct", "my_key", message, properties = None, mandatory = true, immediate = false))

  // give it some time before shutting everything down
//  Thread.sleep(500)
//  system.shutdown()
}

//object Consumer1 {
//
//  def test = println("yo")
//  implicit val system = ActorSystem("mySystem")
//  implicit val ec = ExecutionContext.global
//
//  // create an AMQP connection
//  val connFactory = new ConnectionFactory()
//  connFactory.setUri("amqp://guest:guest@localhost/%2F")
//  val conn = system.actorOf(ConnectionOwner.props(connFactory, 1 second))
//  val queueParams = QueueParameters("my_queue", passive = false, durable = false, exclusive = false, autodelete = true)

//   create an actor that will receive AMQP deliveries
//  val listener: ActorRef = system.actorOf(Props(new Actor {
//    def receive = {
//      case Delivery(consumerTag, envelope, properties, body) => {
//        println("got a message: " + new String(body))
//        Future(body)
//      }
//    }
//  }))

//  val consumer = ConnectionOwner.createChildActor(conn, Consumer.props(listener, channelParams = None, autoack = false))

//
//  val processor = new IProcessor {
//    def process(delivery: Delivery) = {
//
//      val message: String = new String(delivery.body, "UTF-8")
//
//      println("Received " + message)
//      val t = Try {
//        PdfBuilder.createPdf(message)
//      }
//
//
//
//
//      // assume that the message body is a string
////      val response = "response to " + new String(delivery.body, "UTF-8")
////      Future(ProcessResult(Some(response.getBytes)))
////    }
////    def onFailure(delivery: Delivery, e: Throwable) = ProcessResult(None) // we don't return anything
//  }
//
//  val consumer: ActorRef = ConnectionOwner.createChildActor(conn, RpcServer.props(queueParams, StandardExchanges.amqDirect,  "my_key", processor, ChannelParameters(qos = 1)))
//
//
//  // wait till everyone is actually connected to the broker
//  Amqp.waitForConnection(system, consumer).await()
//
//  // create a queue, bind it to a routing key and consume from it
//  // here we don't wrap our requests inside a Record message, so they won't replayed when if the connection to
//  // the broker is lost: queue and binding will be gone
//
//  // create a queue
//  consumer ! DeclareQueue(queueParams)
//  // bind it
//  consumer ! QueueBind(queue = "my_queue", exchange = "amq.direct", routing_key = "my_key")
//  // tell our consumer to consume from it
//  consumer ! AddQueue(QueueParameters(name = "my_queue", passive = false))
//
////  val rpcClient: ActorRef = ConnectionOwner.createChildActor(conn, RpcClient.props())
////
////  implicit val timeout: Timeout = 2 seconds
//////
//  def receive = {
////    println("receiving...")
////    val request: Array[Byte] = ("request " + i).getBytes
//////
////    val f: Future[Response] = (consumer ? Request(List(Publish("amq.direct", "my_key", request)), 3)).mapTo[RpcClient.Response]
////    f.onComplete {
////      case Success(response) => {
////        response.deliveries.foreach(delivery => println("message received: " + new String(delivery.body, "UTF-8")))
////      }
////      case Failure(error) => println(error)
////    }
////  }
//
//
//
//  // run the Producer sample now and see what happens
//  println("Waiting for messages. Press enter to exit...")
//
//  System.in.read()
//  system.shutdown()
//}


