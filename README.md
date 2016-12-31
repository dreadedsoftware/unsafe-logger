# unsafe-logger
##Cheap, unsafe, impure logging.

Unsafe logger is a super small, impure logging solution.
It is for situations where Monadic logging is too much and no logging is too little. Sometimes, you just want to side-effect and move on with your day.

You have a monadic thing that does what you want.
``` scala
for{
  a <- Option("a")
  b <- Option("b")
  c <- Option("c")
}yield a + b + c
```

Some user asks "Why is there no log?"
You say, on error it will give you an error, on success it will give you the result, what more do you want?
The user wants much more logging of status for some reason or another. So you do this
``` scala
val logger: Function1[String, Unit] = ???
for{
  _ <- Option(logger("Performing A"))
  a <- Option("a")
  _ = logger("Performing B")
  b <- Option("b")
  _ = logger("Performing C")
  c <- Option("c")
}yield a + b + c
```
Now your business logic is all cluttered with silly logging statements. Whatever can be done?

Unsafe Logger can help. With a single import and making your logger implicit, you get logging as a side effect without the clutter.
``` scala
import unsafe_logger.implicits._
implicit val logger: Function1[String, Unit] = ???
for{
  a <- Option("a") &: "Performing A"
  b <- Option("b") &: "Performing B"
  c <- Option("c") &: "Performing C"
}yield a + b + c
```
Or prefix the logging statements.
``` scala
import unsafe_logger.implicits._
implicit val logger: Function1[String, Unit] = ???
for{
  a <- "Performing A" :& Option("a")
  b <- "Performing B" :& Option("b")
  c <- "Performing C" :& Option("c")
}yield a + b + c
```
Or mix and match.
``` scala
import unsafe_logger.implicits._
implicit val logger: Function1[String, Unit] = ???
for{
  a <- "Performing A" :& Option("a")
  b <- Option("b")//user does not want log for B
  c <- Option("c") &: "Performing C"
}yield a + b + c
```
