package controllers

import play.api.mvc.{AbstractController, ControllerComponents}

import javax.inject._


@Singleton
class TaskList @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def taskList = Action {
    Ok(views.html.taskList(List("Task 1", "Task 2", "Task 3")))
  }

  def productDetails(prodName: String, prodNum: Int) = Action {
    Ok(s"Product name: $prodName, prduct number: $prodNum")
  }
}
