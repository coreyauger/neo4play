package com.nxtwv.neo4play

import play.api.libs.json.{Json, JsValue}
import scala.concurrent.Future
import play.api.libs.ws.WS
import play.api.libs.concurrent.Execution.Implicits._

trait NeoService{
  def cypher(implicit s:Neo4JServer, query:String, params:Map[String, String]):Future[JsValue]
}

sealed class Neo4JServer(val host:String, val port:Int, val path:String){
  def url(part:String) = {
    "%s:%s%s/%s".format(host,port,path,part)
  }
}

object Neo4Play extends NeoService{

  def cypher(implicit s:Neo4JServer, query:String, params:Map[String, String]):Future[JsValue] ={
    WS.url(s.url("cypher")).post(
      Json.obj(
        "query" -> query,
        "params" -> params
      )
    ).map{
      res =>
        res.json
    }
  }

}
