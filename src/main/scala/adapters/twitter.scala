package adapters.twitter
import twitter4j._
import java.io._
import adapters.postgres._

object twitterStreamer {
    
  def createConfig(
    consumerKey: String, consumerSecret: String,
    accessToken: String, accessTokenSecret: String): conf.Configuration = {
    new twitter4j.conf.ConfigurationBuilder()
        .setOAuthConsumerKey(consumerKey)
        .setOAuthConsumerSecret(consumerSecret)
        .setOAuthAccessToken(accessToken)
        .setOAuthAccessTokenSecret(accessTokenSecret)
        .setPrettyDebugEnabled(true)
        .build()
  }

  def postgresStatusListener() = new StatusListener() {
    def onStatus(status: Status) {
      val tweet = utils.wrapTwitter4jStatus(status)
      postgres.insertTweet(tweet)
    }
    def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}
    def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}
    def onException(ex: Exception) { ex.printStackTrace }
    def onScrubGeo(arg0: Long, arg1: Long) {}
    def onStallWarning(warning: StallWarning) {}
  }

  def stream(keywords: Array[String], config: conf.Configuration) = {
    val twitterStream = new TwitterStreamFactory(config).getInstance
    twitterStream.addListener(postgresStatusListener())
    twitterStream.filter(new FilterQuery().track(keywords))

  }
}

object utils {
  
  def wrapTwitter4jStatus(status: Status): Tweet = {
    new Tweet(status.getId, status.getText,
      status.getCreatedAt, 
      wrapTwitter4jUser(status.getUser()))
  }

  def wrapTwitter4jUser(user: User) : twitterUser = {
    new twitterUser(user.getId, user.getScreenName, 
      user.getFollowersCount, user.getLang)
  }
}

class twitterUser(
  val id: Long, 
  val screenName: String, 
  val followers: Int,
  val lang: String
)

class Tweet(
  val id: Long, 
  val status: String, 
  val createdAt: java.util.Date, 
  val user: twitterUser
)
