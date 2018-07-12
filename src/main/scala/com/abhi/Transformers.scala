package com.abhi

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

import cats.data.OptionT
import cats.data.EitherT
import cats.implicits._
import scala.concurrent.duration._

object Transformers extends App {
    def myFunction() : Future[Either[String, Option[Int]]] = {
        Future(Right(Some(1)))
    }

    // I want Future[Option[Int]]
    val result : Future[Option[Int]] = for {
        eitherOptionVal <- myFunction()
    } yield {
        eitherOptionVal.fold(
            l => Option.empty[Int],
            r => r
        )
    }

    type FutureEither[A] = EitherT[Future, String, A]
    type FutureEitherOption[A] = OptionT[FutureEither, A]
    implicit class FutureEitherOps[A, B](val e: Future[Either[A, B]]) extends AnyVal {
        def toEitherT = EitherT(e)
    }
    implicit class FutureEitherOptionOps[A, B](val o: EitherT[Future, A, Option[B]]) extends AnyVal {
        def toOptionT = OptionT(o)
    }
    
    val mt = myFunction().toEitherT.toOptionT
    for{
        x <- mt
    } yield {
        println(x.getClass.getName)
        println(x)
    }

    // another approach to create mt

    val mt2 : FutureEitherOption[Int] = 10.pure[FutureEitherOption]
    for{
        x <- mt2
    } yield {
        println(x.getClass.getName)
        println(x)
    }
}
