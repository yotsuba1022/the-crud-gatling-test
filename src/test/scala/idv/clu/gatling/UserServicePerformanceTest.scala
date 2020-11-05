package idv.clu.gatling

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.scenario.Simulation
import scala.concurrent.duration._

class UserServicePerformanceTest extends Simulation {

  var userServiceHost = "http://localhost:"
  var userServicePort = "8080"
  var userServiceBaseUrl = userServiceHost + userServicePort
  var userServiceApiPath = "/the-crud/api/v1/users"

  var userServiceQueryParams = "?limit=3&offset=0"

  val httpProtocolConf = http.baseUrl(userServiceBaseUrl)

  val scn = scenario("User CRUD")
    .exec(
      http("Get user information")
      .get(userServiceApiPath)
      .queryParam("limit", 3)
      .queryParam("offset", 0).check(status is 200)
    )

  setUp(
    scn.inject(constantUsersPerSec(2).during(5 seconds)
    ).protocols(httpProtocolConf))

}