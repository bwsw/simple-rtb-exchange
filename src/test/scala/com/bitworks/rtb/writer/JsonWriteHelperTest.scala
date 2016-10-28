package com.bitworks.rtb.writer

import com.fasterxml.jackson.databind.node.ObjectNode
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.JavaConverters._


/**
  * Test for [[com.bitworks.rtb.writer.JsonWriteHelper JsonWriteHelper]].
  *
  * @author Egor Ilchenko
  */
class JsonWriteHelperTest extends FlatSpec with Matchers {

  val helper = new Object with JsonWriteHelper

  "JsonWriteHelper" should """create ObjectNode when "createObject" called""" in {
    val objNode = helper.createObject
    val anotherObjNode = helper.createObject

    objNode should not be null
    objNode shouldBe a[ObjectNode]
    objNode should not be theSameInstanceAs(anotherObjNode)
  }

  it should "put Some Int array to node" in {
    val expectedSeq = Seq(1, 2, 3)
    var node = helper.createObject
    val numArrayName = "numArray"
    val noneArrayName = "noneArray"
    node = helper.ExtJsonNode(node).putOptionIntArray(numArrayName, Some(expectedSeq))
    node = helper.ExtJsonNode(node).putOptionIntArray(noneArrayName, None)

    val arrayNode = node.path(numArrayName)
    val missingNode = node.path(noneArrayName)

    arrayNode shouldBe 'array
    missingNode shouldBe 'missingNode

    val seq = arrayNode.asScala.map(x => x.asInt)

    seq shouldBe expectedSeq
  }

  it should "put Some String array to node" in {
    val expectedSeq = Seq("str1", "str2", "str3")
    val node = helper.createObject
    val strArrayName = "strArray"
    val noneArrayName = "noneArray"
    helper.ExtJsonNode(node).putOptionStringArray(strArrayName, Some(expectedSeq))
    helper.ExtJsonNode(node).putOptionStringArray(noneArrayName, None)

    val arrayNode = node.path(strArrayName)
    val missingNode = node.path(noneArrayName)

    arrayNode shouldBe 'array
    missingNode shouldBe 'missingNode

    val seq = arrayNode.asScala.map(x => x.asText)

    seq shouldBe expectedSeq
  }

  it should "put Some T array to node" in {
    val expectedSeq = Seq(12.12, 15.15, 13.13)
    val node = helper.createObject
    val doubleArrayName = "strArray"
    val noneArrayName = "noneArray"
    helper.ExtJsonNode(node)
        .putOptionArray(doubleArrayName, Some(expectedSeq), (x: Double) => node.numberNode(x))
    helper.ExtJsonNode(node).putOptionArray(noneArrayName, None, (x: Any) => node)

    val arrayNode = node.path(doubleArrayName)
    val missingNode = node.path(noneArrayName)

    arrayNode shouldBe 'array
    missingNode shouldBe 'missingNode

    val seq = arrayNode.asScala.map(x => x.asDouble)

    seq shouldBe expectedSeq
  }

  it should "put Int array to node" in {
    val expectedSeq = Seq(1, 2, 3)
    var node = helper.createObject
    val numArrayName = "numArray"
    node = helper.ExtJsonNode(node).putIntArray(numArrayName, expectedSeq)

    val arrayNode = node.path(numArrayName)

    arrayNode shouldBe 'array

    val seq = arrayNode.asScala.map(x => x.asInt)

    seq shouldBe expectedSeq
  }

  it should "put String array to node" in {
    val expectedSeq = Seq("str1", "str2", "str3")
    val node = helper.createObject
    val strArrayName = "strArray"
    helper.ExtJsonNode(node).putStringArray(strArrayName, expectedSeq)

    val arrayNode = node.path(strArrayName)

    arrayNode shouldBe 'array

    val seq = arrayNode.asScala.map(x => x.asText)

    seq shouldBe expectedSeq
  }

  it should "put T array to node" in {
    val expectedSeq = Seq(12.12, 15.15, 13.13)
    val node = helper.createObject
    val doubleArrayName = "strArray"
    helper.ExtJsonNode(node)
        .putObjectArray(doubleArrayName, expectedSeq, (x: Double) => node.numberNode(x))

    val arrayNode = node.path(doubleArrayName)

    arrayNode shouldBe 'array

    val seq = arrayNode.asScala.map(x => x.asDouble)

    seq shouldBe expectedSeq
  }

  it should "put Some String to node" in {
    val node = helper.createObject
    val expectedValue = "strValue"
    val fieldName = "strField"
    val noneFieldName = "noneField"

    helper.ExtJsonNode(node).putOptionString(fieldName, Some(expectedValue))
    helper.ExtJsonNode(node).putOptionString(fieldName, None)

    val valueNode = node.path(fieldName)
    val missingNode = node.path(noneFieldName)

    valueNode shouldBe 'textual
    missingNode shouldBe 'missingNode

    val value = valueNode.textValue

    expectedValue shouldBe value
  }

  it should "put Some Int to node" in {
    val node = helper.createObject
    val expectedValue = 24
    val fieldName = "intField"
    val noneFieldName = "noneField"

    helper.ExtJsonNode(node).putOptionInt(fieldName, Some(expectedValue))
    helper.ExtJsonNode(node).putOptionInt(fieldName, None)

    val valueNode = node.path(fieldName)
    val missingNode = node.path(noneFieldName)

    valueNode shouldBe 'number
    missingNode shouldBe 'missingNode

    val value = valueNode.intValue

    expectedValue shouldBe value
  }

  it should "put Some Double to node" in {
    val node = helper.createObject
    val expectedValue = 24.12d
    val fieldName = "doubleField"
    val noneFieldName = "noneField"

    helper.ExtJsonNode(node).putOptionDouble(fieldName, Some(expectedValue))
    helper.ExtJsonNode(node).putOptionDouble(fieldName, None)

    val valueNode = node.path(fieldName)
    val missingNode = node.path(noneFieldName)

    valueNode shouldBe 'number
    missingNode shouldBe 'missingNode

    val value = valueNode.doubleValue

    expectedValue shouldBe value
  }

  it should "put Some Float to node" in {
    val node = helper.createObject
    val expectedValue = 24.12f
    val fieldName = "floatField"
    val noneFieldName = "noneField"

    helper.ExtJsonNode(node).putOptionFloat(fieldName, Some(expectedValue))
    helper.ExtJsonNode(node).putOptionFloat(fieldName, None)

    val valueNode = node.path(fieldName)
    val missingNode = node.path(noneFieldName)

    valueNode shouldBe 'number
    missingNode shouldBe 'missingNode

    val value = valueNode.floatValue

    expectedValue shouldBe value
  }

  it should "put Some T using transform function to node" in {
    val node = helper.createObject
    val expectedValue = (123, 345)
    val transformFun = (t: (Int, Int)) => {
      val node = helper.createObject
      node.put("one", t._1)
      node.put("two", t._2)
    }
    val strFieldName = "strField"
    val noneFieldName = "noneField"

    helper.ExtJsonNode(node).putOption(strFieldName, Some(expectedValue), transformFun)
    helper.ExtJsonNode(node).putOption(noneFieldName, None, transformFun)

    val objNode = node.path(strFieldName)
    val missingNode = node.path(noneFieldName)

    objNode shouldBe 'object
    missingNode shouldBe 'missingNode

    val value = (objNode.path("one").asInt, objNode.path("two").asInt)

    value shouldBe expectedValue
  }

}
