package simulations;

import io.gatling.core.scenario.Simulation;
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
 * @author Abhishek Kadavil
 */
class TestGetAPISimulations extends Simulation{

  /**
   * Model: Single API simulation model
   * User count : 1
   */

  //http config
  val httpConf = http.baseUrl("https://reqres.in")
    .header("Accept", value = "application/json")
    .header("content-type", value = "application/json");

  //scenario
  val scn = scenario("get user")
              .exec(http("get user request")
                  .get("/api/user/2")
                  .check(status is 200));

  //setup
  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf);

}