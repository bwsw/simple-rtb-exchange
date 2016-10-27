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
    objNode shouldBe a [ObjectNode]
    objNode should not be theSameInstanceAs(anotherObjNode)
  }

  it should "put Some Int array to node" in {
    val expectedSeq = Seq(1, 2, 3)
    var node = helper.createObject
    val numArrayName = "numArray"
    val noneArrayName = "noneArray"
    node = helper.JsonNodeWriteExtensions(node).putIntArray(numArrayName, Some(expectedSeq))
    node = helper.JsonNodeWriteExtensions(node).putIntArray(noneArrayName, None)

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
    helper.JsonNodeWriteExtensions(node).putStringArray(strArrayName, Some(expectedSeq))
    helper.JsonNodeWriteExtensions(node).putStringArray(noneArrayName, None)

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
    helper.JsonNodeWriteExtensions(node)
      .putObjectArray(doubleArrayName, Some(expectedSeq), (x : Double) => node.numberNode(x))
    helper.JsonNodeWriteExtensions(node).putObjectArray(noneArrayName, None, (x : Any) => node)

    val arrayNode = node.path(doubleArrayName)
    val missingNode = node.path(noneArrayName)

    arrayNode shouldBe 'array
    missingNode shouldBe 'missingNode

    val seq = arrayNode.asScala.map(x => x.asDouble)

    seq shouldBe expectedSeq
  }

  it should "put Some T to node" in {
    val node = helper.createObject
    val expectedStrValue = "strValue"
    val expectedIntValue = 24
    val strFieldName = "strField"
    val intFieldName = "intField"
    val noneFieldName = "noneField"

    helper.JsonNodeWriteExtensions(node).putOption(strFieldName, Some(expectedStrValue))
    helper.JsonNodeWriteExtensions(node).putOption(intFieldName, Some(expectedIntValue))
    helper.JsonNodeWriteExtensions(node).putOption(noneFieldName, None)

    val strNode = node.path(strFieldName)
    val intNode = node.path(intFieldName)
    val missingNode = node.path(noneFieldName)

    strNode shouldBe 'textual
    intNode shouldBe 'number
    missingNode shouldBe 'missingNode

    val strValue = strNode.asText
    val intValue = intNode.asInt

    strValue shouldBe expectedStrValue
    intValue shouldBe expectedIntValue
  }

  it should "put Some T using transform function to node" in {
    val node = helper.createObject
    val expectedValue = (123, 345)
    val transformFun = (t: Tuple2[Int, Int]) => {
      val node = helper.createObject
      node.put("one", t._1)
      node.put("two", t._2)
    }
    val strFieldName = "strField"
    val noneFieldName = "noneField"

    helper.JsonNodeWriteExtensions(node).putOption(strFieldName, Some(expectedValue), transformFun)
    helper.JsonNodeWriteExtensions(node).putOption(noneFieldName, None)

    val objNode = node.path(strFieldName)
    val missingNode = node.path(noneFieldName)

    objNode shouldBe 'object
    missingNode shouldBe 'missingNode

    val value = (objNode.path("one").asInt, objNode.path("two").asInt)

    value shouldBe expectedValue
  }

  it should """throw exception when "putOption" called with unsupported type"""" in {
    val node = helper.createObject

    an [UnsupportedOperationException] shouldBe thrownBy {
      helper.JsonNodeWriteExtensions(node).putOption("someTuple", Some((1, 2)))
    }
  }

}
