package Controllers

import com.google.inject.Inject
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.http.{HttpServer, Controller}
import com.twitter.finatra.request.{QueryParam, RouteParam}
import java.nio.file.{ Paths, Files }

import rmq.{Consumer1, Producer}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}


case class HelloRequest(@RouteParam platform: String, @QueryParam feature: Option[String])

case class Foo(name: String)

class MyController @Inject() extends Controller {
  implicit val ec = ExecutionContext.global

  get("/") { request: Request =>
    response.ok.view("homepage.mustache", Foo("Bob"))
  }

  post("/pdf-me") { request: Request =>
    val firstName: String = request.params("firstname")
    val emailAddress = request.params("emailAddress")

    val examples = Paths.get("./example")
    val `test.pdf` = examples resolve "test.pdf"

    val pdf: Array[Byte] =
      (firstName + emailAddress).getBytes

    println("publishing message")
    val messageSent = Producer.sendMessage(pdf)
    messageSent.onComplete {
      case Success(response) => {
        println("message sent")
        Consumer1.test
      }
      case Failure(error) => println(error)
    }

//    Files write (`test.pdf`, pdf)
    response.ok.body("Your email will be sent shortly")
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
