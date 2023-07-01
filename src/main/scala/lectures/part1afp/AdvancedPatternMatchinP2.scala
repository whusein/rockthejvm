package lectures.part1afp

import lectures.part1afp.AdvancedPatternMatching._


object AdvancedPatternMatchinP2 extends App {
  // infex pattern
  case class Or[A, B](a: A, b: B)
  val either = Or(2, "Two")
  either match {
    //case Or(number, string) => println(s"${number} is written as $string")
    case number Or string => println(s"${number} is written as $string")
  }

  val numbers = List(1,2,3,4,5)
  // decomposing sequences
  val vararc = numbers match {
    case List(1, _*) => "starting with 1"
  }
  abstract class MyList[+A] {
    def head: A = ???
    def tail: MyList[A] = ???
  }
  case object Empty extends MyList[Nothing]
  case class Cons[+A](override val head: A, override val tail: MyList[A]) extends MyList[A]

  object MyList{
    def unapplySeq[A](myList: MyList[A]): Option[Seq[A]] =
      if(myList == Empty) Some(Seq.empty)
      else unapplySeq(myList.tail).map(myList.head +: _)
  }

  val myList = Cons(1, Cons(2, Cons(3, Empty)))
  val decomposedMyList = myList match {
    case MyList(1, _*) => println("My list starting with 1")
  }

  // custom return type for unapply
  // isEmpty: Boolean, get: something

  abstract class Wrapper[T] {
    def isEmpty: Boolean
    def get: T
  }

  object PersonWrapper {
    def unapply(person: Person): Wrapper[String] = new Wrapper[String]{
      def isEmpty = false
      def get = person.name
    }
  }

  println(bob match {
    case PersonWrapper(b) => println(s"this person name is ${b}")
    case _ => "an alien"
  })


}
