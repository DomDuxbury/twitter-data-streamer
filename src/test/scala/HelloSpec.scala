import org.scalatest._
//import dataCollector._

class HelloSpec extends FlatSpec with Matchers {
  "Hello" should "have tests" in {
    true should === (true)
  }
}
