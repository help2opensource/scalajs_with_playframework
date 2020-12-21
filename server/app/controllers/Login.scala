package controllers

import play.api.mvc._
import services.TaskServiceInMemoryImpl

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
            val login = args("login").head
            val password = args("password").head
            if (TaskServiceInMemoryImpl.validateUser(login, password)) {
              Redirect(routes.TaskList.taskList())
            } else {
              Redirect(routes.Login.login())
            }
          }
        }
        .getOrElse(Redirect(routes.Login.login()))
    }
}
