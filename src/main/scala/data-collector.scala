package app
import adapters.postgres._
import adapters.twitter._
import scala.io.Source
import spray.json._
import scala.util.parsing.json._

object dataCollector {
  def main(args: Array[String]): Unit = {
    
    postgres.createTweetsTable() 

    val credsFilePath = args(0)

    val creds = getCredentials(credsFilePath)
    
    val config = twitterStreamer.createConfig(
      creds("consumerKey"),
      creds("consumerSecret"),
      creds("accessToken"),
      creds("accessTokenSecret")
    )

    val keywords = Array("bitcoin")
    twitterStreamer.stream(keywords, config)
   
  }

  
  def getCredentials(credsFile: String): Map[String, String] = {
    
    val credentialFile = Source.fromFile(credsFile)

    JSON.parseFull(credentialFile.mkString) match {
      case Some(creds: Map[String @unchecked, String @unchecked]) => creds
      case _ => Map("consumerKey" -> "N/A")
    }
    
  }
}
