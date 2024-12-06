package scenarios

import feederPath.feeders
import helpers.randomEmailGenerator.generateEmail
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder

/**
 * @author Abhishek Kadavil
 */
object scenario03 {

  def sc03(): ScenarioBuilder = scenario("scenario 3")
    .group("scenario 3"){
      feed(feeders.chainingFeeder.circular)
        .exec(session =>{
          session.set("name",session("name").as[String])
        })
      .exec(actions.updateUserFlow.updateNameWithSpecificEmail(generateEmail,"${name}"))
    }

}
