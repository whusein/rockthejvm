package lectures.part1afp

import DarkSugars.{Composite, TeenGirl}

import scala.util.{Failure, Success, Try}

object DarkSugars extends App {

  // methods with single parameters
  def singleArgMethod(n: Int) = println(s"$n little ducks")

  singleArgMethod{
    println("calling single arg method")
    42
  }

  val aTryInstance = Try {
    10/0
  } match {
    case Success(x) => println(s"${x}")
    case Failure(x) => println(s"Failed to process with error: ${x.getMessage}")
  }

  // syntax sugar #2: single abstract method
  trait Action {
    def act(x: Int): Int
  }

  val anInstance: Action = new Action {
    override def act(x: Int) = x + 1
  }

  val aFunkyInstance: Action = (x: Int) => x + 1

  val aThread = new Thread{ new Runnable {
    override def run(): Unit = println("In a separate thread")
    }
  }

  val funkyThread: Thread = new Thread(() => println("In a funky thread"))
  funkyThread.run()
  funkyThread.join()

  abstract class AnAbstractType {
    def implemented: Int = 23
    def unimplemented(x: Int): Unit = ???
  }

  // syntax sugar 3: :: and #::
  val prependedList = 2 :: List(3,4)
  //2.::(List(3,4))
  // special scala spec, if method name ends with :, it means right associative
  1 :: 2 :: 3 :: List(4,5)
  // List(4,5).::(3).::(2).::(1)

  class MyStream[T] {
    def -->:(value: T): MyStream[T] = this
  }

  val myStream = 1 -->: 2 -->: 3 -->: new MyStream[Int]

  // syntax sugar 4: multi-word method naming

  class TeenGirl(name: String) {
    def `and then said`(gossip: String) = println(s"${name} said ${gossip}")
  }
  val teenGirl = TeenGirl("Lily")
  teenGirl `and then said` "Scala is so sweet"

  // syntax sugar #5: infix types
  class Composite[A, B]
  val composite: Int Composite String = ???

  class -->[A,B]
  val towards: Int --> String = ???

  // syntax sugar #6: update method, special much like apply
  val anArray = Array(1,2,3)
  anArray(2) = 7 // rewritten into anArray.update(2,7)
  // update is used in mutable collections

  // syntax sugar #7: setters
  class Mutable {
    private var internalMember: Int = 0 // private for OO encapsulation
    def member = internalMember // the getter
    def member_=(value: Int): Unit = internalMember = value
  }

  val mutableContainer = new Mutable
  mutableContainer.member = 42 // rewritten to mutableContainer.member_=(42)

}
