package request

import io.gatling.core.structure.ChainBuilder
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
 * @author Abhishek Kadavil
 */
object deleteUser {

  def deleteUserWithSession(): ChainBuilder =
    exec(
      http("deleteUserWithSession")
        .get("/users/${userId}")
        .check(status is 200)
    )

  def deleteUserWithParam(userId: String): ChainBuilder =
    exec(
      http("deleteUserWithParam")
        .get(s"/users/$userId")
        .check(status is 200)
    )

}
