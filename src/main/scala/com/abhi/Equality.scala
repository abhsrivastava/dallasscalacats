import cats.Eq
import cats.instances.int._
import cats.instances.option._
import cats.syntax.eq._

object Equality extends App {
    val x = 10
    val y = Option(10)
    if (x != y) {
        println(s"you compared wrong types")
    }
    val eq1 = Eq[Int]
    val eq2 = Eq[Option[Int]]
    eq1.eqv(x, x)
    eq2.eqv(y, y)
    eq1.eqv(x, y)
    eq2.eqv(x, y)

    if (x =!= y) {
        println(s"you are comparing wrong types")
    }
}