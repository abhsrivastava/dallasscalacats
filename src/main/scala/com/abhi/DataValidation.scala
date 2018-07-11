package com.abhi

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import cats.data.ValidatedNel
import cats.instances.list._
import cats.syntax.validated._
import cats.syntax.apply._

// rules
// 1. name and surname should not be empty
// 2. date of birth should be a valid date
// 3. date of birth cannot be in the future
// 4. person must be 18 years old.

case class RegistrationRequest(name: String, surname: String, dateOfBirth: String)
case class ValidUser(name: String, surname: String, date: DateTime)
case class InvalidRequest(errors: List[String])

object DataValidation extends App {
    type ValidatedResult[A] = ValidatedNel[String, A]

    def validateString(name: String)(value: String) : ValidatedResult[String] = {
        if (value.nonEmpty) value.validNel else s"$name cannot be empty".invalidNel
    }
    def validateDate(name: String)(dateStr: String) : ValidatedResult[DateTime] = {
        val fmt = DateTimeFormat.forPattern("MM/dd/yyyy")
        try {
            val dt = fmt.parseDateTime(dateStr)
            if (dt.plusYears(18).isBefore(DateTime.now.withTimeAtStartOfDay)) 
                dt.validNel 
            else s"Not 18 years old".invalidNel
        } catch {
            case ex: Exception => s"${ex.getMessage}".invalidNel
        }
    }
    def validate(request: RegistrationRequest) : ValidatedResult[ValidUser] = {
        val v1 = validateString("name")(request.name)
        val v2 = validateString("surname")(request.surname)
        val v3 = validateDate("Date of Birth")(request.dateOfBirth)
        (v1, v2, v3).mapN(ValidUser.apply)
    }


    println(validate(RegistrationRequest("", "", "12/12/2018")).leftMap{x => InvalidRequest(x.toList)})
}