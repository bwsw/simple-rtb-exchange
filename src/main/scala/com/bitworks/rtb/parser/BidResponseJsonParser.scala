package com.bitworks.rtb.parser

import com.bitworks.rtb.model.response._
import com.bitworks.rtb.model.response.builder._
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}

import scala.collection.JavaConverters._

/**
  * JSON parser for [[com.bitworks.rtb.model.response.BidResponse BidResponse]].
  *
  * @author Pavel Tomskikh
  */
class BidResponseJsonParser extends BidResponseParser with JsonParseHelper {

  /**
    * Returns parsed JSON string to [[com.bitworks.rtb.model.response.BidResponse BidResponse]]
    * object without exception catching.
    *
    * @param bytes input bytes
    */
  override def parseInternal(bytes: Array[Byte]): BidResponse = {
    val mapper = new ObjectMapper
    val rootNode = mapper.readTree(bytes)

    getBidResponse(rootNode)
  }

  private def getBidResponse(node: JsonNode): BidResponse = {
    require(node.isObject, "node must be object")

    val id = node.getChild("id").getString
    val seatBid = node.getChild("seatbid").getSeq(getSeatBid)
    val builder = BidResponseBuilder(id, seatBid)

    node.fields().asScala.foreach(e => e.getKey match {
      case "bidid" => builder.withBidId(e.getValue.getString)
      case "cur" => builder.withCur(e.getValue.getString)
      case "customdata" => builder.withCustomData(e.getValue.getString)
      case "nbr" => builder.withNbr(e.getValue.getInt)
      case _ =>
    })
    builder.build
  }

  private def getSeatBid(node: JsonNode): SeatBid = {
    require(node.isObject, "node must be object")

    val bid = node.getChild("bid").getSeq(getBid)
    val builder = SeatBidBuilder(bid)

    node.fields().asScala.foreach(e => e.getKey match {
      case "seat" => builder.withSeat(e.getValue.getString)
      case "group" => builder.withGroup(e.getValue.getInt)
      case _ =>
    })
    builder.build
  }

  private def getBid(node: JsonNode): Bid = {
    require(node.isObject, "node must be object")

    val id = node.getChild("id").getString
    val impId = node.getChild("impid").getString
    val priceNode = node.getChild("price")
    require(priceNode.isNumber, "node must be a decimal")
    val price = priceNode.decimalValue
    val builder = BidBuilder(id, impId, price)

    node.fields().asScala.foreach(e => e.getKey match {
      case "adid" => builder.withAdId(e.getValue.getString)
      case "nurl" => builder.withNurl(e.getValue.getString)
      case "adm" => builder.withAdm(e.getValue.getString)
      case "adomain" => builder.withAdomain(e.getValue.getStringSeq)
      case "bundle" => builder.withBundle(e.getValue.getString)
      case "iurl" => builder.withIurl(e.getValue.getString)
      case "cid" => builder.withCid(e.getValue.getString)
      case "crid" => builder.withCrid(e.getValue.getString)
      case "cat" => builder.withCat(e.getValue.getStringSeq)
      case "attr" => builder.withAttr(e.getValue.getIntSeq.toSet)
      case "dealid" => builder.withDealId(e.getValue.getString)
      case "h" => builder.withH(e.getValue.getInt)
      case "w" => builder.withW(e.getValue.getInt)
      case _ =>
    })
    builder.build
  }
}
