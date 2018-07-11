package example

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by e-plbk on 2017-07-27.
  *
  * High level view on scala and it's scalability
  */
class Ch1 extends FlatSpec with Matchers {



  "Scala" should "be scalable" in {

    def loopTill(cond: => Boolean)(body: => Unit): Unit = {
      if (cond) {
        body
        loopTill(cond)(body)
      }
    }

    var i = 10

    loopTill(i > 0) {
      println(i)
      i -= 1
    }
  }

  "Scala" should "be productive langue with default constructor" in {
    class JavaProgrammer {
      private var name: String = null
      private var language: String = null
      private var favDrink: String = null

      def getName: String = name

      def setName(name: String): Unit = {
        this.name = name
      }

      def getLanguage: String = language

      def setLanguage(language: String): Unit = {
        this.language = language
      }

      def getFavDrink: String = favDrink

      def setFavDrink(favDrink: String): Unit = {
        this.favDrink = favDrink
      }
    }

    //var generates getters and setters
    //val generates only setters
    //if none specified, its private field
    class ScalaProgrammer(var name: String, var language: String, var favDrink: String, private var car: String)

    val x = new ScalaProgrammer("Adrian", "Scala", "Blood Marry", "VW T1")

    //field is accessible
    x.name = "Andrzej"
    x.name shouldEqual "Andrzej"
    //compilation issue on modification attempt
    //x.car = "Fiat"
  }

  "Scala" should "be dynamic language, call non existing methods like groovy" in {
    import scala.language.dynamics

    class Text extends Dynamic{
      def applyDynamic(s: String)(o: Any) = f"Called function $s with parameter $o"
    }

    val t = new Text();

    t.fakeMethod("fakeParam") shouldEqual "Called function fakeMethod with parameter fakeParam"
  }

  "Scala" should "touple" in{
    val t= Tuple2(1,2)

    println(t._1)
    println(t._2)

  }
}
