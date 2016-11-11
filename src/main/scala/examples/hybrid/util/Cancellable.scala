package examples.hybrid.util

import scala.concurrent.{Future, Promise}
import scala.util.Success


trait CancellableStatus {
  def isCancelled: Boolean
  def nonCancelled = !isCancelled
}

trait Cancellable {
  def cancel(): Unit
  def status: CancellableStatus
}

object Cancellable {
  def apply() = new Cancellable {
    val p = Promise[Unit]()

    override def cancel: Unit = p.tryComplete(Success(()))

    val status: CancellableStatus = new CancellableStatus {
      override def isCancelled: Boolean = p.future.value != None
    }
  }

  def run()(cont: CancellableStatus => Future[Unit]): Cancellable = {
    val cancellable = Cancellable()
    cont(cancellable.status) // run continuation feeding status
    cancellable
  }
}