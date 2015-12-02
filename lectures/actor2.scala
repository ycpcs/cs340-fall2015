import scala.actors.Actor
import scala.actors.Actor._

case class Add(val x : Int, val y : Int)

class Adder extends Actor {
  def act() {
    loop {
      react {
        case msg : Add => {
          val sum = msg.x + msg.y
          sender ! sum
          exit()
        }
      }
    }
  }
}

object Main {
  def main(args : Array[String]) {
    val adder = new Adder
    adder.start()

    val f = adder !! new Add(3, 4)
    val resultUntyped = f()
    resultUntyped match {
      case sum : Int => {
        println("sum is " + sum)
      }
    }
  }
}

Main.main(args)
