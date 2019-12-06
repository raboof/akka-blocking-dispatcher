import akka.actor.typed.ActorSystem
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object BlockingWrong extends App {
  val root = Behaviors.setup[Nothing] { context =>
    val printActor = context.spawn(PrintActor(), "future")

    for (i <- 1 to 100) {
      context.spawn(BlockingActor(), s"blocking-$i") ! i
      printActor ! i
    }

    Behaviors.empty
  }
  val system = ActorSystem[Nothing](root, "NonBlockingSample")
}
