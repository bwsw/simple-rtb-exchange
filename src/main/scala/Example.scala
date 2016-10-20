/** Just example
  *
  * Created on: 10/20/2016
  *
  * @author Ilchenko Egor
  * @version %I%
  *
  *          All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class Example {
  def add(a: Int, b: Int) = a + b
  def mult(a: Int, b: Int) = a * b
}

object Example{
  def apply(): Example = new Example()
}
