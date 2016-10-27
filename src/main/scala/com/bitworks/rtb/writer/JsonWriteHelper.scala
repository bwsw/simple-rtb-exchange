package com.bitworks.rtb.writer

import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}

import scala.collection.JavaConverters._

trait JsonWriteHelper {

  def createObject(implicit mapper: ObjectMapper): ObjectNode = mapper.createObjectNode

  implicit class JsonNodeWriteExtensions(node: ObjectNode) {

    def putIntArray(name: String, value: Option[Iterable[Int]]) =
    putObjectArray(name, value, (i: Int) => node.numberNode(i))

    def putStringArray(name: String, value: Option[Iterable[String]]) =
    putObjectArray(name, value, (s: String) => node.textNode(s))

    def putObjectArray[T](name: String, value: Option[Iterable[T]], f: T => JsonNode) = {
      value.foreach(v => {
        node.putArray(name).addAll(v.map(f).asJavaCollection)
      })
      node
    }

    def putOption[T](name: String, value: Option[T], f: T => ObjectNode) = {
      value.foreach(v => {
        node.set(name, f(v))
      })
      node
    }

    def putOption[T](name: String, value: Option[T]) = {
      value.foreach {
        case v: String => node.put(name, v)
        case v: Int => node.put(name, v)
        case _ => throw new UnsupportedOperationException
      }
      node
    }
  }

}
