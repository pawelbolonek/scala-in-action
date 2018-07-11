package example

import org.scalatest.{FlatSpec, Matchers}

import scala.annotation.tailrec
import scala.collection.immutable.List

/**
  * Created by e-plbk on 2017-07-27.
  *
  * High level view on scala and it's scalability
  */
class Ch5 extends FlatSpec with Matchers {

  "Scala" should "keep side offects outside main flow" in {

    def tap[A](a: A)(sideEffect: A => Unit): A = {
      sideEffect(a)
      a
    }

    val i = 2;

    println(tap(i) { p => println(p)}.toString())
  }

  "Scala" should "curry function" in {
    val x1 = (s1: String, s2: String) => {s1 + s2}
    def xx1(s1: String)(s2: String)= {s1+ s2}

    println(x1("a","b"))
    println(xx1("a")("b"))

    val x2 = x1.curried("c")
    println(x2("d"))

    val xx2 = xx1("c") _
    println(xx2("d"))

    val x3 = x1(_:String, "f")

    println(x3("e"))

  }


  "Scala" should "compose function" in {
    val addOne: Int => Int = x => x + 1
    val addTwo: Int => Int = x => x + 2
    val addThree = addOne compose addTwo

  }

  "Scala" should "demo partial function" in{
    val one: PartialFunction[Int, String] = { case 1 => "one" }
    val two: PartialFunction[Int, String] = { case 2 => "one" }

    val three = one orElse two

    println(one.isDefinedAt(1))
    println(one.isDefinedAt(2))
    println(three.isDefinedAt(2))
  }

  "Scala" should "functional" in{
    def even: Int => Boolean = _ % 2 == 0
    def not: Boolean => Boolean = !_

    def filter[A](criteria: A => Boolean)(col: Traversable[A]):Traversable[A]  = col.filter(criteria)
    def map[A, B](f: A => B)(col: Traversable[A]) = col.map(f)



    def evenFilter = filter(even) _
    def double: Int => Int = _ * 2

    def doubleAllEven = evenFilter andThen map(double)

    println(doubleAllEven(List(1,2,3,4)))
  }

  "Scala" should "strategy pattern" in {
    trait TaxStrategy {def taxIt(product: String): Double }
    class ATaxStrategy extends TaxStrategy {
      def taxIt(product: String): Double = 2.0
    }
    class BTaxStrategy extends TaxStrategy {
      def taxIt(product: String): Double = 1.0
    }

    def taxIt2: TaxStrategy => String => Double = s => p => s.taxIt(p)

    def taxIt_a: String => Double = taxIt2(new ATaxStrategy)
    def taxIt_b: String => Double = taxIt2(new BTaxStrategy)


  }

  "Scala" should "function definition" in {
    type Bolo[-A, +B] = Function1[A, B];


    def plus2 (x: Int): Int = x+1

    object plus3 extends Function1[Int,Int]{
      def apply(x: Int):Int = x+1
    }
    //shorthand of above
    val plus1 = (x: Int) => x+1
    //alternative of above
    object plus4 extends (Int=> Int){
      def apply(x: Int):Int = x+1
    }

    //The good news is that Scala lets you convert methods to functions using a transform process called Eta expansion
    val f = plus2 _
    f(1)

    println(plus1.getClass())
    //println(plus2.getClass())
    println(plus3.getClass())
    println(plus4.getClass())
    //passing real function - scala creates wrapper object where apply redirects execution to function
    List(1,2,3).map(plus2)
  }

  "Scala" should "filter lazyness" in {
    //scala 2.8 -> List(1) (filter is lazy) list.withFilter
    //scala 2.7 -> List(1 ,2, 3) (filter is eager) list.Filter
    //
    val list = List(1, 2, 3)
    var go = true
    val x = for(i <- list; if(go)) yield {
      go = false
      i
    }
    println(x)
  }
 }

