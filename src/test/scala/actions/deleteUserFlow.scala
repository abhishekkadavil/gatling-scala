package actions

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder

/**
 * @author Abhishek Kadavil
 */
object deleteUserFlow {

  def deleteUserWithSpecificEmail(email: String): ChainBuilder = {
    group("deleteUserWithSpecificEmail"){
      exec(request.createUser.createUserWithSpecificEmail(email))
      .exec(request.deleteUser.deleteUserWithParam("${userId}"))
    }
  }

  def deleteUserSpecificUser(user: String): ChainBuilder = {
    group("deleteUserWithSpecificEmail"){
      exec(request.deleteUser.deleteUserWithParam(user))
    }
  }

}
