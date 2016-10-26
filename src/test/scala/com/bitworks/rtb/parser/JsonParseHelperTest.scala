package com.bitworks.rtb.parser

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.JsonNodeType
import org.scalatest.{FlatSpec, Matchers}

/**
  * Test for [[com.bitworks.rtb.parser.JsonParseHelper JsonParseHelper]].
  *
  * @author Egor Ilchenko
  */
class JsonParseHelperTest extends FlatSpec with Matchers{

  "JsonParseHelper" should "throw exception when \"throwNotRecognized\" called" in {
    val helper = new Object with JsonParseHelper

    an [DataValidationException] should be thrownBy
      helper.throwNotRecognized("name", "name", JsonNodeType.BOOLEAN)
  }

  it should "return node child when \"getChild\" called" in {
    val helper = new Object with JsonParseHelper
    val mapper = new ObjectMapper
    val rootNode = mapper.createObjectNode
    val childNodeInfo = new {val name = "name"; val value = "value"}
    rootNode.put(childNodeInfo.name, childNodeInfo.value)

    val childNode = helper.JsonNodeExtensions(rootNode).getChild(childNodeInfo.name)

    childNode.asText shouldBe childNodeInfo.value
  }

  it should "return empty Seq when \"asSeqUsing\" called on empty array node" in {
    val helper = new Object with JsonParseHelper
    val mapper = new ObjectMapper
    val arrayNode = mapper.createArrayNode

    val emptySeq = helper.JsonNodeExtensions(arrayNode).asSeqUsing(_.toString)

    emptySeq shouldBe empty
  }

  it should "return Seq using projection function when \"asSeqUsing\" called on non empty node" in {
    val helper = new Object with JsonParseHelper
    val mapper = new ObjectMapper
    val arrayNode = mapper.createArrayNode

    val expectedSeq = Seq(true, false, true)
    arrayNode.add(expectedSeq(0))
    arrayNode.add(expectedSeq(1))
    arrayNode.add(expectedSeq(2))

    val parsedSeq = helper.JsonNodeExtensions(arrayNode).asSeqUsing(_.asBoolean)

    parsedSeq shouldBe expectedSeq
  }

  it should "return string Seq  when \"asStringSeq\" called on non empty node" in {
    val helper = new Object with JsonParseHelper
    val mapper = new ObjectMapper
    val arrayNode = mapper.createArrayNode

    val expectedSeq = Seq("string1", "string2", "string3")
    arrayNode.add(expectedSeq(0))
    arrayNode.add(expectedSeq(1))
    arrayNode.add(expectedSeq(2))

    val parsedSeq = helper.JsonNodeExtensions(arrayNode).asStringSeq

    parsedSeq shouldBe expectedSeq
  }

  it should "return int Seq when \"asIntSeq\" called on non empty node" in {
    val helper = new Object with JsonParseHelper
    val mapper = new ObjectMapper
    val arrayNode = mapper.createArrayNode

    val expectedSeq = Seq(1, 2, 3)
    arrayNode.add(expectedSeq(0))
    arrayNode.add(expectedSeq(1))
    arrayNode.add(expectedSeq(2))

    val parsedSeq = helper.JsonNodeExtensions(arrayNode).asIntSeq

    parsedSeq shouldBe expectedSeq
  }

  it should "return fields when \"getFields\" called" in {
    val helper = new Object with JsonParseHelper
    val mapper = new ObjectMapper
    val parentNode = mapper.createObjectNode

    val fields = Seq(
      ("one", JsonNodeType.NUMBER, 1),
      ("two", JsonNodeType.STRING, "somestr"))

    fields.foreach {
      case (n, _, s: String) => parentNode.put(n, s)
      case (n, _, i: Int) => parentNode.put(n, i)
      case _ => fail
    }

    val parsedFields = helper.JsonNodeExtensions(parentNode)
      .getFields
      .map{
        case (n, JsonNodeType.STRING, v)  => (n, JsonNodeType.STRING, v.asText)
        case (n, JsonNodeType.NUMBER, v)  => (n, JsonNodeType.NUMBER, v.asInt)
        case _ => fail
      }

    parsedFields shouldBe fields
  }

  it should "return fields without ignored when \"getFieldsWithoutIgnored\" called" in {
    val helper = new Object with JsonParseHelper
    val mapper = new ObjectMapper
    val parentNode = mapper.createObjectNode

    val fields = Seq(
      ("one", JsonNodeType.NUMBER, 1),
      ("two", JsonNodeType.STRING, "somestr"))

    fields.foreach {
      case (n, _, s: String) => parentNode.put(n, s)
      case (n, _, i: Int) => parentNode.put(n, i)
      case _ => fail
    }

    val ignoredFieldName = "name"
    parentNode.put(ignoredFieldName, "value")

    val parsedFields = helper.JsonNodeExtensions(parentNode)
      .getFieldsWithoutIgnored(Seq(ignoredFieldName))
      .map{
        case (n, JsonNodeType.STRING, v)  => (n, JsonNodeType.STRING, v.asText)
        case (n, JsonNodeType.NUMBER, v)  => (n, JsonNodeType.NUMBER, v.asInt)
        case _ => fail
      }

    parsedFields shouldBe fields
  }
}
