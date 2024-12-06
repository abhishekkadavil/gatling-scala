package feederPath

import io.gatling.core.Predef._

/**
 * @author Abhishek Kadavil
 */
object feeders {

    val chainingFeeder = csv("testData/chaining/getFeeder.csv")

}
