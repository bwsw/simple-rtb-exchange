package com.bitworks.rtb.parser.response

import com.bitworks.rtb.model.response._
import com.bitworks.rtb.model.response.builder._
import com.bitworks.rtb.parser.JsonParseHelper
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}

/**
  * JSON parser for [[com.bitworks.rtb.model.response.BidResponse BidResponse]].
  *
  * @author Pavel Tomskikh
  */
object BidResponseJsonParser extends BidResponseParser with JsonParseHelper {

  /**
    * Returns parsed JSON string to [[com.bitworks.rtb.model.response.BidResponse BidResponse]]
    * object without exception catching.
    *
    * @param s parsing string
    */
  override def parseInternal(s: String): BidResponse = {
    val mapper = new ObjectMapper
    val rootNode = mapper.readValue(s, classOf[JsonNode])

    getBidResponse(rootNode)
  }

  private def getBidResponse(node: JsonNode): BidResponse = {
    require(node.isObject, "node must be object")

    val id = node.getChild("id").getString
    val seatBid = node.getChild("seatbid").getSeqUsing(getSeatBid)
    val builder = BidResponseBuilder(id, seatBid)

    node.getFields.par.foreach {
      case ("bidid", v) => builder.withBidId(v.getString)
      case ("cur", v) => builder.withCur(v.getString)
      case ("customdata", v) => builder.withCustomData(v.getString)
      case ("nbr", v) => builder.withNbr(v.getInt)
      case ("ext", _) =>
      case _ =>
    }
    builder.build
  }

  private def getSeatBid(node: JsonNode): SeatBid = {
    require(node.isObject, "node must be object")

    val bid = node.getChild("bid").getSeqUsing(getBid)
    val builder = SeatBidBuilder(bid)

    node.getFields.par.foreach {
      case ("seat", v) => builder.withSeat(v.getString)
      case ("group", v) => builder.withGroup(v.getInt)
      case ("ext", _) =>
      case _ =>
    }
    builder.build
  }

  private def getBid(node: JsonNode): Bid = {
    require(node.isObject, "node must be object")

    val id = node.getChild("id").getString
    val impId = node.getChild("impid").getString
    val priceNode = node.getChild("price")
    if (!priceNode.isNumber)
      throw new IllegalArgumentException
    val price = priceNode.decimalValue
    val builder = BidBuilder(id, impId, price)

    node.getFields.par.foreach {
      case ("adid", v) => builder.withAdId(v.getString)
      case ("nurl", v) => builder.withNurl(v.getString)
      case ("adm", v) => builder.withAdm(v.getString)
      case ("adomain", v) => builder.withAdomain(v.getStringSeq)
      case ("bundle", v) => builder.withBundle(v.getString)
      case ("iurl", v) => builder.withIurl(v.getString)
      case ("cid", v) => builder.withCid(v.getString)
      case ("crid", v) => builder.withCrid(v.getString)
      case ("cat", v) => builder.withCat(v.getStringSeq)
      case ("attr", v) => builder.withAttr(v.getIntSeq.toSet)
      case ("dealid", v) => builder.withDealId(v.getString)
      case ("h", v) => builder.withH(v.getInt)
      case ("w", v) => builder.withW(v.getInt)
      case ("ext", _) =>
      case _ =>
    }
    builder.build
  }
}
