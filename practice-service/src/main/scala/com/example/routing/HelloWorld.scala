package com.example.routing

import com.example.rabbitmq.Publisher.{PublishFailure, PublishResult, PublishSuccess}
import org.http4s._
import org.http4s.dsl._
import org.slf4j.{Logger, LoggerFactory}

import scalaz.concurrent.Task

object HelloWorld {
  protected val logger: Logger = LoggerFactory.getLogger(getClass)

  def routes(publishFunc: String => Task[PublishResult]): PartialFunction[Request, Task[Response]] = {
    case GET -> Root / "hello" => {
      logger.info("hello route")
      for {
        publishResult <- publishFunc("Hello??!!!")
        res <- handlePublishResult(publishResult)
      } yield res
    }
  }


  def handlePublishResult: PublishResult => Task[Response] = {
    case PublishSuccess =>
      Task.now(Response(status = Status.Ok))

    case PublishFailure =>
      Response(status = Status.InternalServerError)
        .withBody("Failed to publish the updated settings")
  }
}
