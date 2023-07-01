package lectures.part2afp

object TypeClass extends App {
  trait HTMLWritable {
    def toHTML: String
  }

  case class User(name: String, age: Int, email: String) extends HTMLWritable {
    override def toHTML: String = s"<div>${name} (${age} yo) <a href=$email/></div>"
  }

  User("William", 37, "william.husein@gmail.com").toHTML

  /*
  - 1: Only work for the type that we write
  - 2: ONE implementation out of quite a number
  */

  /*  object HTMLSerializer {
    def serializeToHTML(value: Any) = value match {
      case User(a,b,c) =>
      case _ =>
    }
  }*/

  /**
   * lost type safety
   * need to modify the code everytime
   * still ONE implementation for each
   */

  trait HTMLSerializer[T] {
    def serialize(value: T): String
  }

   object UserSerializer extends HTMLSerializer[User] {
    override def serialize(user: User): String = s"<div>${user.name} (${user.age} yo) <a href=$user.email/></div>"
  }

  println(UserSerializer.serialize(User("Jon", 32, "john@google.com")))

  /**
   * We can define serializer for other types
   */

  import java.util.Date

  object DateSerializer extends HTMLSerializer[Date] {
    override def serialize(value: Date): String = s"<div>${value.toString}</div>"
  }

  /**
   * we can define MULTIPLE serializers
   */

  implicit object PartialUserSerializer extends HTMLSerializer[User] {
    override def serialize(value: User): String = s"<div>${value.name}</div>"
  }

  // part 2
  object HTMLSerializer {
    def serialize[T](value: T)(implicit serializer: HTMLSerializer[T]): String =
      serializer.serialize(value)
    def apply[T](implicit serializer: HTMLSerializer[T]) = serializer
  }

  implicit object IntSerializer extends HTMLSerializer[Int] {
    override def serialize(value: Int): String = s"<div style: color=blue>$value</div>"
  }
  println(HTMLSerializer.serialize(42))

  // have access to the entire HTMLSerializer class
  println(HTMLSerializer[User].serialize(User("William", 37, "william.husein@gmail.com")))

  /**
   * Implement TC pattern for Equality tc
   */

  // part 3
  implicit class HTMLEnrichment[T](value: T) {
    def asHTML(implicit serializer: HTMLSerializer[T]): String  = serializer.serialize(value)
  }

  val john = User("John", 25, "John@google.com")
  println(john.asHTML) // COOOOL

  /**
   * extends to new type
   * choose implementation
   */

  println(2.asHTML)

  println(john.asHTML(UserSerializer))
}
