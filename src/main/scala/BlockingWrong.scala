import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors

object BlockingWrong extends App {
  val root = Behaviors.setup[Nothing] { context =>
    for (i <- 1 to 50) {
      context.spawn(PrintActor(), s"print-$i") ! i
      context.spawn(BlockingActor(), s"blocking-$i") ! i
    }
    Behaviors.empty
  }
  val system = ActorSystem[Nothing](root, "NonBlockingSample")
}
