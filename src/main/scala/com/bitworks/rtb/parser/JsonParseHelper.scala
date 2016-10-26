package com.bitworks.rtb.parser

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode

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
  implicit class JsonNodeExtensions(node: JsonNode) {

    /**
      * Returns child node with specified name.
      *
      * @param name name of the node to get
      * @throws IllegalArgumentException when parent or child node not found
      */
    def getChild(name: String): JsonNode = {
      require(node != null, "parent node must be not null")
      require(!node.isMissingNode, "missing parent node")
      val childNode = node.path(name)
      require(!childNode.isMissingNode, s"missing node $name")
      childNode
    }

    /** Returns all fields of the node.  */
    def getFields = getFieldsWithoutIgnored(Seq.empty)

    /**
      * Returns all fields of the node, excluding ignored fields.
      *
      * @param ignoredFields ignored fields
      */
    def getFieldsWithoutIgnored(ignoredFields: Seq[String]) = node
      .fields()
      .asScala
      .filterNot(elem => ignoredFields.contains(elem.getKey))
      .map(field => (field.getKey, field.getValue))
      .toList

    /**
      * Returns node value as Seq[String].
      *
      * @throws IllegalArgumentException if node not an array
      */
    def getStringSeq = getSeqUsing(n => n.getString)

    /**
      * Returns node value as Seq[Int].
      *
      * @throws IllegalArgumentException if node not an array
      */
    def getIntSeq = getSeqUsing(n => n.getInt)

    /**
      * Returns node value as Seq[T].
      *
      * @param f function returns resulting array element
      * @tparam T type of the returning array
      * @throws IllegalArgumentException if node not an array
      */
    def getSeqUsing[T](f: JsonNode => T): Seq[T] = {
      node match {
        case arrayNode: ArrayNode => arrayNode.elements().asScala.map(f).toSeq
        case _ => throw new DataValidationException("node must be an array")
      }
    }

    /**
      * Returns node value as int.
      *
      * @throws IllegalArgumentException if node is not an int
      */
    def getInt = {
      require(node.isInt, "node must be an integer")
      node.asInt
    }

    /**
      * Returns node value as string.
      *
      * @throws IllegalArgumentException if node is not an string
      */
    def getString = {
      require(node.isTextual, "node must be a string")
      node.asText
    }

    /**
      * Returns node value as double.
      *
      * @throws IllegalArgumentException if node is not an double
      */
    def getDouble = {
      require(node.isNumber, "node must be a number")
      node.asDouble
    }

    /**
      * Returns node value as float.
      *
      * @throws IllegalArgumentException if node is not an float
      */
    def getFloat = {
      require(node.isNumber, "node must be a number")
      node.floatValue
    }

  }

}
