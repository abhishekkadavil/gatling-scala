package request

import io.gatling.core.structure.ChainBuilder
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
 * @author Abhishek Kadavil
 */
object createUser {

  def createUserWithDynamicData(name: String,gender: String,email: String,userStatus: String): ChainBuilder =
    exec(
      http("createUserWithDynamicData")
        .post("/users")
        .header("","")
        .body(StringBody(
          s"""
              {
                "name": "$name",
                "gender": "$gender",
                "email": "$email",
                "status": "$userStatus"
              }
            """)).asJson
        .check(status is 201)
        .check(jsonPath("$.id").find.optional.saveAs("userId"))
    )

  def createUserWithSpecificEmail(email: String): ChainBuilder =
    exec(
      http("createUserWithSpecificEmail")
        .post("/users")
        .header("","")
        .body(StringBody(
          s"""
              {
                "name": "Tenali Ramakrishna",
                "gender": "male",
                "email": "$email",
                "status": "active"
              }
            """)).asJson
        .check(status is 201)
        .check(jsonPath("$.id").saveAs("userId"))
    )

}
