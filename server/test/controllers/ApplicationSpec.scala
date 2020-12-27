package controllers

import org.scalatestplus.play.PlaySpec
import play.api.test.Helpers.{contentAsString, defaultAwaitTimeout}
import play.api.test.{FakeRequest, Helpers}

class ApplicationSpec extends PlaySpec{
  "Application#index" must {
    "return index page" in {
      val controller = new Application(Helpers.stubControllerComponents())
      val result = controller.index.apply(FakeRequest())
      val body = contentAsString(result)
      body must include ("Play and Scala.js share a same message")
      body must include ("Play shouts out")
      body must include ("Scala.js shouts out")
    }
  }

}
