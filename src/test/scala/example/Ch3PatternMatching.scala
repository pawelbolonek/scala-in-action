package example

import java.net.URL

import org.scalatest.{FlatSpec, Matchers}

import scala.io.Source

/**
  * Created by e-plbk on 2017-07-11.
  */
class Ch3PatternMatching  extends FlatSpec with Matchers {

  sealed abstract class Expression
  case class X() extends Expression
  case class Const(value : Int) extends Expression
  case class Add(left : Expression, right : Expression) extends Expression
  case class Mult(left : Expression, right : Expression) extends Expression
  case class Neg(expr : Expression) extends Expression

  def eval(expression : Expression, xValue : Int) : Int = expression match {
    case X() => xValue
    case Const(cst) => cst
    case Add(left, right) => eval(left, xValue) + eval(right, xValue)
    case Mult(left, right) => eval(left, xValue) * eval(right, xValue)
    case Neg(expr) => - eval(expr, xValue)
  }

  def deriv(expression : Expression) : Expression = expression match {
    case X() => Const(1)
    case Const(_) => Const(0)
    case Add(left, right) => Add(deriv(left), deriv(right))
    case Mult(left, right) => Add(Mult(deriv(left), right), Mult(left, deriv(right)))
    case Neg(expr) => Neg(deriv(expr))
  }

  "Scala" should "match pattern" in {
    val expr = Add(Const(1), Mult(Const(2), Mult(X(), X())))  // 1 + 2 * X*X
    assert(eval(expr, 3) == 19)
    val df = deriv(expr)
    assert(eval(df, 3) == 12)

    val expr2 = X()

    eval(X(),1)
  }

  "Scala" should "either case class" in{
    def getContent(url: URL): Either[String, Source] =
      if (url.getHost.contains("google"))
        Left("Requested URL is blocked for the good of the people!")
      else
        Right(Source.fromURL(url))

    getContent(new URL("http://google.com")) match {
      case Left(msg) => println(msg)
      case Right(source) => source.getLines.foreach(println)
    }
  }

}
