package com.nxtwv.neo4play

import scala.concurrent.Future
import play.api.libs.ws.WS
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.{JsObject, JsValue, Json}

trait NeoService{
  def cypher(query:String, params:Map[String, String])(implicit s:Neo4JServer):Future[JsValue]
}

sealed class Neo4JServer(val host:String, val port:Int, val path:String){
  def url(part:String) = {
    "http://%s:%s%s%s".format(host,port,path,part)
  }
}

object Neo4Play extends NeoService{

  def cypher(query:String, params:Map[String, String])(implicit s:Neo4JServer):Future[JsValue] ={
    val json = Json.obj(
      "query" -> query,
      "params" -> params
    )
    println(s.url("cypher"))
    WS.url(s.url("cypher"))
      .withHeaders( ("Accept","application/json; charset=UTF-8"), ("Content-Type", "application/json") )
      .post(
        json
      ).map{
      res =>
        println("RESPONSE")
        res.json
    }
  }

  def neoJsonToDataObjectList(json:JsValue):List[JsObject] = {
    println("neoJsonToDataObjectList")
    //println(json)
    (json \ "data").apply(0).as[List[JsObject]]
  }
  def neoJsonToDataList(json:JsValue):List[List[String]] = {
    println("neoJsonToDataList")
    // println(json)
    (json \ "data").as[List[List[String]]]
  }

  def neoJsonToDataMap(json:JsValue):List[Map[String,String]] = {
    println("neoJsonToDataMap")
    //println(json)
    val data = (json \ "data").apply(0).as[List[JsObject]]
    data.map{
      jobj =>
        (jobj \ "data").as[JsObject].value.map{
          case (k,v) => (k,v.as[String])
        }.toMap
    }.toList
  }

}
