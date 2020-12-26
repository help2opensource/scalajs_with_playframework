package controllers

import play.api.mvc.{AbstractController, ControllerComponents}
import services.TaskServiceInMemoryImpl

import javax.inject._

@Singleton
class TasksController @Inject()(cc: ControllerComponents)
  extends AbstractController(cc) {

  def taskList =
    Action { implicit request =>
      val usernameOption = request.session.get("username")
      usernameOption
        .map { username =>
          Ok(views.html.taskList(TaskServiceInMemoryImpl.getTasks(username)))
        }.getOrElse(Redirect(routes.UserService.login()))
    }

  def addTask = Action { implicit request => {
    request.session.get("username").flatMap(login => {
      request.body.asFormUrlEncoded.map(args => {
        val task = args("newTask").head
        TaskServiceInMemoryImpl.addTask(login, task)
        Redirect(routes.TasksController.taskList())
      })
    }).getOrElse(Redirect(routes.UserService.login()))
  }}

  def productDetails(prodName: String, prodNum: Int) =
    Action {
      Ok(s"Product name: $prodName, product number: $prodNum")
    }
}
