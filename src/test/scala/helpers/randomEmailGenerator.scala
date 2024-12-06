package helpers

import scala.util.Random

/**
 * @author Abhishek Kadavil
 */
object randomEmailGenerator {

  private val random = new Random()
  private val domains = List("example.com", "test.com", "demo.com", "gmail.com", "yahoo.com")

  def generateEmail: String = {
    val username = (1 to 10).map(_ => ('a' + random.nextInt(26)).toChar).mkString
    s"$username@${domains(random.nextInt(domains.size))}"
  }

}
