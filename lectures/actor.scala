import scala.actors.Actor
import scala.actors.Actor._

case object SayHello

class Hello(val name : String) extends Actor {
  def act() {
    loop {
      react {
        case SayHello => {
          println(name + " says hello")
          exit()
        }
      }
    }
  }
}

object Main {
  def main(args : Array[String]) {
    val a = new Hello("Alice")
    a.start()

    val b = new Hello("Bob")
    b.start()

    a ! SayHello
    b ! SayHello
  }
}

Main.main(args)
