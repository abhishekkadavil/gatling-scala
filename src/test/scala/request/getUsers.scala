package request

import io.gatling.core.structure.ChainBuilder
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
 * @author Abhishek Kadavil
 */
object getUsers {

  def getUsers: ChainBuilder =
    exec(
      http("getUsers")
        .get("/users")
        .check(status is 200)
    )

}
