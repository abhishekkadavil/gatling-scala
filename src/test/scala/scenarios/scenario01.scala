package scenarios

import feederPath.feeders
import helpers.randomEmailGenerator
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.core.Predef._
import request._

/**
 * @author Abhishek Kadavil
 */
object scenario01 {

  def sc01() : ScenarioBuilder = scenario("scenario 1")
    .group("scenario 1"){
      feed(feeders.chainingFeeder.random)
        .exec(session =>{
          session.set("name",session("name").as[String])
          session.set("gender",session("gender").as[String])
          session.set("status",session("status").as[String])
        })
        .exec(session => {
          val email = randomEmailGenerator.generateEmail
          session.set("randomEmail", email)
        })
        .exec(createUser.createUserWithDynamicData("${name}","${gender}","${randomEmail}","${status}"))
    }

}
