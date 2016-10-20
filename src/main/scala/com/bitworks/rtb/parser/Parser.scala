package com.bitworks.rtb.parser

import org.json4s.JsonAST.JObject

/** Interface for parsers.
  *
  * Created on: 10/20/2016
  *
  * @author Tomskih Pavel
  * @version %I%
  *
  *          All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
trait Parser {
  def parse(in: JObject)
}