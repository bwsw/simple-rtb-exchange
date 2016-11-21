package com.bitworks.rtb.service.factory

import java.time.LocalDate

import scala.util.Random

/**
  * Helper for bid request factory.
  *
  * @author Pavel Tomskikh
  */
trait FactoryHelper {

  val nowYear = LocalDate.now.getYear

  def checkSeq[T](p: T => Boolean)(s: Seq[T]): Boolean =
    s.nonEmpty && s.forall(p)

  def checkSeq(s: Seq[Option[_]]) = checkSeq[Option[_]](_.nonEmpty)(s)

  def checkSize(min: Option[Int], expected: Option[Int], max: Option[Int]): Boolean = {
    min.forall(isPositive) &&
      expected.forall(isPositive) &&
      max.forall(isPositive) &&
      notLarger(min, expected) &&
      notLarger(min, max) &&
      notLarger(expected, max)
  }

  def genId() = {
    val len = 16
    Random.alphanumeric.take(len).mkString
  }

  def between(left: Int, right: Int)(i: Int) = left <= i && i <= right

  def between(left: Float, right: Float)(i: Float) = left <= i && i <= right

  def isFlag(i: Int) = i == 0 || i == 1

  def isPositive(i: Int) = i > 0

  def isPositive(d: Double) = d > 0

  def notLarger(o1: Option[Int], o2: Option[Int]): Boolean = {
    o1.isEmpty ||
      o2.isEmpty ||
      o1.get <= o2.get
  }
}
