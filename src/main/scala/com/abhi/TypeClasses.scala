package com.abhi

case class Person(firstName: String, lastName: String)

// 1. trait
trait Show[A] {
  def show(a: A) : String
}

// 2. instance 
object ShowInstances {
  implicit val personShow : Show[Person] = new Show[Person] {
    def show(p: Person) : String = {
      s"FirstName: ${p.firstName} LastName: ${p.lastName}"
    }
  }
}

// 3. nterface Syntax 
object ShowSyntax {
  implicit class ShowOps[A](a: A) {
    def show()(implicit s: Show[A]) : String = {
      s.show(a)
    }
  }
}

// 3.1 Interace Object 
object Show {
  def show[A](a: A)(implicit inst: Show[A]) : String = {
    inst.show(a)
  }
}

object TypeClasses extends App {
  import com.abhi.ShowInstances._
  import com.abhi.ShowSyntax._
  // invoke typeclass approach 1
  val p = Person("Foo", "Bar")
  println(p.show())
  // invoke typeclass approach 2
  println(Show.show(p))
}