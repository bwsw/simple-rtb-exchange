import org.scalatest.{FlatSpec, Matchers}

/**
  *
  * Created on: 10/20/2016
  *
  * @author Ilchenko Egor
  * @version %I%
  *
  *          All Rights Reserved (c) 2016 Bitworks Software, Ltd.
  */
class ExampleTest extends FlatSpec with Matchers{
  "Example" should "add ints correctly" in {
    val four = Example().add(2, 2)

    four shouldBe (2 + 2)
  }
}
