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
    def putOptionIntArray(name: String, value: Option[Iterable[Int]]) =
    putOptionArray(name, value, (i: Int) => node.numberNode(i))

    /**
      * Sets option value of a fields to specified string array. If value is None, does nothing.
      *
      * @param name  name of the field
      * @param value value of the field
      * @return this node (to allow chaining)
      */
    def putOptionStringArray(name: String, value: Option[Iterable[String]]) =
    putOptionArray(name, value, (s: String) => node.textNode(s))

    /**
      * Sets option value of a fields to specified T array. If value is None, does nothing.
      *
      * @param name  name of the field
      * @param value value of the field
      * @param f     function returning JsonNode from T
      * @tparam T type of source array
      * @return this node (to allow chaining)
      */
    def putOptionArray[T](name: String, value: Option[Iterable[T]], f: T => JsonNode) = {
      value.foreach(v =>
        node.putArray(name).addAll(v.map(f).asJavaCollection))
      node
    }

    /**
      * Sets value of a fields to specified int array.
      *
      * @param name  name of the field
      * @param value value of the field
      * @return this node (to allow chaining)
      */
    def putIntArray(name: String, value: Iterable[Int]) =
    putObjectArray(name, value, (i: Int) => node.numberNode(i))

    /**
      * Sets value of a fields to specified string array.
      *
      * @param name  name of the field
      * @param value value of the field
      * @return this node (to allow chaining)
      */
    def putStringArray(name: String, value: Iterable[String]) =
    putObjectArray(name, value, (s: String) => node.textNode(s))

    /**
      * Sets value of a fields to specified T array.
      *
      * @param name  name of the field
      * @param value value of the field
      * @param f     function returning JsonNode from T
      * @tparam T type of source array
      * @return this node (to allow chaining)
      */
    def putObjectArray[T](name: String, value: Iterable[T], f: (T) => JsonNode) = {
      node.putArray(name).addAll(value.map(f).asJavaCollection)
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
    def putOption[T](name: String, value: Option[T], f: (T) => ObjectNode) = {
      value.foreach(v => node.set(name, f(v)))
      node
    }

    def putOptionInt(name: String, value: Option[Int]) = {
      value.foreach(v => node.put(name, v))
      node
    }

    def putOptionFloat(name: String, value: Option[Float]) = {
      value.foreach(v => node.put(name, v))
      node
    }

    def putOptionDouble(name: String, value: Option[Double]) = {
      value.foreach(v => node.put(name, v))
      node
    }

    def putOptionString(name: String, value: Option[String]) = {
      value.foreach(v => node.put(name, v))
      node
    }
  }

}
