package example

import org.scalatest.{FlatSpec, Matchers}

import scala.annotation.tailrec
import scala.collection.immutable.List

/**
  * Created by e-plbk on 2017-07-27.
  *
  * High level view on scala and it's scalability
  */
class Ch4 extends FlatSpec with Matchers {

  class Shape

  class Rectangle extends Shape

  class Square extends Rectangle

  "Scala" should "covariant and contravariant" in{

    //A invariant
    //+A Covariant
    //-A Contravariance

    def foo[T](s: T) : T ={s}

    var s:Rectangle = foo(new Rectangle());

    trait Fun1[-A,+R]{
      def apply(a: A) :R
    }

    var f: Fun1[Rectangle,Rectangle] = {x:Rectangle => x}

    //works because of +R
    var g: Fun1[Rectangle, Shape] = f

    //works because of -A
    var h: Fun1[Square, Rectangle] = f
  }

  "Scala" should "type bound" in {

    def foo[A <: B,B](a:A, b:B):Unit={}

    foo(new Square(), new Shape());

    foo(new Shape(), new Square());
  }

  "Scala" should "replace call by value by call by name" in {
    def longMessageRetrieval():String ={
      println("Message from analog tape")
      return "Analog message"
    }



    def log_by_value(s:String) = {
      print("Log by value")
      if(false){ println(s)}
    }

    log_by_value(longMessageRetrieval())

    def log_by_name(s: => String) = {
      print("Log by name")
      if(false){ println(s)}
    }

    log_by_name(longMessageRetrieval())


  }


  "Scala" should "what is difference between lambda and closure?" in{

  }

  "Scala" should "tail recursion" in {

    @tailrec
    def _len[A](l: List[A], size: Int): Int ={
      l match {
        case Nil => size
        case x :: ys => _len(ys, size + 1)
      }
    }

    println( _len(List(1,2,3,4), 0) )


  }

  "Scala" should "lazy collections" in{
    List(1, 2, 3, 4, 5).view.map( x=> {println(x); x + 1} ).head
    List(1, 2, 3, 4, 5).map( x=> {println(x); x + 1} ).head



    //eager
    val allTweets1 = Map("nraychaudhuri" -> println("nraychaudhuri"),
      "ManningBooks" -> println("ManningBooks"),
      "bubbl_scala" -> println("bubbl_scala")
    )

    //lazy map with partial function
    val allTweets2 = Map(
      "nraychaudhuri" -> print _ , "ManningBooks" -> print _,
      "bubbl_scala" -> print _
    )

    allTweets2("ManningBooks")("aa")

  }

}
