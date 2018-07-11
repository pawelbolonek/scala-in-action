package example {

  import org.scalatest._

  import scala.reflect.runtime.universe._
  import scala.xml.Elem
  import example.list.MyList
  import example.list.WritableMyList
  import example.list.SecondTraitMyList

  import scala.annotation.tailrec

  //http://www.scalatest.org/user_guide/selecting_a_style
  //http://www.scalatest.org/user_guide/using_matchers

  //Scala puts any inline code defined inside the class into the primary constructor of the class
  //scala.AnyRef is a main class in scala
  //One more interesting point to note here is that Scala package declaration doesn’t have to match the folder structure of your filesystem. You’re free to declare multiple packages in the same file:
  //In Scala, import doesn’t have to be declared at the top of the file; you could use import almost anywhere


  // import java.lang.System._ similar to static import in Java
  // import java.util.Date
  // below making alias
  // import java.sql.{Date => SqlDate}
  //
  // hiding class, will not be possible to use
  // import java.sql.{Date => _ }
  //
  // below will not work cause new uses package context, fix is: val m = new _root_.monads.IOMonad
  //package io {
  //  package monads {
  //    class Console { val m = new monads.IOMonad }
  //  }
  //}
  //
  //The difference between def and val is that val gets evaluated when an object is created, but def is evaluated every time a method is called.
  //In scala == calls always equals method
  //.copy method allow cop object everriding each field using named arguments

  //access modificators
  //private[com.lang] - com .lang and any subpackages can access it
  //pirvate[this] - object private, private is class private
  //by default everything is public
  //The override modifier can be combined with an abstract modifier, and the combination is allowed only for members of trait
  //sealead classes are almost final, they can be extended by classes from same file

  //persistent = immutable
  //A total function is one that knows how to handle all the values of an algebraic data type and always produces a result
  //TODO: read PartialFunction

  class HelloSpec extends FlatSpec with Matchers {


    "Scala" should "init map" in {
      val m1 = Map((1, "1"), (2, "2"))
      val m2 = Map(1 -> "1", 2 -> "2")

      m2 filter {(k) => true}


      def bolo(a: Int, p: Int => String, k: String => String): Unit ={
        p(1);
        k("1");
      }

      bolo(1, { x => println("one"); "dziala"}, {y =>println("two"); "dziala"})

    }







  }
//One more interesting point to note here is that Scala package declaration doesn’t have to match the folder structure of your filesystem. You’re free to declare multiple packages in the same file:
//In Scala, import doesn’t have to be declared at the top of the file; you could use import almost anywhere:
  package com.persistence {
    package mongo {
      class MongoClient
    }
    package riak {
      class RiakClient
    }
    package hadoop {
      class HadoopClient
    }
  }
}