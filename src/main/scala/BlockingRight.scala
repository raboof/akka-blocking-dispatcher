import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
//#snip
  import akka.actor.typed.DispatcherSelector

//#snip
object BlockingRight extends App {

  //#snip
  val root = Behaviors.setup[Nothing] { context =>
    for (i <- 1 to 50) {
      context.spawn(PrintActor(), s"future-$i") ! i
      context.spawn(
        BlockingActor(),
        s"blocking-$i",
        DispatcherSelector.fromConfig("my-dispatcher-for-blocking")
      ) ! i
    }

    Behaviors.empty
  }
  //#snip
  val system = ActorSystem[Nothing](root, "NonBlockingSample")
}