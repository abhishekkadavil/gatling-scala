package actions

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder

/**
 * @author Abhishek Kadavil
 */
object updateUserFlow {

  def updateNameWithSpecificEmail(email: String, updateUserName: String): ChainBuilder = {
    group("updateNameWithSpecificEmail"){
      exec(request.createUser.createUserWithSpecificEmail(email))
      .exec(request.updateUser.updateName(updateUserName))
    }
  }

}
