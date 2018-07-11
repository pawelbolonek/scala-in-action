package example.ignore

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by e-plbk on 2017-07-18.
  */
class PriceCalculatorTest extends FlatSpec with Matchers {

  "PriceCalculator" should "work without monad" in {
    println(PriceCalculatorWihoutMonad.calculatePrice("id", "code"))
  }

}

object PriceCalculatorWihoutMonad{

  import StubsWithoutMonad._
  case class PriceState(productId: String, stateCode:String, price: Double)

  def findBasePrice(productId: String, stateCode:String): PriceState ={
    val basePrice = findTheBasePrice(productId)
    PriceState(productId, stateCode, basePrice)
  }

  def applyStateSpecificDiscount(ps: PriceState): PriceState ={
    val discount = findStateSpecificDiscount(ps.productId, ps.stateCode)
    ps.copy(price = ps.price-discount)
  }

  def applyProductSpecificDiscount(ps: PriceState): PriceState ={
    val discount = findProductSpecificDiscount(ps.productId)
    ps.copy(price = ps.price-discount)
  }

  def applyTax(ps: PriceState): PriceState ={
    val tax = calculateTax(ps.productId, ps.price)
    ps.copy(price = ps.price+tax)
  }

  def calculatePrice(productId: String, stateCode: String): Double = {
    val a = findBasePrice(productId, stateCode)
    val b = applyStateSpecificDiscount(a)
    val c = applyProductSpecificDiscount(b)
    val d = applyTax(c)
    d.price
  }

}

object StubsWithoutMonad {
  def findTheBasePrice(productId: String) = 10.0
  def findStateSpecificDiscount(productId: String, stateCode: String) = 0.5
  def findProductSpecificDiscount(productId: String) = 0.5
  def calculateTax(productId: String, price: Double) = 5.0
}

object StateMonad{

  import State._
  trait State[S, +A]{
    def apply(s: S):(S, A)
    def map[B](f: A=> B): State[S, B] =
      state(apply(_) match{
        case (s, a) => (s, f(a))
    })
    def flatMap[B](f: A => State[S, B]): State[S, B] =
      state(apply(_) match{
        case (s, a) => f(a)(s)
      })
  }

  object State {
    def state[S, A](f: S=> (S, A)) = new State[S, A]{
      def apply(s: S) = f(s)
    }
    def init[S]: State[S, S] = state[S, S](s=>(s,s))
    def modify[S](f: S=> S) = init[S] flatMap (s=> state(_ => (f(s), ())))
  }

}



