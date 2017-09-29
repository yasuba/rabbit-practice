package com.example.routing

import java.io.{BufferedReader, InputStream, InputStreamReader}
import org.http4s.dsl._
import org.http4s.{MediaType, Request, Response}
import org.http4s.headers.`Content-Type`
import scalaz.concurrent.Task

object UiRoutes {
  val routes: PartialFunction[Request, Task[Response]] = {

    case _ @ GET -> Root / "ui" / "js" / file if isFileExtensionAllowed(file) =>
      val resourceStream = getClass.getResourceAsStream(s"/ui/js/$file")
      serveFile(resourceStream, MediaType.`application/javascript`)

    case _ @ GET -> Root / "ui" / "css" / file if isFileExtensionAllowed(file) =>
      val resourceStream = getClass.getResourceAsStream(s"/ui/css/$file")
      serveFile(resourceStream, MediaType.`text/css`)

    case _ @ GET -> Root / "ui" / _ =>
      val resourceStream = getClass.getResourceAsStream("/ui/index.html")
      serveFile(resourceStream, MediaType.`text/html`)
  }

  private def isFileExtensionAllowed(path: String) = path.endsWith(".html") || path.endsWith(".css") || path.endsWith(".js")

  private def serveFile(is: InputStream, contentType: MediaType) = {
    val reader = new BufferedReader(new InputStreamReader(is))
    Ok()
      .withBody(reader)
      .withContentType(Option(`Content-Type`(contentType)))
  }
}
