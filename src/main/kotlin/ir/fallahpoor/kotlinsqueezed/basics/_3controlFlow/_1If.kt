package ir.fallahpoor.kotlinsqueezed.basics._3controlFlow

fun main() {

    val a = 10
    val b = 20

    val max1: Int

    if (a > b) {
        max1 = a
    } else {
        max1 = b
    }

    /**
     * 'If' is an expression, meaning that it returns a value.
     */
    val max3 = if (a > b) a else b

    /**
     * 'If' branches can be blocks, and the last expression is the value of a block.
     */
    val max4 = if (a > b) {
        println("a > b")
        a
    } else {
        println("b >= a")
        b
    }

    /**
     * If you're using 'if' as an expression rather than a statement (for example, returning
     * its value or assigning it to a variable), the expression is required to have an else
     * branch.
     */

//    val max5 = if (a > b) a // compilation error

}