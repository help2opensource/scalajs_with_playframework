package services

import scala.collection.mutable

case class User(login: String, password: String)

trait TasksService {
  def validateUser(login: String, password: String): Boolean
  def createUser(login: String, password: String): Option[User]
  def getTasks(login: String): Seq[String]
  def addTask(task: String): String
  def removeTask(task: String): Unit
}

object TaskServiceInMemoryImpl extends TasksService {
  private val users = mutable.Map("Ksawery" -> "Pass")
  private val tasks = mutable.Map("Ksawery" -> List("Eat", "Sleep", "Code", "Bruh"))

  override def validateUser(login: String, password: String): Boolean = {
    users.get(login).contains(password)
  }

  override def createUser(login: String, password: String): Option[User] = {
    users.get(login) match {
      case None =>
        println("none")
        users += (login -> password)
        Some(User(login, password))
      case Some(savedPassword) =>
        println(savedPassword)
        if (savedPassword == password) {
          Some(User(login, password))
        } else None
    }
  }

  override def getTasks(login: String): Seq[String] = tasks.getOrElse(login, Nil)

  override def addTask(task: String): String = ???

  override def removeTask(task: String): Unit = ???
}
