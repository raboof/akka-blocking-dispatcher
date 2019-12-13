import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors

object NoBlocking extends App {
  val root = Behaviors.setup[Nothing] { context =>
    for (i <- 1 to 100) {
      val actor = context.spawn(PrintActor(), s"nonblocking-$i")
      actor ! i
    }
    Behaviors.empty
  }
  ActorSystem[Nothing](root, "NonBlockingSample")
}
