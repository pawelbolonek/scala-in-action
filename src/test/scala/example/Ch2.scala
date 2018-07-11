package example

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by e-plbk on 2017-07-27.
  */
class Ch2 extends FlatSpec with Matchers {

  "Scala" should "use following literals" in {
    //Once initialized a val can’t be changed or reassigned to some other value (similar to final variables in Java)
    val _integer = 11235
    //compilation error
    //_integer = 11236

    var _long = 11235L
    //reasigment works fine
    _long = 11236L

    val _hex = 0x23
    val _double = 0.2d
    val _float = 0.3f
    val _char1 = '\102'
    val _char2 = 'B'
    val multiline =
      """multiline
        |a
        | b
        |  c
        |   d
      """.stripMargin
    println(multiline)
  }

  "Scala" should "support string interpolation" in {
    val name = "Jan"
    val age = 18f

    s"$name $age" shouldEqual "Jan 18.0"

    f"$name $age%2.2f" shouldEqual "Jan 18,00"
  }

  "Lazy declrations " must "be val not var" in {
    var i = 1
    lazy val b = i + 1
    i = 2
    b shouldEqual 3
  }

  "Scala" should "support xml literals" in {
    val book = <book>
      <title>Scala in Action</title>
      <author>Nilanjan Raychaudhuri</author>
    </book>

    val message = "I didn't know xml could be so much fun"
    val code = "1"

    val alert = <alert>
      <message priority={code}>{message}</message>
      <date>{new java.util.Date()}</date>
    </alert>
  }

  "Scala" should "function definition" in{

    def foo1(): String = {"foo1"}
    def foo2() = {"but compiler can infer return type"}
    def foo3 = "Function definition can be really short"

    foo1() shouldEqual "foo1"
    foo2 shouldEqual "but compiler can infer return type"
    foo3 shouldEqual "Function definition can be really short"

    def foo4(){"Without equality sign there is no return type inference. Scala generates Unit (void)"}

    // will not work case its void
    //foo3 shouldEqual ??

    def genericSingletonListFactory[A](item: A){List[A](item)}

    //multiply all items and double at the begining
    val l = List(1,2,3,4)
    l.foldLeft(2){(x,y) => x*y} shouldEqual 48
    //with sugar
    l.foldLeft(2){_*_} shouldEqual 48
  }

  "Scala" should "mimic break operation" in {
    val breakException = new RuntimeException("break exception")

    def breakable(op: => Unit) {
      try {
        op
      } catch { case _ => }
    }

    def break = throw breakException

    breakable {
      val env = System.getenv("SCALA_HOME")
      if(env == null) break
      println("found scala home lets do the real work")
    }


  }

  "Scala" should "use abstract list from autoimported Predef class" in {
      List(1) shouldEqual scala.collection.immutable.List(1)
      //list is abstract. There are two impls: Nill and $colon$colon
      List(1).getClass().getName shouldEqual "scala.collection.immutable.$colon$colon"
      List().getClass().getName shouldEqual "scala.collection.immutable.Nil$"

    ((3 :: List(1)) :+ 2) shouldEqual List(3,1,2)

    //GOTO: List.scala line 447 - how unapply works with $colon$colon case class and constructor
    val l = 1 :: 2 :: Nil //:: is left append operator so we need end with explicit list
  }

  "Scala" should "not use ? operator" in {
    val someValue = if(2==1) "Title1" else "Title 2"
  }

  "Scala" should "have for loops" in {
    case class Book(title:String, author: String)

    val books = List(Book("Dark Tower","King"), Book("Krzyzacy","Sienkiewicz"), Book("Green mile","King"))

    val authorsWhoHaveWrittenAtLeastTwoBooks = for {
      b1 <- books       //gen
      b2 <- books       //gen
      if b1 != b2       //filter
      if b1.author == b2.author   //filter
    } yield b1

    println(authorsWhoHaveWrittenAtLeastTwoBooks)

  }

  "Scala" should "for loop translation" in {
    val artists = List(("Pink floyd", "Rock"), ("Depeche Mode", "Electro"),("Nirvana","Rock"), ("Deep purple", "Other"));


    val newList1 = for(Tuple2(artist, genre) <- artists; if(genre == "Rock") ) yield artist

    //its translated to:
    val newList = artists filter {
      case Tuple2(artist, genre) => genre == "Rock"
    } map {
      case Tuple2(artist, genre) => artist
    }

    println(newList)
    println(newList1)
  }

  "Scala" should "nested loops" in{
    val artistsWithAlbums = List(
      Tuple2(Tuple2("Pink Floyd", "Rock"),
        List("Dark side of the moon", "Wall")),
      Tuple2(Tuple2("Led Zeppelin", "Rock"),
        List("Led Zeppelin IV", "Presence")),
      Tuple2(Tuple2("Michael Jackson", "Pop"),
        List("Bad", "Thriller")),
      Tuple2(Tuple2("Above & Beyond", "trance"),
        List("Tri-State", "Sirens of the Sea"))
    )
    for { Tuple2(Tuple2(artist, genre), albums) <- artistsWithAlbums
          album <- albums
          if(genre == "Rock")
    } println(album)


    artistsWithAlbums flatMap {
      case Tuple2(Tuple2(artist, genre), albums) => albums withFilter {
        album => genre == "Rock"
      } map { case album => println(album) }
    }


  }


  "Scala" should "use pattern matching" in {

      def pattern(x: Any) ={
          x match  {
          case s: List[Int] => "List1"
          case k: List[String] => "List2"
          case i: Int => "Int"
          case _ => "Other"
          }
        }

      pattern(List(1)) shouldEqual "List1"
      //beware below
      pattern(List("a")) shouldEqual "List1"
      pattern(1) shouldEqual "Int"
      pattern(1.0) shouldEqual "Other"
    }


  "Scala" should "use pattern matching extended" in {
    val suffixes = List("th", "st", "nd", "rd", "th", "th", "th", "th", "th","th")

        def ordinal(number:Int) = number match {
          case tenTo20 if 10 to 20 contains tenTo20 => number + "th"
          case rest => rest + suffixes(number % 10)
        }

      ordinal(12) shouldEqual "12th"
  }



}
