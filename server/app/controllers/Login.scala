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

  def validateGet(login: String, password: String) =
    Action {
      Ok(s"Validated user: $login with password: $password")
    }

  def validatePost =
    Action { request =>
      request.body.asFormUrlEncoded
        .map { args =>
          {
            val login = args.get("login")
            val password = args.get("password")
            println(s"Logged in user: $login with password $password")
          }
          Redirect(routes.TaskList1.taskList())
        }
        .getOrElse(Redirect(routes.Login.login()))
    }
}
