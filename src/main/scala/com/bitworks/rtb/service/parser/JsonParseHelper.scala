package com.bitworks.rtb.service.parser

import com.fasterxml.jackson.databind.JsonNode

import scala.collection.JavaConverters._

/**
  * Helper for JSON parsing.
  *
  * @author Egor Ilchenko
  */
trait JsonParseHelper {

  /**
    * JsonNode extension methods.
    *
    * @param node [[com.fasterxml.jackson.databind.JsonNode node]] object
    * @author Egor Ilchenko
    */
  implicit class ExtJsonNode(node: JsonNode) {

    /**
      * Returns child node with specified name.
      *
      * @param name name of the node to get
      * @throws scala.IllegalArgumentException when child node is not found
      */
    def getChild(name: String): JsonNode = {
      val childNode = node.path(name)
      require(!childNode.isMissingNode, s"missing node $name")
      childNode
    }

    /**
      * Returns node value as Seq[String].
      *
      * @throws scala.IllegalArgumentException if node is not a string array
      */
    def getStringSeq = getSeq(_.getString)

    /**
      * Returns node value as Seq[Int].
      *
      * @throws scala.IllegalArgumentException if node is not an int array
      */
    def getIntSeq = getSeq(_.getInt)

    /**
      * Returns node value as Seq[T].
      *
      * @param f function to transform array elements
      * @tparam T type of the returning array
      * @throws scala.IllegalArgumentException if node is not an array
      */
    def getSeq[T](f: JsonNode => T): Seq[T] = {
      require(node.isArray, "node must be an array")
      node.elements().asScala.toList.map(f)
    }

    /**
      * Returns node value as int.
      *
      * @throws scala.IllegalArgumentException if node is is not an int
      */
    def getInt = {
      require(node.isInt, "node must be an integer")
      node.asInt
    }

    /**
      * Returns node value as string.
      *
      * @throws scala.IllegalArgumentException if node is not a string
      */
    def getString = {
      require(node.isTextual, "node must be a string")
      node.asText
    }

    /**
      * Returns node value as double.
      *
      * @throws scala.IllegalArgumentException if node is not an double
      */
    def getDouble = {
      require(node.isNumber, "node must be a number")
      node.doubleValue
    }

    /**
      * Returns node value as float.
      *
      * @throws scala.IllegalArgumentException if node is not a float
      */
    def getFloat = {
      require(node.isNumber, "node must be a number")
      node.floatValue
    }
  }
}
