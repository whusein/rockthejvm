package lectures.part2afp

import lectures.part1afp.AdvancedPatternMatching.Person

import scala.io.{Source, StdIn}

object PartialFunction extends App {

  println("Apa kabar")
  println(s"Reesult of aPartialFunction(1): ${aPartialFunction(1)}")

  def aFunction = (x: Int) => x + 1 // Function1[Int,Int] === Int => Int
  def aFussyFunction = (x: Int) =>
    if(x == 1) 42
    else if(x == 2) 56
    else if(x == 5) 999
    else throw new FunctionNotApplicableException

    class FunctionNotApplicableException extends Exception

    def aNicerFussyFuncion = (x: Int) => x match {
      case 1 => 52
      case 2 => 56
      case 5 => 999
    }

  def aPartialFunction: PartialFunction[Int, Int] = {
      case 1 => 52
      case 2 => 56
      case 5 => 999
  }

  println(aPartialFunction(1))
  println(aPartialFunction.isDefinedAt(898))

  val lifted = aPartialFunction.lift
  println(lifted(1))
  println(lifted(99))

  val pfChain = aPartialFunction.orElse[Int,Int]{
    case 45 => 120
  }
  println(pfChain(45))

  // PF extends normal function

  val aTotalFunction: Int => Int = {
    case 45 => 120
  }

  // HOF accepts PF
  val aMappedList = List(1,2,3).map{
    case 1 => 41
    case 2 => 42
    case 3 => 1000
  }
  println(aMappedList)

  /*
  PF can only have one parameter type
  */
  /**
   * Exercise
   * 1 - contruct a PF as anonymous function
   * 2 - dumb chatbot as PF
   */

  def aManualPartialFunction: PartialFunction[Int, Int] = new PartialFunction [Int, Int] {
    override def apply(x: Int) = x match {
      case 1 => 45
      case 2 => 46
      case 5 => 999
    }

    override def isDefinedAt(x: Int): Boolean = x == 1 || x == 2 || x == 5
  }

  def chatBot: PartialFunction[String, String] = {
    case "Hello" => "Hi, I am a chatbot"
    case "What is your name?" => "I am a chatbot, i don't have a name"
  }
  //Source.stdin.getLines.map(chatBot).foreach(println)

  val myAnyList = List("1", 1, true, "123", 3, List(1,2,3))

  println(myAnyList.collect {case x: Boolean => x})
  println(myAnyList.collect {case x: List[Int] => x.mkString("-") })
}
