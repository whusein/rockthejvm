package lectures.part2afp

import lectures.part2afp.FunctionalCollection.MySet

import scala.annotation.tailrec

object FunctionalCollection extends App {

  trait MySet[A] extends (A => Boolean) {
    def apply(elem: A) = contains(elem)
    def contains(elem: A): Boolean
    def +(elem: A): MySet[A]
    def ++(anotherSet: MySet[A]): MySet[A]

    def map[B](f: A => B): MySet[B]
    def flatMap[B](f: A => MySet[B]): MySet[B]
    def filter(predicates: A => Boolean): MySet[A]
    def foreach(f: A => Unit): Unit
  }

  class EmptySet[A] extends MySet[A]{
    def contains(elem: A): Boolean = false
    def +(elem:A): MySet[A] = new NonEmptySet[A](elem, this)
    def ++(anotherSet: MySet[A]) = anotherSet

    def map[B](f: A => B): MySet[B] = new EmptySet[B]
    def flatMap[B](f: A => MySet[B]): MySet[B] = new EmptySet[B]
    def filter(predicates: A => Boolean): MySet[A] = this
    def foreach(f: A => Unit): Unit = ()
  }

  class NonEmptySet[A](head: A, tail: MySet[A]) extends MySet[A]{
    def contains(elem: A): Boolean = elem == head || tail.contains(elem)
    def +(elem: A): MySet[A] =
      if(this.contains(elem)) this
      else new NonEmptySet[A](elem, this)
    def ++(anotherSet: MySet[A]): MySet[A] = tail ++ anotherSet + head

    def map[B](f: A => B): MySet[B] = tail.map(f) + f(head)
    def flatMap[B](f: A => MySet[B]): MySet[B] = tail.flatMap(f) ++ f(head)
    def filter(predicates: A => Boolean): MySet[A] = {
      val filteredTail = tail filter predicates
      if(predicates(head)) filteredTail + head
      else filteredTail
    }
    def foreach(f: A => Unit): Unit =
      f(head)
      tail foreach f
  }

  object MySet {
    def apply[A](values: A*) = {
      @tailrec
      def buildSeq(valSeq: Seq[A], acc: MySet[A]): MySet[A] = {
        if (valSeq.isEmpty) acc
        else buildSeq(valSeq.tail, acc + valSeq.head)
      }

      buildSeq(values.toSeq, new EmptySet[A])
    }
  }
}

object MySetTesting extends App {
  val s = MySet(1,2,3,4,5)
  s + 6 ++ MySet(-1, -2) + 3 flatMap (x => MySet(x, x * 10))  filter (_ % 2 == 0) foreach println
}
