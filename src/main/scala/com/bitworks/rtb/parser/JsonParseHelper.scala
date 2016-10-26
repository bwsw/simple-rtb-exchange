package com.bitworks.rtb.parser

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.{ArrayNode, JsonNodeType}

import scala.collection.JavaConverters._


/**
  * Helper for JSON parsing.
  *
  * @author Egor Ilchenko
  */
trait JsonParseHelper {

  /**
    * Throws [[com.bitworks.rtb.parser.DataValidationException DataValidationException]] for
    * unrecognized field.
    *
    * @param nodeName  node name, containing unrecognized field
    * @param fieldName unrecognized field name
    * @param fieldType unrecognized field type
    */
  def throwNotRecognized(nodeName: String, fieldName: String, fieldType: JsonNodeType) = {
    throw new DataValidationException(
      s"""unrecognized field "$fieldName" with type "$fieldType" on "$nodeName" node""")
  }

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
      .map(field => (field.getKey, field.getValue.getNodeType, field.getValue))
      .toList

    /**
      * Returns node value as Seq[String].
      *
      * @throws IllegalArgumentException if node not an array
      */
    def asStringSeq = asSeqUsing(n => n.asText)

    /**
      * Returns node value as Seq[Int].
      *
      * @throws IllegalArgumentException if node not an array
      */
    def asIntSeq = asSeqUsing(n => n.asInt)

    /**
      * Returns node value as Seq[T].
      *
      * @param f function returns resulting array element
      * @tparam T type of the returning array
      * @throws IllegalArgumentException if node not an array
      */
    def asSeqUsing[T](f: JsonNode => T): Seq[T] = {
      node match {
        case arrayNode: ArrayNode => arrayNode.elements().asScala.map(f).toSeq
        case _ => throw new DataValidationException("node must be of array type")
      }
    }
  }

}
