package exercises

import lectures.part2afp.TypeClass.User

object EqualityPlayground {

  /**
   * Equality
   */

  trait Equal[T] {
    def apply(a: T, b: T): Boolean
  }

  implicit object NameEquality extends Equal[User] {
    def apply(a: User, b: User) = a.name == b.name
  }

  object Equal {
    def apply[T](a: T, b: T)(implicit equalizer: Equal[T]) = equalizer.apply(a, b)
  }

  println(Equal(User("William", 37, "w"), User("William", 35, "j")))
}
