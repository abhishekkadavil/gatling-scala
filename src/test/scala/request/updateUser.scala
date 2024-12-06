package request

import io.gatling.core.structure.ChainBuilder
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
 * @author Abhishek Kadavil
 */
object updateUser {

  def updateName(name: String): ChainBuilder =
    exec(
      http("updateName")
        .patch("/users/${userId}")
        .header("","")
        .body(StringBody(
          s"""
              {
                "name": "$name"
              }
            """)).asJson
        .check(status is 200)
    )

}
