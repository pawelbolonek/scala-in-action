package example.Examples

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by e-plbk on 2017-07-27.
  */
class Monads extends FlatSpec with Matchers{

  "Monad" should "wrap integers v1" in {
    trait MaybeInt {
      def map(f: Int => Int): MaybeInt
      def flatMap(f: Int => MaybeInt): MaybeInt
    }

    case class SomeInt(i: Int) extends MaybeInt {
      def map(f: Int => Int): MaybeInt = {
        println("Some int map")
        SomeInt(f(i))
      }
      def flatMap(f: Int => MaybeInt): MaybeInt = {
        println("Some int flatMap")
        f(i)
      }
    }

    case object NoInt extends MaybeInt {
      def map(f: Int => Int): MaybeInt = {
        println("NoInt map")
        NoInt
      }
      def flatMap(f: Int => MaybeInt): MaybeInt = {
        println("NoInt flatMap")
        NoInt
      }
    }


    val someInt12 = SomeInt(12)
    val someInt10 = SomeInt(10)
    val noInt = NoInt

    val i = for {
      a <-someInt12
      b <-someInt10
    } yield a + b;

    println(i)
  }

  "Monad" should "wrap integers v2" in {
    sealed trait MaybeInt {
      def map(f: Int => Int): MaybeInt
      def flatMap(f: Int => MaybeInt): MaybeInt
    }

    case class SomeInt(i: Int) extends MaybeInt {
      def map(f: Int => Int): MaybeInt = {
        println("Some int map")
        SomeInt(f(i))
      }
      def flatMap(f: Int => MaybeInt): MaybeInt = {
        println("Some int flatMap")
        f(i)
      }
    }

    case object ZeroInt extends MaybeInt{
      override def map(f: (Int) => Int): MaybeInt = SomeInt(f(0))
      override def flatMap(f: (Int) => MaybeInt): MaybeInt = f(0)
    }
    case object OneInt extends MaybeInt{
      override def map(f: (Int) => Int): MaybeInt = SomeInt(f(1))

      override def flatMap(f: (Int) => MaybeInt): MaybeInt = f(1)
    }

    case object NoInt extends MaybeInt {
      def map(f: Int => Int): MaybeInt = {
        println("NoInt map")
        NoInt
      }
      def flatMap(f: Int => MaybeInt): MaybeInt = {
        println("NoInt flatMap")
        NoInt
      }
    }


    val someInt12 = SomeInt(12)
    val someInt10 = SomeInt(10)
    val noInt = NoInt

    val i = for {

      c <-ZeroInt
      d <- OneInt
      e <- OneInt
    } yield c + d + e;

    val j = ZeroInt flatMap {c => OneInt flatMap {d => OneInt flatMap {e => SomeInt(c + d + e)}} }


      println(i)
  }

  "List" should "be a monad too" in {
    val first = List(1, 2)
    val second = List(8, 9)
    val third = List(11, 23)

    val lsb = for {
      i <- first
      j <- second
      k <- third
    } yield (i * j * k)

    val rsb = first flatMap {
      _i =>
        second flatMap {
          _j => third map{
            _k => _i * _j * _k
          }
        }
    }

    lsb shouldEqual rsb
  }

  "Option" should "be a monad too" in{
    case class Order(lineItem: Option[LineItem])
    case class LineItem(product: Option[Product])
    case class Product(name: String)

    val o = Option(Order(Option(LineItem(Option(Product("Nike"))))))


    for {
      order <- o
      lineItem <- order.lineItem
      product <- lineItem.product
    }
      yield product.name

  }
}
