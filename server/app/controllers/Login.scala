package controllers

import play.api.mvc._
import javax.inject._

@Singleton
class Login @Inject() (cc: ControllerComponents)
    extends AbstractController(cc) {
  def login =
    Action {
      Ok(views.html.login())
    }

  def validate(login: String, password: String) =
    Action {
      Ok(s"Validated user: $login with password: $password")
    }
}
