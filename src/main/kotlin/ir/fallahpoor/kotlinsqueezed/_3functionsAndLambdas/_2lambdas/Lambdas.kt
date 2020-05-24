package ir.fallahpoor.kotlinsqueezed._3functionsAndLambdas._2lambdas

/***************************
 *  Hider-Order Functions  *
 ***************************/

/**
 * A higher-order function is a function that takes functions as parameters, or returns a
 * function.
 */
fun <T, R> Collection<T>.fold(
    initial: R,
    combine: (acc: R, nextElement: T) -> R
): R {
    var accumulator: R = initial
    for (element: T in this) {
        accumulator = combine(accumulator, element)
    }
    return accumulator
}

/**
 * In the code above, the parameter 'combine' has a function type (R, T) -> R, so it accepts a
 * function that takes two arguments of types R and T and returns a value of type R.
 */

/**
 * To call fold, we need to pass it an instance of the function type as an argument, and lambda
 * expressions (described in more detail below) are widely used for this purpose.
 */

/*********************
 *  Functions Types  *
 *********************/

/**
 * Kotlin uses a family of function types like (Int) -> String for declarations that deal with
 * functions.
 *
 * These types have a special notation that corresponds to the signatures of the functions, i.e.
 * their parameters and return values:
 * - All function types have a parenthesized parameter types list and a return type. So
 *   (A, B) -> C denotes a type that represents functions taking two arguments of types A and B
 *   and returning a value of type C. The parameter types list may be empty, as in () -> A.
 * - Function types can optionally have an additional receiver type, which is specified before a
 *   dot in the notation: the type A.(B) -> C represents functions that can be called on a receiver
 *   object of A with a parameter of B and return a value of C.
 * - Suspending functions belong to function types of a special kind, which have a 'suspend' modifier
 *   in the notation, such as suspend () -> Unit or suspend A.(B) -> C.
 *
 * The function type notation can optionally include names for the function parameters:
 * (x: Int, y: Int) -> Point
 *
 * To specify that a function type is nullable, use parentheses: ((Int, Int) -> Int)?.
 *
 * Function types can be combined using parentheses: (Int) -> ((Int) -> Unit)
 *
 * The arrow notation is right-associative, (Int) -> (Int) -> Unit is equivalent to the previous example,
 * but not to ((Int) -> (Int)) -> Unit.
 *
 * There are several ways to obtain an instance of a function type:
 * - Using a code block within a function literal, in one of the forms:
 *   - a lambda expression: { a, b -> a + b },
 *   - an anonymous function: fun(s: String): Int { return s.toIntOrNull() ?: 0 }
 * - Using a callable reference to an existing declaration:
 *    - a top-level, local, member, or extension function: ::isOdd, String::toInt,
 *    - a top-level, member, or extension property: List<Int>::size,
 *    - a constructor: ::Regex
 * - Using instances of a custom class that implements a function type as an interface.
 */

/**
 * The compiler can infer the function types for variables if there is enough information.
 */
val a = { i: Int -> i + 1 } // The inferred type is (Int) -> Int

/**
 * Non-literal values of function types with and without receiver are interchangeable, so
 * that the receiver can stand in for the first parameter, and vice versa.
 * For instance, a value of type (A, B) -> C can be passed or assigned where a A.(B) -> C
 * is expected and the other way around.
 */
val repeatFun: String.(Int) -> String = { times -> this.repeat(times) }
val twoParameters: (String, Int) -> String = repeatFun // OK

fun runTransformation(f: (String, Int) -> String): String {
    return f("hello", 3)
}

val b = runTransformation(repeatFun) // OK

/**
 * A value of a function type can be invoked by using its invoke(...) operator:
 * f.invoke(x) or just f(x).
 *
 * If the value has a receiver type, the receiver object should be passed as the first argument.
 * Another way to invoke a value of a function type with receiver is to prepend it with the receiver
 * object, as if the value were an extension function: 1.foo(2).
 */
fun main() {

    val stringPlus: (String, String) -> String = String::plus
    val intPlus: Int.(Int) -> Int = Int::plus

    println(stringPlus.invoke("<-", "->"))
    println(stringPlus("Hello, ", "world!"))

    println(intPlus.invoke(1, 1))
    println(intPlus(1, 2))
    println(2.intPlus(3)) // extension-like call

}

/************************
 *  Lambda Expressions  *
 ************************/

/**
 * Lambda expressions and anonymous functions are 'function literals', i.e. functions that are
 * not declared, but passed immediately as an expression.
 */

/**
 * The full syntactic form of lambda expressions is as follows.
 */
val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y }

/**
 * If the last parameter of a function is a function, then a lambda expression passed as the
 * corresponding argument can be placed outside the parentheses.
 */
val items = listOf(1, 2, 3, 4, 5)
val product = items.fold(1) { acc, e -> acc * e }

/**
 * If the lambda is the only argument to that call, the parentheses can be omitted entirely.
 */
val c = run { println("...") }

/**
 * It's very common that a lambda expression has only one parameter.
 * If the compiler can figure the signature out itself, it is allowed not to declare the only
 * parameter and omit ->. The parameter will be implicitly declared under the name it.
 */
val ints = listOf(-9, -1, 2, 3, 4, 5)
val d = ints.filter { it > 0 } // this literal is of type '(it: Int) -> Boolean'

/**
 * We can explicitly return a value from the lambda using the qualified return syntax. Otherwise,
 * the value of the last expression is implicitly returned.
 */
val e = ints.filter {
    val shouldFilter = it > 0
    shouldFilter
}

val f = ints.filter {
    val shouldFilter = it > 0
    return@filter shouldFilter
}

/**
 * Since Kotlin 1.1 If the lambda parameter is unused, you can place an underscore instead of its
 * name.
 */
val map = mapOf(1 to 100, 2 to 200, 3 to 300)
val g = map.forEach { (_, value) -> println("$value!") }

/*************************
 *  Anonymous Functions  *
 *************************/

/**
 * One thing missing from the lambda expression syntax presented above is the ability to specify the
 * return type of the function. If you do need to specify it explicitly, you can use an anonymous
 * function.
 * An anonymous function looks very much like a regular function declaration, except that its name
 * is omitted.
 */
val h = fun(x: Int, y: Int): Int = x + y

/**
 * The parameter types of an anonymous function can be omitted if they can be inferred from context.
 */
val i = ints.filter(fun(item) = item > 0)

/**
 * One difference between lambda expressions and anonymous functions is the behavior of non-local
 * returns.
 * A return statement without a label always returns from the function declared with the 'fun'
 * keyword. This means that a return inside a lambda expression will return from the enclosing
 * function, whereas a return inside an anonymous function will return from the anonymous function
 * itself.
 */

/**
 * Function types with receiver, such as A.(B) -> C, can be instantiated with a special form of
 * function literals – function literals with receiver.
 * Inside the body of the function literal, the receiver object passed to a call becomes an
 * implicit this, so that you can access the members of that receiver object without any additional
 * qualifiers, or access the receiver object using a 'this' expression.
 */
val minus: Int.(Int) -> Int = { other -> minus(other) }

/**
 * Lambda expressions can be used as function literals with receiver when the receiver type can be
 * inferred from context. One of the most important examples of their usage is type-safe builders.
 */
class HTML {
    fun body() {
        /* ... */
    }
}

fun html(init: HTML.() -> Unit): HTML {
    val html = HTML()  // create the receiver object
    html.init()        // pass the receiver object to the lambda
    return html
}

val htmlPage =
    html {       // lambda with receiver begins here
        body()   // calling a method on the receiver object
    }
