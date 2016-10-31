package com.bitworks.rtb.parser

import com.fasterxml.jackson.databind.ObjectMapper
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}

/**
  * Test for [[com.bitworks.rtb.parser.JsonParseHelper JsonParseHelper]].
  *
  * @author Egor Ilchenko
  */
class JsonParseHelperTest extends FlatSpec with Matchers with OneInstancePerTest {

  val helper = new Object with JsonParseHelper
  val mapper = new ObjectMapper

  "JsonParseHelper" should "return node child when \"getChild\" called" in {
    val childName = "name"
    val childValue = "value"

    val rootNode = mapper.createObjectNode
    rootNode.put(childName, childValue)

    val childNode = helper.ExtJsonNode(rootNode).getChild(childName)

    childNode.asText shouldBe childValue
  }

  it should "throw exception when \"getChild\" called for nonexistent node" in {
    val rootNode = mapper.createObjectNode

    an[IllegalArgumentException] should be thrownBy
        helper.ExtJsonNode(rootNode).getChild("some")
  }

  it should "return empty Seq when \"getSeq\" called on empty array node" in {
    val arrayNode = mapper.createArrayNode

    val emptySeq = helper.ExtJsonNode(arrayNode).getSeq(_.toString)

    emptySeq shouldBe empty
  }

  it should "return Seq using projection function when \"getSeq\" called on non empty node" in {
    val arrayNode = mapper.createArrayNode

    val expectedSeq = Seq(true, false, true)
    expectedSeq.foreach(arrayNode.add)

    val parsedSeq = helper.ExtJsonNode(arrayNode).getSeq(_.asBoolean)

    parsedSeq shouldBe expectedSeq
  }

  it should "throw exception when \"getSeq\" called on non array node" in {
    val objectNode = mapper.createObjectNode

    an[IllegalArgumentException] should be thrownBy
        helper.ExtJsonNode(objectNode).getSeq(_.asBoolean)
  }

  it should "return string Seq  when \"getStringSeq\" called on non empty node" in {
    val arrayNode = mapper.createArrayNode

    val expectedSeq = Seq("string1", "string2", "string3")
    expectedSeq.foreach(arrayNode.add)

    val parsedSeq = helper.ExtJsonNode(arrayNode).getStringSeq

    parsedSeq shouldBe expectedSeq
  }

  it should "throw exception when \"getStringSeq\" called on non array node" in {
    val objectNode = mapper.createObjectNode

    an[IllegalArgumentException] should be thrownBy
        helper.ExtJsonNode(objectNode).getStringSeq
  }

  it should "return int Seq when \"getIntSeq\" called on non empty node" in {
    val arrayNode = mapper.createArrayNode

    val expectedSeq = Seq(1, 2, 3)
    expectedSeq.foreach(arrayNode.add)

    val parsedSeq = helper.ExtJsonNode(arrayNode).getIntSeq

    parsedSeq shouldBe expectedSeq
  }

  it should "throw exception when \"getIntSeq\" called on non array node" in {
    val objectNode = mapper.createObjectNode

    an[IllegalArgumentException] should be thrownBy
        helper.ExtJsonNode(objectNode).getIntSeq
  }

  it should "return int when \"getInt\" called on int node" in {
    val intNode = mapper.createObjectNode
    val intVal = 3
    val nodeName = "id"
    intNode.put(nodeName, intVal)

    val parsedInt = helper.ExtJsonNode(intNode.get(nodeName))
        .getInt

    parsedInt shouldBe intVal
  }

  it should "throw exception when \"getInt\" called on nonint node" in {
    val intNode = mapper.createObjectNode
    val nodeName = "id"
    intNode.put(nodeName, "str")

    an[IllegalArgumentException] should be thrownBy
        helper.ExtJsonNode(intNode.get(nodeName)).getInt
  }

  it should "return string when \"getString\" called on string node" in {
    val stringNode = mapper.createObjectNode
    val stringVal = "str"
    val nodeName = "id"
    stringNode.put(nodeName, stringVal)

    val parsedString = helper.ExtJsonNode(stringNode.get(nodeName))
        .getString

    parsedString shouldBe stringVal
  }

  it should "throw exception when \"getString\" called on nonstring node" in {
    val stringNode = mapper.createObjectNode
    val nodeName = "id"
    stringNode.put(nodeName, 11)

    an[IllegalArgumentException] should be thrownBy
        helper.ExtJsonNode(stringNode.get(nodeName)).getString
  }

  it should "return double when \"getDouble\" called on numeric node" in {
    val doubleNode = mapper.createObjectNode
    val doubleVal = 3.24
    val nodeName = "id"
    doubleNode.put(nodeName, doubleVal)

    val parsedDouble = helper.ExtJsonNode(doubleNode.get(nodeName))
        .getDouble

    parsedDouble shouldBe doubleVal
  }

  it should "throw exception when \"getDouble\" called on non numeric node" in {
    val doubleNode = mapper.createObjectNode
    val nodeName = "id"
    doubleNode.put(nodeName, "str")

    an[IllegalArgumentException] should be thrownBy
        helper.ExtJsonNode(doubleNode.get(nodeName)).getDouble
  }

  it should "return float when \"getFloat\" called on numeric node" in {
    val floatNode = mapper.createObjectNode
    val floatVal = 3.24f
    val nodeName = "id"
    floatNode.put(nodeName, floatVal)

    val parsedFloat = helper.ExtJsonNode(floatNode.get(nodeName))
        .getDouble

    parsedFloat shouldBe floatVal
  }

  it should "throw exception when \"getFloat\" called on non numeric node" in {
    val floatNode = mapper.createObjectNode
    val nodeName = "id"
    floatNode.put(nodeName, "str")

    an[IllegalArgumentException] should be thrownBy
        helper.ExtJsonNode(floatNode.get(nodeName)).getFloat
  }
}
