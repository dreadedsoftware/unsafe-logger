package unsafe_logger

case class UnsafeLogger[F[_], In, L <: Function1[In, _]](logger: L){
  def &:[A](f: F[A]): F[A] = f
  def :&[A](f: F[A]): F[A] = &:(f)
  def &:(in: In): this.type = {
    logger(in)
    this
  }
  def :&(s: In): this.type = &:(s)
}

object implicits{
  implicit def sToLog[F[_], In, L <: Function1[In, _]](
    s: In)(
    implicit l: L): UnsafeLogger[F, In, L] = {
    UnsafeLogger[F, In, L](l) :& s
  }
}