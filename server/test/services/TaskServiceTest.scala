package services

import org.scalatestplus.play.PlaySpec

class TaskServiceTest extends PlaySpec {
  "TaskService" must {
    "accept default user" in {
      TaskServiceInMemoryImpl.validateUser("Ksawery", "Pass") mustBe true
    }
    "reject non-existing user" in {
      TaskServiceInMemoryImpl.validateUser("NonUser", "Password") mustBe false
    }
    "reject user with incorrect password" in {
      TaskServiceInMemoryImpl.validateUser("Ksawery", "Password") mustBe false
    }
    "return correct tasks for user" in {
      TaskServiceInMemoryImpl.getTasks("Ksawery") mustEqual List("Eat", "Sleep", "Code", "Bruh")
    }
    "return empty list of tasks for non-existing user" in {
      TaskServiceInMemoryImpl.getTasks("NonUser") mustEqual Nil
    }
    "return new user on registration" in {
      TaskServiceInMemoryImpl.createUser("NewUser", "NewPassword")
        .mustBe(Some(User("NewUser", "NewPassword")))
    }
    "reject registration of existing user" in {
      TaskServiceInMemoryImpl.createUser("Ksawery", "NewPassword") mustBe None
    }
    "login on registration of already existing user" in {
      TaskServiceInMemoryImpl.createUser("Ksawery", "Pass") mustBe Some(User("Ksawery", "Pass"))
    }
    "add task to existing user" in {
      TaskServiceInMemoryImpl.addTask("Ksawery", "Some new task")
      TaskServiceInMemoryImpl.getTasks("Ksawery").contains("Some new task") mustBe true
    }
    "remove task from existing user" in {
      val task = TaskServiceInMemoryImpl.getTasks("Ksawery").head
      TaskServiceInMemoryImpl.removeTask("Ksawery", 0)
      TaskServiceInMemoryImpl.getTasks("Ksawery").contains(task) mustBe false
    }
  }
}
