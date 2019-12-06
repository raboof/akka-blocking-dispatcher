import akka.actor.typed.ActorSystem
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object NonBlockingActor {
  def apply(): Behavior[Int] =
    Behaviors.receive { (context, i) =>
      context.log.info(s"Non-blocking operation finished: $i")
      Behaviors.same
    }
}

object NoBlockingMain extends App {
  val root = Behaviors.setup[Nothing] { context =>
    val printActor = context.spawn(PrintActor(), "future")

    for (i <- 1 to 100) {
      context.spawn(NonBlockingActor(), s"nonblocking-$i") ! i
      printActor ! i
    }
    context.log.info("Done spawning and sending")

    Behaviors.empty
  }
  val system = ActorSystem[Nothing](root, "NonBlockingSample")
}
