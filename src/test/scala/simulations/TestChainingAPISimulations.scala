package simulations

import configs.config
import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

/**
 * @author Abhishek Kadavil
 */
class TestChainingAPISimulations extends Simulation {

  /**
   * Model: Chained API simulation model
   * User count : rampUp -> hold -> rampDown
   */

  setUp(

    /**
     * Feeder implementation
     */
    scenarios.scenario01.sc01().inject(
      rampConcurrentUsers(0) to (0) during (1 seconds),
      rampConcurrentUsers(0) to (1) during (1 seconds),
      constantConcurrentUsers(0) during (1 seconds),
      rampConcurrentUsers(1) to (0) during (1 seconds)
    ).protocols(config.httpConf),


    /**
     * Create and delete user scenario two APIs are chained
     */
    scenarios.scenario02.sc02().inject(
      atOnceUsers(1)
    ).protocols(config.httpConf),

    /**
     * Create and update user scenario two APIs are chained
     */
    scenarios.scenario03.sc03().inject(
      atOnceUsers(1)
    ).protocols(config.httpConf)
  )

}
