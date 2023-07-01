package lectures.part2afp

object PimpMyLibrary extends App {
  // 2.isPrime

  implicit class RichInt(val value: Int) {
    def isEven: Boolean = value % 2 == 0
    def sqrt: Double = Math.sqrt(value)
    def times(f: () => Unit): Unit = {
      def timesAux(n: Int): Unit = {
        if(n <= 1) f()
        else{
          f()
          timesAux(n - 1)
        }
      }
      timesAux(value)
    }

  }

  new RichInt(42).sqrt
  42.isEven
  // type enrichment = pimping

  1 to 10

  import scala.concurrent.duration._
  3.seconds

  // compiler doesnt do multiple implicit searches

  implicit class RicherInt(richInt: RichInt) {
    def isOdd = richInt.value % 2 != 0
  }

  // cannot do 42.isOdd because

  /**
   * Enrich the String class
   * asInt
   * encrypt
   * - "John" -> Lnjp
   *
   * keep enriching the Int class
   * times(function)
   * 3.times( () => ...)
   */

  implicit class RichString(value: String) {
    def asInt = value.toInt
    def encrypt(cypherDistanct: Int) = value.map(c => (c + cypherDistanct).toChar)
  }
  "42".asInt
  println("William".encrypt(2))


  3.times(() => println("Scala rocks"))

  implicit def stringToInt(s: String): Int = Integer.valueOf(s)

  "3" / 2 // stringToInt("3") / 2

  // equivalent: implicit class RichAltInt(value: Int)
  class RichAltInt(value: Int)
  implicit def enrich(value: Int): RichAltInt = RichAltInt(value)

  // danger zone
  implicit def intToBoolean(value: Int): Boolean = value == 1

  /**
   * if (n) do something
   * else  do something else
   */

  println(if(3) "OK" else "SOmething else")
}
