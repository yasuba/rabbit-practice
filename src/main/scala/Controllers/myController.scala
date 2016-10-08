package Controllers

import com.google.inject.Inject
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.http.{HttpServer, Controller}
import com.twitter.finatra.request.{QueryParam, RouteParam}
import java.nio.file.{ Paths, Files }

import rmq.Producer


case class HelloRequest(@RouteParam platform: String, @QueryParam feature: Option[String])

case class Foo(name: String)

class MyController @Inject() extends Controller {
  get("/") { request: Request =>
    response.ok.view("homepage.mustache", Foo("Bob"))
  }

  post("/pdf-me") { request: Request =>
    val firstName: String = request.params("firstname")
    val lastName = request.params("lastname")

    val examples = Paths.get("./example")
    val `test.pdf` = examples resolve "test.pdf"

    val pdf: Array[Byte] =
      (firstName + lastName).getBytes

    println("publishing message")
    Producer.sendMessage(pdf)
    Files write (`test.pdf`, pdf)

  }

  get("/ping") { request: Request =>
    "pong"
  }

  get("/platform/:platform") { request: HelloRequest =>
     "You got " + request.platform
  }


}

object ServerMain extends Server

class Server extends HttpServer {

//  override val defaultFinatraHttpPort: String = ":8080"

  override def configureHttp(router: HttpRouter): Unit = {
    router.add[MyController]
  }
}
