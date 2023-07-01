package lectures.part2afp

object CurriesPAF extends App {

  // curried function
  def superAdder: Int => Int => Int =
    x => y => x + y

  val add3: Int => Int = superAdder(3) // Int => Int = y = 3 + 7
  println(add3(5))
  println(superAdder(3)(5))

  def curriedAdder(x: Int)(y: Int): Int = x + y

  val add4: Int => Int = curriedAdder(4)
  // lifting = ETA-EXPANSION

  // function != method (JVM limitation)
  def inc(i: Int) = i + 1
  List(1,2,3).map(inc) // ETA-EXPANSION to List(1,2,3).map(x => inc(x))

  // Partial function application
  val add5 = curriedAdder(5)
  println(add5(9))

  //EXERCISE
  val simpleAddFunction = (x: Int, y: Int) => x + y
  def simpleAddMethod(x: Int, y: Int) = x + y
  def curriedAddMethod(x: Int)(y: Int) = x + y

  // add7: Int => Int = y => y + 7
  // as many different variations of add7 using the above

  val add7 = (y: Int) => simpleAddFunction(7, y)
  val add7_2 = simpleAddFunction.curried(7)
  def add7(y: Int) = simpleAddFunction(7, y)
  def add7Curried(y: Int) = curriedAddMethod(7)(y)
  def add7Curried_2 = curriedAddMethod(7) _
  def add7Curried_3 = curriedAddMethod(7)(_) // alternative syntax
  def add7Curried_4 = simpleAddFunction(7, _: Int) // another alternative syntax





}
