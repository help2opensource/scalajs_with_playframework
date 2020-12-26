package controllers

import play.api.mvc.{AbstractController, ControllerComponents}
import services.TaskServiceInMemoryImpl

import javax.inject._

@Singleton
class TaskList @Inject()(cc: ControllerComponents)
  extends AbstractController(cc) {

  def taskList =
    Action { implicit request =>
      val usernameOption = request.session.get("username")
      usernameOption
        .map { username =>
          Ok(views.html.taskList(TaskServiceInMemoryImpl.getTasks(username)))
        }.getOrElse(Redirect(routes.UserService.login()))
    }

  def productDetails(prodName: String, prodNum: Int) =
    Action {
      Ok(s"Product name: $prodName, product number: $prodNum")
    }
}
