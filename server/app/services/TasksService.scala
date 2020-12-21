package services


case class User(login: String, password: String)

trait TasksService {
  def validateUser(login: String, password: String): Boolean
  def createUser(login: String, password: String): User
  def getTasks(login: String): Seq[String]
  def addTask(task: String): String
  def removeTask(task: String): Unit
}

object TaskServiceInMemoryImpl extends TasksService {
  private val users = Map("Ksawery" -> "Pass")

  override def validateUser(login: String, password: String): Boolean = {
    users.get(login).contains(password)
  }

  override def createUser(login: String, password: String): User = ???

  override def getTasks(login: String): Seq[String] = ???

  override def addTask(task: String): String = ???

  override def removeTask(task: String): Unit = ???
}
