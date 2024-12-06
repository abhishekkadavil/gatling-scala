package scenarios

import helpers.randomEmailGenerator._
import io.gatling.core.Predef._

/**
 * @author Abhishek Kadavil
 */
object scenario02 {

  /**
   * Create and delete user scenario
   */
  def sc02() = scenario("scenario 2")
    .group("scenario 2"){
      exec(actions.deleteUserFlow.deleteUserWithSpecificEmail(generateEmail))
    }

}
