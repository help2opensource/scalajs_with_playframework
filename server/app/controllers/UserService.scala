package controllers

import play.api.mvc._
import services.TaskServiceInMemoryImpl

import javax.inject._

@Singleton
class UserService @Inject()(cc: ControllerComponents)
    extends AbstractController(cc) {
  def login =
    Action { implicit request =>
      request.session.get("username") match {
        case Some(_) => Redirect(routes.TaskList.taskList())
        case None    => Ok(views.html.login())
      }
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
                .withSession("username" -> login)
            } else {
            Redirect(routes.UserService.login())
            }
          }
        }
        .getOrElse(Redirect(routes.UserService.login()))
    }

  def register =
    Action { request =>
      request.body.asFormUrlEncoded.map { args =>
        val login = args("login").head
        val password = args("password").head
        TaskServiceInMemoryImpl.createUser(login, password) match {
          case Some(user) =>
            Redirect(routes.TaskList.taskList())
              .withSession("username" -> user.login)
          case None => Redirect(routes.UserService.login())
        }
      }
      Redirect(routes.UserService.login())
    }

  def logout = Action {
    Redirect(routes.UserService.login()).withNewSession
  }
}
