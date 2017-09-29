package com.example.server

import com.example.rabbitmq.Publisher.PublishResult
import com.example.routing.{HelloWorld, UiRoutes}
import org.http4s.HttpService
import org.slf4j.{Logger, LoggerFactory}

import scalaz.concurrent.Task

object PracticeService {
  val log: Logger = LoggerFactory.getLogger(getClass)

  def create(publishFunc: String => Task[PublishResult]): HttpService = {

    log.info("Creating practice service")

    HttpService {
      UiRoutes.routes orElse
      HelloWorld.routes(publishFunc)
    }
  }
}
