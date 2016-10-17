# Twitter Data Streamer
Scala app to stream bitcoin data from twitter using twitter4j library to a postgresql database

### Requirements
* [Scala](http://www.scala-lang.org/)
* [PostgreSQL](https://www.postgresql.org/)
* [sbt](http://www.scala-sbt.org/)

### Build
Uses SBT and can be run with 
```bash
sbt run
```
To build a .jar file use
```bash
sbt assembly
```

### How to use
First argument is a file of the form:   

```json
{
  "url": "localhost",
  "port": "5432",
  "user": "postgres",
  "password": "postgres"
}
```
This file configures the connection to PostgreSQL

Second argument is a file of the form:   

```json
{
  "consumerKey": "key",
  "consumerSecret": "secret",
  "accessToken": "token",
  "accessTokenSecret": "tokensecret"
}
```
This file contains twitter credentials for the [twitter stream api](https://dev.twitter.com/)
