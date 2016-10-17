package app
import adapters.postgres._
import adapters.twitter._
import scala.io.Source
import spray.json._
import scala.util.parsing.json._

object dataCollector {
  def main(args: Array[String]): Unit = {
   
    val postgresConfigFilePath = args(0)
    val postgresConfig = getConfig(postgresConfigFilePath)

    postgres.connect(
      postgresConfig("url"),
      postgresConfig("port"),
      postgresConfig("user"),
      postgresConfig("password")
    )

    postgres.createTweetsTable 

    val twitterCredsFilePath = args(1)

    val twitterCreds = getConfig(twitterCredsFilePath)
    
    val config = twitterStreamer.createConfig(
      twitterCreds("consumerKey"),
      twitterCreds("consumerSecret"),
      twitterCreds("accessToken"),
      twitterCreds("accessTokenSecret")
    )

    val keywords = Array("bitcoin")
    twitterStreamer.stream(keywords, config)
   
  }

  
  def getConfig(credsFile: String): Map[String, String] = {
    
    val credentialFile = Source.fromFile(credsFile)

    JSON.parseFull(credentialFile.mkString) match {
      case Some(creds: Map[String @unchecked, String @unchecked]) => creds
      case _ => Map("consumerKey" -> "N/A")
    }
    
  }
}
