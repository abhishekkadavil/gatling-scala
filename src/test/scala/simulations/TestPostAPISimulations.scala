package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

/**
 * @author Abhishek Kadavil
 */
class TestPostAPISimulations extends Simulation{

  /**
   * Model: Multiple API simulation model
   * User count : 1
   */

  //http config
  val httpConf = http.baseUrl("https://reqres.in")
    .header("Accept", value = "application/json")
    .header("content-type", value = "application/json");

  //scenario
  val scn = scenario("add user")
    .exec(http("add user request")
      .post("/api/users")
      .body(RawFileBody("src/test/resources/testData/post/ReqBody.json")).asJson
      .check(status is 201))


    // Type 1 - Pause for a fixed duration - defaults to seconds
//    .pause(5)
    // Type 2 - Pause for a fixed duration, specify the time unit
//    .pause(4000.milliseconds)
    // Type 3 - Pause for a random time between two durations
//    .pause(1, 5)
    // Type 4 - Pause for a random time between two durations, specify the time unit
    .pause(1000.milliseconds, 7000.milliseconds)


    .exec(http("get user request")
      .get("/api/user/2")
      .check(status is 200));

  //setup
  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf);

}
