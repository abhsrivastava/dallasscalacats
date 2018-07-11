package com.abhi

import cats.Eq
import cats.instances.int._
import cats.instances.string._
import cats.instances.option._
import cats.syntax.eq._

object PersonEqInstance {
    implicit val personEqInstance : Eq[Person] = Eq.instance[Person] { (p1, p2) => 
        p1.firstName === p2.firstName && p1.lastName === p2.lastName
    }
}

object Equality extends App {

    val i1 = 10
    val i2 = Option(10)
    
    if (i1 != i2) {
        println("bug!!")
    }

    if (i1 === i1) {
        println(s"numbers are same")
    }

    if (i2 === i2) {
        println(s"numbers are same")
    }

    import PersonEqInstance._
    val p1 = Person("f1", "l1")
    val p2 = Option(Person("f2", "l2"))
    
    if (p1 === p1) {
        println("p1 and p2 is the same person")
    }

    if (p2 === p2) {
        println("p1 and p2 is the same person")
    }
}