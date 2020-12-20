package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class TaskList1 @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def taskList = Action {
    Ok("Finish this endpoint")
  }
}
