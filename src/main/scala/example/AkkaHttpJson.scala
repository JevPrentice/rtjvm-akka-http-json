package example

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import de.heikoseeberger.akkahttpjackson.JacksonSupport

import java.util.UUID

// Domain

case class Person(name: String, age: Int)

case class UserAdded(id: String, timestamp: Long)

// Spray
// step 1 import it

import spray.json._

// step 2 create implicit json conversions for json spray
trait PersonJsonProtocol extends DefaultJsonProtocol {
  implicit val personFormat: RootJsonFormat[Person] = jsonFormat2(Person)
  implicit val userAddedFormat: RootJsonFormat[UserAdded] = jsonFormat2(UserAdded)
}

// step 3 add implicit json converts into the scope of your route

/**
 *
 *
 * @author Jev Prentice
 * @since 02 November 2021
 */
object AkkaHttpJson extends PersonJsonProtocol with SprayJsonSupport {

  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "AkkaHttpJson")

  val route: Route = (path("api" / "user") & post) {
    entity(as[Person]) { person: Person =>
      complete(UserAdded(UUID.randomUUID().toString, System.currentTimeMillis()))
    }
  }

  def main(args: Array[String]): Unit = {
    Http().newServerAt("localhost", 8081).bind(route)
  }
}

// Circe
object AkkaHttpCirce extends FailFastCirceSupport {

  import io.circe.generic.auto._

  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "AkkaHttpCirce")

  val route: Route = (path("api" / "user") & post) {
    entity(as[Person]) { person: Person =>
      complete(UserAdded(UUID.randomUUID().toString, System.currentTimeMillis()))
    }
  }

  def main(args: Array[String]): Unit = {
    Http().newServerAt("localhost", 8082).bind(route)

    //echo '{"name": "asd", "age": 99}' | http post localhost:8081/api/user
  }
}

object AkkaHttpJackson extends JacksonSupport {

  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "AkkaHttpCirce")

  val route: Route = (path("api" / "user") & post) {
    entity(as[Person]) { person: Person =>
      complete(UserAdded(UUID.randomUUID().toString, System.currentTimeMillis()))
    }
  }

  def main(args: Array[String]): Unit = {
    Http().newServerAt("localhost", 8083).bind(route)
    //echo '{"name": "asd", "age": 99}' | http post localhost:8083/api/user
  }
}