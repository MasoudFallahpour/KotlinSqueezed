package ir.fallahpoor.kotlinsqueezed._1basics._4returnsAndJumps

fun main() {

    /**
     * return: By default returns from the nearest enclosing FUNCTION or ANONYMOUS FUNCTION.
     * break: Terminates the nearest enclosing LOOP.
     * continue: Proceeds to the next step of the nearest enclosing LOOP.
     */

    for (i in 1..10) {
        if (i > 5) break
        print("$i ")
    }

    println()

    for (i in 1..10) {
        if (i == 5) {
            print("  ")
            continue
        } else {
            print("$i ")
        }
    }

    /**
     * Any expression in Kotlin may be marked with a label. Labels have the form of an
     * identifier followed by the @ sign.
     */
    loop@ for (i in 1..100) {
        for (j in 1..100) {
            if (j == 50) break@loop
        }
    }

    foo1()
    foo2()
    foo3()
    foo4()
    foo5()

}

/**
 * By default return-expression returns from the nearest enclosing function.
 */
fun foo1() {
    println()
    listOf(1, 2, 3, 4, 5).forEach {
        if (it == 3) return // non-local return directly to the caller of foo1()
        print("$it ")
    }
    println("this point is unreachable")
}

/**
 * If we need to return from a lambda expression, we have to label it and qualify the return.
 */
fun foo2() {
    println()
    listOf(1, 2, 3, 4, 5).forEach lit@{
        if (it == 3) return@lit // local return to the caller of the lambda, i.e. the forEach loop
        print("$it ")
    }
    print("done with explicit label")
}

/**
 * Oftentimes it is more convenient to use implicit labels: such a label has the same name as
 * the function to which the lambda is passed.
 */
fun foo3() {
    println()
    listOf(1, 2, 3, 4, 5).forEach {
        if (it == 3) return@forEach // local return to the caller of the lambda, i.e. the forEach loop
        print("$it ")
    }
    print("done with implicit label")
}

/**
 * we can replace the lambda expression with an anonymous function. A return statement in an
 * anonymous function will return from the anonymous function itself.
 */
fun foo4() {
    println()
    listOf(1, 2, 3, 4, 5).forEach(fun(value: Int) {
        if (value == 3) return  // local return to the caller of the anonymous fun, i.e. the forEach loop
        print("$value ")
    })
    print("done with anonymous function")
}

/**
 * Note that the use of local returns in previous three examples is similar to the use of
 * continue in regular loops. There is no direct equivalent for break, but it can be simulated
 * by adding another nesting lambda and non-locally returning from it.
 */
fun foo5() {
    println()
    run loop@{
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return@loop // non-local return from the lambda passed to run
            print("$it ")
        }
    }
    print("done with nested loop")
}