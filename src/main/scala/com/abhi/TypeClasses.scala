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

// 3. Interace Object OR Interface Syntax 
object ShowSyntax {
  implicit class ShowOps[A](a: A) {
    def show()(implicit s: Show[A]) : String = {
      s.show(a)
    }
  } 
}

object TypeClasses extends App {
  import com.abhi.ShowInstances._
  import com.abhi.ShowSyntax._
  val p = Person("Foo", "Bar")
  println(p.show())
}