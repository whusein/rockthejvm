package lectures.part1afp

object AdvancedPatternMatching extends App {

  val numbers = List(1)

  val description = numbers match {
    case head :: Nil => println(s"the only element is ${head}")
    case _ =>
  }

  /*
  - constant
  - wildcards
  - case classes
  - tuples
  - some special magic like above
  */

  class Person(val name: String, val age: Int)
  object Person{
    def unapply(person: Person): Option[(String, Int)] = Some((person.name, person.age))
    def unapply(age: Int): Option[String] = {
      if(age < 23) Some("minor") else Some("adult")
    }
  }

  val bob = Person("Bob", 37)
  bob match {
    case Person(name, age) => println(s"Hi, my name is ${name} and my age is ${age}")
  }
  bob.age match {
    case Person(status) => println(s"My legal status is ${status}")
  }

  /*
  Exercise
  */
  val n = 10
  val maxProperty = n match {
    case x if x < 10 => "single digit"
    case x if x % 2 == 0 => "even number"
    case _ => "no property"
  }

  object even {
    def unapply(args: Int): Boolean = args % 2 == 0
  }

  object singleDigit{
    def unapply(args: Int): Boolean = args > -10 && args < 10
  }

  val newMathProperty = n match {
    case singleDigit() => "single digit"
    case even() => "even"
    case _ => "no property"
  }

  println(newMathProperty)

}

