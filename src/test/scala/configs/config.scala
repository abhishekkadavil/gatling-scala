package configs

import io.gatling.http.Predef._
import io.gatling.core.Predef._

/**
 * @author Abhishek Kadavil
 */
object config {

  val baseURLName = Option(System.getProperty("prodUrl")).isDefined match {
    case true => "https://gorest.co.in/public/v2"
    case false => "https://gorest.co.in/public/v2"
  }

  val httpConf = http.baseUrl(config.baseURLName)
    .header("Accept", value = "application/json")
    .header("content-type", value = "application/json")
    .header("Authorization","Bearer b0aef0021b39d875cfba3e9f192aac861c77a871c224d0ec76d467b43049061a")
    .disableWarmUp
    .disableCaching

}
