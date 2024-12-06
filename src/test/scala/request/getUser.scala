package request

import io.gatling.core.structure.ChainBuilder
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
 * @author Abhishek Kadavil
 */
object getUser {

  def getUserDetailsWithSession: ChainBuilder =
    exec(
      http("getUserDetailsWithSession")
        .get("/users/${userId}")
        .check(status is 200)
        .check(jsonPath("$.name").is("Tenali Ramakrishna"))
    )

  def getUserDetailsWithParam(userId: String): ChainBuilder =
    exec(
      http("getUserDetailsWithParam")
        .get(s"/users/$userId")
        .check(status is 200)
    )

}
