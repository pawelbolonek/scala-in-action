package example {

  import org.scalatest._

  import scala.reflect.runtime.universe._
  import scala.xml.Elem
  import example.list.MyList
  import example.list.WritableMyList
  import example.list.SecondTraitMyList

  import scala.annotation.tailrec


  class Monad extends FlatSpec with Matchers {

    "Monad" should "pure monad definition" in {
      trait M[A] {
        def flatMap[B](f: A => M[B]): M[B]
        def unit[A](x: A): M[A]
      }

    }



    "Monad" should "obey left identity law" in {
      //unit(x).flatMap(f) == f(x)
      val f: (Int => List[Int]) = x => List(x - 1, x, x + 1)

      val x = 2
      val unit = List(x)
      val lhs = unit.flatMap(f)

      val rhs = f(x)

      assert(lhs == rhs)
    }

    "Monad" should "obey right identity law" in {
      //m is a monad
      //m.flatMap(unit) == m
      val f: (Int => List[Int]) = x => List(x - 1, x, x + 1)

      val monad = List(1,2,3)

      val lhs = monad.flatMap(List(_))
      val rhs = monad

      assert(lhs == rhs)
    }

    "Monad" should "obey associativity law" in {
      //m.flatMap(f).flatMap(g) == m.flatMap(x ⇒ f(x).flatMap(g))
      val f: (Int => List[Int]) = x => List(x - 1, x, x + 1)
      val g: (Int => List[Int]) = x => List(x, -x)

      val monad = List(1,2,3)

      val lhs = monad.flatMap(f).flatMap(g)
      val rhs = monad.flatMap(x => f(x).flatMap(g))


      assert(lhs == rhs)
    }

    "Monad" should "be wrapper" in {
      val x = (1 to 4)
      val y = (1 to 4)

      println(x.flatMap(xi => y.map(yi => xi * yi)))

      println(x flatMap {xi => y map {yi => xi*yi}})

      val w= for {
        xi <-x
        yi <-y
      }yield(xi * yi)


      // Puere monda definition
//      def unit: A → F[A]
//      def map: A → B
//      def flatten: F[F[A]] → F[A]

      //Monad's FLATMAP aka bind

      println(List(1,2,3).flatMap( i => List(i.toString)))
      //Monad's UNIT aka Indentity/Return
      //def unit[A](x: A): M[A]
    }

  }


}