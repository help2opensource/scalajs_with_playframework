package controllers

import play.api.data.Form
import play.api.data.Forms.{mapping, text}
import play.api.mvc._
import services.TaskServiceInMemoryImpl

import javax.inject._

case class RegisterData(login: String, password: String)

@Singleton
class UserService @Inject()(mcc: MessagesControllerComponents)
  extends MessagesAbstractController(mcc) {

  val registerForm = Form(mapping(
    "Login" -> text(3, 12),
    "Password" -> text(8)
  )(RegisterData.apply)(RegisterData.unapply))

  def login =
    Action { implicit request =>
      request.session.get("username") match {
        case Some(_) => Redirect(routes.TasksController.taskList())
        case None => Ok(views.html.login(registerForm))
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
          val login = args("login").head
          val password = args("password").head
          if (TaskServiceInMemoryImpl.validateUser(login, password))
            Redirect(routes.TasksController.taskList())
              .withSession("username" -> login)
              .flashing("success" -> "Successfully logged in.")
          else Redirect(routes.UserService.login())
            .flashing("error" -> "Incorrect login and/or password")
        }.getOrElse(Redirect(routes.UserService.login()))
    }

  def register =
    Action { request =>
      request.body.asFormUrlEncoded.map { args =>
        val login = args("login").head
        val password = args("password").head
        TaskServiceInMemoryImpl.createUser(login, password) match {
          case Some(user) =>
            Redirect(routes.TasksController.taskList())
              .withSession("username" -> user.login)
          case None => Redirect(routes.UserService.login())
            .flashing("error" -> "User creation unsuccessful.")
        }
      }.getOrElse(Redirect(routes.UserService.login()))
    }

  def validateRegisterForm = Action { implicit request => {
    registerForm.bindFromRequest().fold(
      formWithError => BadRequest(views.html.login(formWithError)),
      rd => TaskServiceInMemoryImpl.createUser(rd.login, rd.password) match {
        case Some(user) =>
          Redirect(routes.TasksController.taskList())
            .withSession("username" -> user.login)
        case None => Redirect(routes.UserService.login())
          .flashing("error" -> "User creation unsuccessful.")
      }
    )
  }}

  def logout = Action {
    Redirect(routes.UserService.login()).withNewSession
  }
}
