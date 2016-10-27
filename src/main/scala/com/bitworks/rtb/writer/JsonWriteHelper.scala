package com.bitworks.rtb.writer

import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}

import scala.collection.JavaConverters._

/**
  * Helper for JSON writing.
  *
  * @author Egor Ilchenko
  */
trait JsonWriteHelper {

  protected val mapper = new ObjectMapper()

  /**
    * Creates new [[com.fasterxml.jackson.databind.node.ObjectNode ObjectNode]].
    */
  def createObject = mapper.createObjectNode

  /**
    * ObjectNode extension methods.
    *
    * @param node [[com.fasterxml.jackson.databind.node.ObjectNode ObjectNode]] object
    * @author Egor Ilchenko
    */
  implicit class JsonNodeWriteExtensions(node: ObjectNode) {

    /**
      * Sets value of a fields to specified int array. If value is None, does nothing.
      *
      * @param name  name of the field
      * @param value value of the field
      * @return this node (to allow chaining)
      */
    def putIntArray(name: String, value: Option[Iterable[Int]]) =
      putObjectArray(name, value, (i: Int) => node.numberNode(i))

    /**
      * Sets value of a fields to specified string array. If value is None, does nothing.
      *
      * @param name  name of the field
      * @param value value of the field
      * @return this node (to allow chaining)
      */
    def putStringArray(name: String, value: Option[Iterable[String]]) =
      putObjectArray(name, value, (s: String) => node.textNode(s))

    /**
      * Sets value of a fields to specified T array. If value is None, does nothing.
      *
      * @param name  name of the field
      * @param value value of the field
      * @param f     function returning JsonNode from T
      * @tparam T type of source array
      * @return this node (to allow chaining)
      */
    def putObjectArray[T](name: String, value: Option[Iterable[T]], f: T => JsonNode) = {
      value.foreach(v => {
        node.putArray(name).addAll(v.map(f).asJavaCollection)
      })
      node
    }

    /**
      * Sets value of a field to specified Option[T]. If value is None, does nothing.
      *
      * @param name  name of the field
      * @param value value of the field
      * @param f     function returning JsonNode from T
      * @tparam T type of value
      * @return this node (to allow chaining)
      */
    def putOption[T](name: String, value: Option[T], f: T => ObjectNode) = {
      value.foreach(v => {
        node.set(name, f(v))
      })
      node
    }

    /**
      * Sets value of a field to specified Option[T]. If value is None, does nothing.
      *
      * @param name  name of the field
      * @param value value of the field
      * @tparam T type of value
      * @return this node (to allow chaining)
      */
    def putOption[T](name: String, value: Option[T]) = {
      value.foreach {
        case v: String => node.put(name, v)
        case v: Int => node.put(name, v)
        case v: Float => node.put(name, v)
        case v: Double => node.put(name, v)
        case _ => throw new UnsupportedOperationException
      }
      node
    }
  }

}
