package ir.fallahpoor.kotlinsqueezed._3functionsAndLambdas._1functions

import kotlin.math.cos

/*****************
 *  Declaration  *
 *****************/

/**
 * Functions in Kotlin are declared using the fun keyword.
 */
fun double(x: Int): Int {
    return 2 * x
}

/*****************
 *     Usage     *
 *****************/

/**
 * Calling functions uses the traditional approach.
 */
val result = double(2)

/**
 * Calling member functions uses the dot notation.
 */
class Earth {
    fun getPopulation() = 7_594_000_000 // as of 2018!
}

val earthPopulation = Earth().getPopulation()

/**
 * Function parameters are defined using Pascal notation, i.e. name: type. Parameters are
 * separated using commas. Each parameter must be explicitly typed.
 */
fun powerOf(number: Int, exponent: Int) {}

/**
 * Function parameters can have default values, which are used when a corresponding argument
 * is omitted. This allows for a reduced number of overloads compared to other languages.
 */
fun read(b: Array<Byte>, off: Int = 0, len: Int = b.size) {}

/**
 * Overriding methods always use the same default parameter values as the base method. When
 * overriding a method with default parameter values, the default parameter values must be
 * omitted from the signature.
 */
open class A {
    open fun foo(i: Int = 10) {}
}

class B : A() {
    override fun foo(i: Int) {} // no default value allowed
}

/**
 * If a default parameter precedes a parameter with no default value, the default value can
 * only be used by calling the function with named arguments.
 */
fun foo(bar: Int = 0, baz: Int) {}

val resultOfFoo =
    foo(baz = 1) // The default value bar = 0 is used

/**
 * If the last argument after default parameters is a lambda, it can be passed in either as a
 * named argument or outside the parentheses.
 */
fun foo(bar: Int = 0, baz: Int = 1, qux: () -> Unit) { /*...*/
}

val a =
    foo(1) { println("hello") } // Uses the default value baz = 1
val b =
    foo(qux = { println("hello") })  // Uses both default values bar = 0 and baz = 1
val c =
    foo { println("hello") }         // Uses both default values bar = 0 and baz = 1

/**
 * Function parameters can be named when calling functions.
 */
fun reformat(
    str: String,
    normalizeCase: Boolean = true,
    upperCaseFirstLetter: Boolean = true,
    divideByCamelHumps: Boolean = false,
    wordSeparator: Char = ' '
) {
}

val d = reformat(
    "Hello",
    true,
    true,
    false,
    '_'
) // Not that much readable
val e = reformat(
    "Hello",
    normalizeCase = true,
    upperCaseFirstLetter = true,
    divideByCamelHumps = false,
    wordSeparator = '_'
) // Much more readable

/**
 * When a function is called with both positional and named arguments, all the positional
 * arguments should be placed before the first named one. For example, the call
 * f(1, y = 2) is allowed, but f(x = 1, 2) is not.
 */

/**
 * Variable number of arguments (vararg) can be passed in the named form by using the spread
 * operator.
 */
fun foo(vararg strings: String) {}

val f = foo(
    strings = *arrayOf(
        "a",
        "b",
        "c"
    )
) // Pay attention to *

/**
 * On the JVM: the named argument syntax cannot be used when calling Java functions because
 * Java bytecode does not always preserve names of function parameters.
 */

/**
 * If a function does not return any useful value, its return type is Unit and could be
 * omitted. Unit is a type with only one value - Unit. This value does not have to be
 * returned explicitly.
 */
fun printHello(name: String?): Unit {
    if (name != null)
        println("Hello $name")
    else
        println("Hi there!")
    // `return Unit` or `return` is optional
}

val g: Unit =
    printHello("Mark") // print(g) would print 'kotlin.Unit'

/**
 * When a function returns a single expression, the curly braces can be omitted and the
 * body is specified after a = symbol.
 * Explicitly declaring the return type is optional when this can be inferred by the
 * compiler.
 */
fun triple(x: Int): Int = x * 3

/**
 * Functions with block body must always specify return types explicitly, unless it's
 * intended for them to return Unit, in which case it is optional.
 * The reason is that such functions may have complex control flow in the body, and
 * the return type will be non-obvious to the reader (and sometimes even for the compiler).
 */

/**
 * A parameter of a function (normally the last one) may be marked with vararg modifier which
 * allows a variable number of arguments to be passed to the function.
 * Inside a function, a vararg-parameter of type T is visible as an array of T.
 * Only one parameter may be marked as vararg.
 */
fun <T> asList(vararg ts: T): List<T> {
    val result = ArrayList<T>()
    for (t in ts) // ts is an Array
        result.add(t)
    return result
}

val list = asList(1, 2, 3)

/**
 * When we call a vararg-function, we can pass arguments one-by-one, e.g. asList(1, 2, 3), or,
 * if we already have an array and want to pass its contents to the function, we use the spread
 * operator (prefix the array with *)
 */
val array = arrayOf(1, 2, 3)
val myList = asList(
    -1,
    0,
    *array,
    4
)

/**
 * Functions marked with the 'infix' keyword can also be called using the infix notation (omitting
 * the dot and the parentheses for the call).
 * Infix functions MUST satisfy the following requirements:
 * - They must be member functions or extension functions;
 * - They must have a single parameter;
 * - The parameter must not accept variable number of arguments and must have no default value.
 */
infix fun String.append(s: String): String {
    return this + s
}

val h = "Hello" append " World"

/**
 * Infix function calls have lower precedence than the arithmetic operators, type casts, and the
 * rangeTo operator. On the other hand, infix function call's precedence is higher than that of
 * the boolean operators && and ||, is- and in-checks, and some other operators.
 */

/*****************
 *     Scope     *
 *****************/

/**
 * Kotlin supports local functions, i.e. a function inside another function.
 * Local function can access local variables of outer functions (i.e. the closure).
 */
fun cool(i: Int) {

    val j = 0

    fun cooler(k: Int, m: Int) {
        println("$j $k $m")
    }

    cooler(10, 20)
}

/**
 * A member function is a function that is defined inside a class or object and is called with
 * dot notation.
 */
class Sample {
    fun foo(): Int {
        return 100
    }
}

val i = Sample().foo()

/*********************
 * Generic Functions *
 *********************/

/**
 * Functions can have generic parameters which are specified using angle brackets before the
 * function name.
 */
fun <T> makeList(vararg ts: T): List<T> {
    val result = ArrayList<T>()
    for (t in ts) {
        result.add(t)
    }
    return result
}

/****************************
 * Tail Recursive Functions *
 ***************************/

/**
 * Kotlin supports a style of functional programming known as tail recursion. This allows some
 * algorithms that would normally be written using loops to instead be written using a recursive
 * function, but without the risk of stack overflow.
 * When a function is marked with the 'tailrec' modifier and meets the required form, the compiler
 * optimises out the recursion, leaving behind a fast and efficient loop based version instead.
 */
val eps = 1E-10

tailrec fun findFixPoint(x: Double = 1.0): Double =
    if (kotlin.math.abs(x - kotlin.math.cos(x)) < eps) x else findFixPoint(
        cos(x)
    )

/**
 * Writing the above function using the traditional style is as follow.
 */
fun findFixPoint(): Double {
    var x = 1.0
    while (true) {
        val y = kotlin.math.cos(x)
        if (kotlin.math.abs(x - y) < eps) return x
        x = kotlin.math.cos(x)
    }
}

/**
 * To be eligible for the tailrec modifier, a function must call itself as the last operation it
 * performs. You cannot use tail recursion when there is more code after the recursive call, and
 * you cannot use it within try/catch/finally blocks. Currently, tail recursion is supported by
 * Kotlin for JVM and Kotlin/Native.
 */