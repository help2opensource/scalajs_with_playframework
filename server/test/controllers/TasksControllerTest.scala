package controllers

import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import org.scalatestplus.play.{HtmlUnitFactory, OneBrowserPerSuite, PlaySpec}

class TasksControllerTest extends PlaySpec with GuiceOneServerPerSuite with OneBrowserPerSuite with HtmlUnitFactory {
  "TaskController" must {
    "login and access functions" in {
      go to s"http://localhost:$port/login"
      pageTitle mustBe "Login"
      clickOn("login-login-post")
      textField("login-login-post").value = "Ksawery"
      clickOn("password-login-post")
      textField("password-login-post").value = "Pass"
      submit()
      pageTitle mustBe "Task list"
      findAll(cssSelector("li")).toList.map(_.text) mustBe List("Eat", "Sleep", "Code", "Bruh")
    }
  }
}
