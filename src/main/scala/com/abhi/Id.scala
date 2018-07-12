package com.abhi

import cats.Monad
import cats.Id
import cats.implicits._
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object Id extends App {

    def addOld(x: Int, y: Int) : Future[Int] = Future(x + y)
    
    def add[F[_]](x: Int, y: Int)(implicit m : Monad[F]) : F[Int] = {
        Monad[F].pure(x + y)
    }

    val x : Int = add[Id](1, 1)
    println(x)

    val y : Future[Int] = add[Future](1, 1)
    val z = Await.result(y, Duration.Inf)
    println(z)
}