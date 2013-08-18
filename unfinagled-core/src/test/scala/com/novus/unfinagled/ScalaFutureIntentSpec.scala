package com.novus.unfinagled

import org.scalatest.GivenWhenThen
import org.scalatest.matchers.ShouldMatchers
import unfiltered.response._
import unfiltered.request._
import scala.concurrent._

class ScalaFutureIntentSpec extends FinagleServed[FuturePlan.Intent] with GivenWhenThen with ShouldMatchers {

  implicit val executionContext = ExecutionContext.Implicits.global

  def service = UnfilteredService(intent)

  def intent = {
    case GET(Path("/ping")) => Future {
      ResponseString(http(host / "ping-req" as_str))
    }
    case GET(Path("/ping-req")) => Future(ResponseString("pong"))
  }

  feature("Scala Futures front-end") {
    scenario("GET ping") {
      http(host / "ping" as_str) should be("pong")
    }
  }

}