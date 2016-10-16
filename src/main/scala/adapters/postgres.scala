package adapters.postgres
import scalikejdbc._
import adapters.twitter._

object postgres {
  // initialize JDBC driver & connection pool
  Class.forName("org.postgresql.Driver")
  ConnectionPool.singleton("jdbc:postgresql://localhost:5432", "postgres", "postgres")
  implicit val session = AutoSession

  def createTweetsTable() {
    if (!tableExists("tweets")) {
      sql"""
        create table tweets (
          id serial not null primary key,
          tweet_id bigint,
          status text,
          created_at timestamp not null,
          user_id bigint,
          user_screen_name text,
          user_followers int,
          user_lang text
        )
      """.execute.apply()
    }
  }

  def tableExists(tableName: String): Boolean = {
    val table = sql"""
      SELECT EXISTS (
        SELECT 1 
        FROM   pg_catalog.pg_class c
        JOIN   pg_catalog.pg_namespace n ON n.oid = c.relnamespace
        WHERE  c.relname = ${tableName}
      );
    """.map(_.toMap).list.apply()
    // Take value of "exists" from results and cast to Boolean
    table(0)("exists") == true
  }

  def insertTweet(tweet: Tweet) = {
    // insert initial data
    sql"""
      insert into tweets (
        tweet_id, status, created_at, 
        user_id, user_screen_name, user_followers, user_lang) 
      values (
        ${tweet.id}, ${tweet.status}, ${tweet.createdAt},
        ${tweet.user.id}, ${tweet.user.screenName}, 
        ${tweet.user.followers}, ${tweet.user.lang})
      """.update.apply()
  }

  def viewData(tableName: String) = {
    // for now, retrieves all data as Map value
    val entities: List[Map[String, Any]] = sql"select * from ${tableName}".map(_.toMap).list.apply()
    entities map println
  } 
}
