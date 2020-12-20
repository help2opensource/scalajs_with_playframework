package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class TaskList1 @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def taskList = Action {
    Ok(views.html.taskList(List("Task 1", "Task 2", "Task 3")))
  }

  def productDetails(prodName: String, prodNum: Int) = Action {
    Ok(s"Product name: $prodName, prduct number: $prodNum")
  }
}
