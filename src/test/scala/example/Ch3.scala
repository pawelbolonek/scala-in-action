package example

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by e-plbk on 2017-07-27.
  */
class Ch3 extends FlatSpec with Matchers {

  "Scala" should "use class constructors" in {
    //AnyRef i base class for references
    class Clazz(var mutable: String, val immutable: String, private_immutable: String, private var _private_mutable: String) extends AnyRef{
      //Scala scripting nature
      println("Hello clazz")

      def private_mutable = _private_mutable
      // _private_mutable_= is a method name which is exposed as setter for variable
      def private_mutable_=(new_private_mutable: String) = _private_mutable = new_private_mutable
      //2nd non default constructor
      def this(mutable: String) {
        this(mutable, "immutable", "private_immutable","private_mutable")
      }

      def foo(): Unit ={
        mutable = "new value"
        //below errors in compilation
        //immutable = "new value"
        //private_immutable = "new value"
        private_mutable ="new value"
      }
    }

    val c = new Clazz("mutable", "immutable", "private_immutable","private_mutable")

    c.mutable = "new value"
    //below errors in compilation
    //c.immutable = "new value"
    c.private_mutable = "new value"


  }

  "Scala" should "demo imports" in {
    //like a star
    import scala.util._
    new Random().nextInt()
    import scala.collection.immutable.{List => Lista}

    var v = Lista(1)
  }

  "Scala" should "demonstrate factory pattern with companion object" in{
    abstract class Role {
      def canAccess(page: String): Boolean
    }
    class Root extends Role {
      override def canAccess(page:String) = true
    }
    class SuperAnalyst extends Role {
      override def canAccess(page:String) = page != "Admin"
    }
    class Analyst extends Role {
      override def canAccess(page:String) = false
    }

    //sharing same name with object - companion object
    object Role {
      def apply(roleName:String) = roleName match {
        case "root" => new Root
        case "superAnalyst" => new SuperAnalyst
        case "analyst" => new Analyst
      }
    }

    //explicit apply call
    //object are lazy eveluated like static classes
    val role = Role("root")

  }

  /*
   Another difference between traits and abstract classes in Scala is that an abstract class can have constructor parameters,
   but traits can’t take any parameters. Both can take type parameters, which I discuss in the next chapter.

   The difference between def and val is that val gets evaluated when an object is created,
   but def is evaluated every time a method is called.


   */
  "Scala" should "use traits where def is eveluated every access, val only during creation" in {
    /*trait is compiled to interface or
     Scala generates two class files: one for the interface (as shown in the previous code) and a new class file that
     contains the code.
     When a class extends a trait, the variables declared in the trait are copied to the class file,
     and the method defined in the trait becomes a façade method in the class.
     These façade methods in the class will call the methods defined in the trait code class.
     */
    class Room(val person: Person)
    class Person(var name: String, var surname: String)

    trait DisplayName{
        val person: Person
        val name = person.name
        def surname = person.surname
    }

    val p = new Person("John", "White")
    val r = new Room(p) with DisplayName

    r.name shouldEqual "John"
    r.surname shouldEqual "White"

    p.name = "Mike"
    p.surname = "Black"

    r.name shouldEqual "John"
    r.surname shouldEqual "Black"

  }

  "Scala" should "resolve diamont issue with traits" in {
    //    This is a two-step process in which it resolves method invocation by first using right-first, depth-first search and then removing all but the last occurrence of each class in the hierarchy

    //http://techbus.safaribooksonline.com/9781935182757/ch02lev1sec2_html#X2ludGVybmFsX0h0bWxWaWV3P3htbGlkPTk3ODE5MzUxODI3NTclMkZjaDAzbGV2MXNlYzZfaHRtbCZxdWVyeT0=
    //http://ivanyu.me/wp-content/uploads/2014/12/classhierarchy.png

    /*
    When a trait declares concrete methods or code,
    Scala generates two class files: one for the interface (as shown in the previous code) and a new class file that contains the code.
    When a class extends a trait, the variables declared in the trait are copied to the class file,
    and the method defined in the trait becomes a façade method in the class.
    These façade methods in the class will call the methods defined in the trait code class.

    When discussing class linearization, I didn’t give you the complete picture.
    Scala always inserts a trait called scala.ScalaObject as a last mixin in all the classes you create in Scala.
     */

    class Car

    trait Base{
      def name(): String
    }

    trait FooBase extends Base{
      override def name()="Foo"
    }

    //abstract here is a killer, enables trait stacking
    trait BarBase extends Base{
      abstract override def name()={super.name() + "Bar"}
    }

    val s = new Car() with FooBase with BarBase

    println(s.name())
  }

  "Scala" should "case class" in {
    /*
    Scala prefixes all the parameters with val, and that will make them public value. But remember that you still never access the value directly; you always access through accessors.
    Both equals and hashCode are implemented for you based on the given parameters.
    The compiler implements the toString method that returns the class name and its parameters.
    Every case class has a method named copy that allows you to easily create a modified copy of the class’s instance. You’ll learn about this later in this chapter.
    A companion object is created with the appropriate apply method, which takes the same arguments as declared in the class.
    The compiler adds a method called unapply, which allows the class name to be used as an extractor for pattern matching (more on this later).
    A default implementation is provided for serialization
    You’re allowed to prefix the parameters to the case class with var if you want both accessors and mutators. Scala defaults it to val because it encourages immutability.
     */

    case class Person(firstName:String, lastName: String){
      def foo(arg: String) = arg //identity function
      def bar(arg: => String) = arg //identity function
    }
    object Person {

      def apply(firstName:String, lastName:String) = {
        new Person(firstName, lastName)
      }
      def unapply(p:Person): Option[(String, String)] =
        Some((p.firstName, p.lastName))
    }


    val p = Person("John", "Goodman")

    //for sugar and aunapply working with named parameters
    for(Person(lastName,firstName) <- List(p)){
      println(firstName)
    }



  }

  "Scala" should "use value classes" in {
    /*
    The class must have exactly one val parameter (vars are not allowed).
    The parameter type may not be a value class.
    The class can not have any auxiliary constructors.
    The class can only have def members, no vals or vars.
    The class cannot extend any traits, only universal traits (we will see them shortly).
    */



    //old way
    val w = new Wrapper("hey")

    //this will be transcoded to static object: Wrapper.up$extension("hey")



  }

  "Scala" should "implicit conversionnon" in{
    //this will not work
    //val a:Int = 2.1

    //you can write method for conversion
    def double2int_v1(i: Double): Int = {i.toInt}
    val b: Int = double2int_v1(2.1)



    //you can make thing more implicit
    implicit def double2int_v2(i: Double): Int = {i.toInt}

    val c: Int = 2.1

    //example usge - create custom parametra --> for range creation for instance 1 --> 10 equall to 1 to 10
    //implicit class must have primary constructor with one arguiment
    //
    //should be implicit class, explained later
    //implicit class CustomRangeMarker(val left: Int) extends AnyVal{
    class CustomRangeMarker(left: Int){
      def -->(right: Int) = {left to right}
    }

    new CustomRangeMarker(1).-->(10) shouldEqual (1 to 10)

    implicit def int2CustomRangeMarker(left: Int) = new CustomRangeMarker(left)

    //since Int dont have .--> method compiler will search for implicit class which accepts Int and have --> definition
    val d: Range = 1 --> 10

    //above approach will creat new ImplicitConversion class per each conversion. You can transform it to value class to avoid issue, just prefix with implicit

  }
  "Scala" should "use NUll and Nothing" in{
    //http://techbus.safaribooksonline.com/book/programming/scala/9781935182757/chapter-3dot-oop-in-scala/ch03lev1sec11_html#X2ludGVybmFsX0h0bWxWaWV3P3htbGlkPTk3ODE5MzUxODI3NTclMkZjaDAzbGV2MXNlYzEyX2h0bWwmcXVlcnk9
  }
}

class Wrapper(val name: String) extends AnyVal {
  def up() = name.toUpperCase
}


//it can be accesible in whole app as singleton
package object bar {
  val minimumAge = 18
  def verifyAge = {}
}

//packaging
package com {
  package outerpkg {
    package innerpkg {
      //sealed - final except can be extended in same source file
      sealed class Outer {
        class Inner {
          private[Outer] def f()  = "This is f"
          private[innerpkg] def g() = "This is g"
          private[outerpkg] def h() = "This is h"
          //object private
          private[this] def i() = "This is i"
          //class-private
          private def j() = "This is j"
        }
      }
    }
  }




}