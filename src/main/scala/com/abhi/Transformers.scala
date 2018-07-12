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

    implicit class FutureEither[A, B](val e: Future[Either[A, B]]) extends AnyVal {
        def toEitherT  = EitherT(e)
    }
    implicit class FutureEitherOption[A, B](val o: EitherT[Future, A, Option[B]]) extends AnyVal {
        def toOptionT = OptionT(o)
    }
    
    val mt = myFunction().toEitherT.toOptionT
    val fut = for{
        x <- mt
    } yield {
        println(x.getClass.getName)
        println(x)
    }
    Await.result(fut.value.value, Duration.Inf)
}
