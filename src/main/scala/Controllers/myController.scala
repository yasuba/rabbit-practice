package Controllers

import com.google.inject.Inject
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.http.{HttpServer, Controller}
import com.twitter.finatra.request.{QueryParam, RouteParam}

case class HelloRequest(@RouteParam platform: String, @QueryParam feature: Option[String])

class MyController @Inject() extends Controller {
  get("/") { request: Request =>
    "Hello World!"
  }

  get("/ping") { request: Request =>
    "pong"
  }

  get("/platform/:platform") { request: HelloRequest =>
     "You got " + request
  }


}

//object ServerMain extends Server

class Server extends HttpServer {

//  override val defaultFinatraHttpPort: String = ":8080"

  override def configureHttp(router: HttpRouter): Unit = {
    router.add[MyController]
  }
}
