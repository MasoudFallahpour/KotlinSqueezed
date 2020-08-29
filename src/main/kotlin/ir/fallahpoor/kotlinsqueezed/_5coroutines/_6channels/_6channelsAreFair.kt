package ir.fallahpoor.kotlinsqueezed._5coroutines._6channels

import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    /***********************
     *  Channels Are Fair  *
     ***********************/

    /**
     * Send and receive operations to channels are fair with respect to the order of their invocation from multiple
     * coroutines. They are served in first-in first-out order, e.g. the first coroutine to invoke receive gets the
     * element. In the following example two coroutines "ping" and "pong" are receiving the "ball" object from the
     * shared "table" channel.
     */
    data class Ball(var hits: Int)

    suspend fun player(name: String, table: Channel<Ball>) {
        for (ball in table) { // receive the ball in a loop
            ball.hits++
            println("$name $ball")
            delay(300) // wait a bit
            table.send(ball) // send the ball back
        }
    }

    runBlocking {
        val table = Channel<Ball>() // a shared table
        launch { player("ping", table) }
        launch { player("pong", table) }
        table.send(Ball(0)) // serve the ball
        delay(1000) // delay 1 second
        coroutineContext.cancelChildren() // game over, cancel them
    }

    /**
     * The "ping" coroutine is started first, so it is the first one to receive the ball. Even though "ping"
     * coroutine immediately starts receiving the ball again after sending it back to the table, the ball gets
     * received by the "pong" coroutine, because it was already waiting for it.
     *
     * Note that sometimes channels may produce executions that look unfair due to the nature of the executor that
     * is being used.
     */

}