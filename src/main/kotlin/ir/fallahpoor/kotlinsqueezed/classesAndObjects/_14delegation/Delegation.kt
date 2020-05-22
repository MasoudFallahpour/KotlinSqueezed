package ir.fallahpoor.kotlinsqueezed.classesAndObjects._14delegation

/**
 * The Delegation pattern has proven to be a good way to achieve the same code reuse
 * as inheritance, and Kotlin supports it natively with no boilerplate code.
 */
interface Base {
    fun print()

    fun printMessage()
}

class BaseImpl(private val x: Int) : Base {
    override fun print() {
        println(x)
    }

    override fun printMessage() {
        println("Hello")
    }
}

class DerivedDelegation(b: Base) : Base by b

//val b = BaseImpl(10)
//DerivedDelegation(b).print() // prints 10
//DerivedDelegation(b).printMessage() // prints "Hello"

/**
 * The by-clause in the supertype list for class Derived indicates that b will be stored internally
 * in objects of Derived and the compiler will generate all the methods of Base that forward to b.
 */

/**
 * To achieve the same functionality as DerivedDelegation, using inheritance, we
 * would have to do it as follow.
 * As you can see there is boilerplate code whose sole purpose is method forwarding.
 */
class DerivedInheritance(private val b: Base) : Base {
    override fun print() {
        b.print()
    }

    override fun printMessage() {
        b.printMessage()
    }
}

/**
 * Overrides work as you might expect: the compiler will use your override implementations
 * instead of those in the delegate object.
 */

class DerivedDelegation1(b: Base) : Base by b {
    override fun printMessage() {
        println("Goodbye")
    }
}

//val b = BaseImpl(10)
//DerivedDelegation1(b).print() // prints 10
//DerivedDelegation1(b).printMessage() // prints "Goodbye"

/**
 * Note, however, that members overridden in this way do not get called from the members of
 * the delegate object, which can only access its own implementations of the interface members.
 */
interface AnotherBase {
    val message: String
    fun print()
}

class AnotherBaseImpl(x: Int) : AnotherBase {
    override val message = "BaseImpl: x = $x"
    override fun print() {
        println(message)
    }
}

class AnotherDerivedDelegation(b: AnotherBase) : AnotherBase by b {
    // This property is not accessed from b's implementation of `print`
    override val message = "Message of Derived"
}

fun main() {
    val b = AnotherBaseImpl(10)
    val derived = AnotherDerivedDelegation(b)
    derived.print() // prints "BaseImpl: x = 10"
    println(derived.message) // prints "Message of Derived"
}
