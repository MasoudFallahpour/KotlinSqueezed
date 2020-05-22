package ir.fallahpoor.kotlinsqueezed.classesAndObjects._5extensions

/**
 * To declare an extension function, we need to prefix its name with a receiver type, i.e.
 * the type being extended.
 */
fun MutableList<Int>.swap(index1: Int, index2: Int) {
    val tmp = this[index1] // 'this' corresponds to the list
    this[index1] = this[index2]
    this[index2] = tmp
}

fun <T> MutableList<T>.swapItems(index1: Int, index2: Int) {
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}

/**
 * Extensions do not actually modify classes they extend. By defining an extension, you do not
 * insert new members into a class, but merely make new functions callable with the dot-notation
 * on variables of this type.
 */

/**
 * Extension functions are dispatched statically. This means that the extension function being
 * called is determined by the type of the expression on which the function is invoked.
 */
open class Shape

class Rectangle : Shape()

fun Shape.getName() = "Shape"

fun Rectangle.getName() = "Rectangle"

fun printClassName(s: Shape) {
    println(s.getName())
}

// printClassName(Rectangle()) // prints "Shape"

/**
 * If a class has a member function, and an extension function is defined which has the same
 * receiver type, the same name, and is applicable to given arguments, the member always wins.
 */
class Example {
    fun printFunctionType() {
        println("Class method")
    }
}

fun Example.printFunctionType() {
    println("Extension function")
}

// Example().printFunctionType() // prints "Class method"

/**
 * Extensions can be defined with a nullable receiver type. Such extensions can be called on an
 * object variable even if its value is null, and can check for this == null inside the body.
 */
fun Any?.toString(): String {
    if (this == null) return "null"
    // after the null check, 'this' is auto-cast to a non-null type, so the toString() below
    // resolves to the member function of the Any class
    return toString()
}

/**
 * Similarly to functions, Kotlin supports extension properties.
 */
val <T> List<T>.lastIndex: Int
    get() = size - 1

/**
 * Initializers are not allowed for extension properties
 */
//val House.number = 1

/**
 * If a class has a companion object defined, you can also define extension functions and
 * properties for the companion object.
 */
class MyClass {
    companion object {}  // will be called "Companion"
}

fun MyClass.Companion.printCompanion() {
    println("companion")
}

fun main() {
    MyClass.printCompanion()
}

/**
 * Inside a class, you can declare extensions for another class. Inside such an extension,
 * there are multiple implicit receivers.
 * The instance of the class in which the extension is declared is called 'dispatch' receiver,
 * and the instance of the receiver type of the extension method is called 'extension' receiver.
 */
class Host(val hostname: String) {
    fun printHostname() {
        print(hostname)
    }
}

class Connection(val host: Host, val port: Int) {
    fun printPort() {
        print(port)
    }

    fun Host.printConnectionString() {
        printHostname() // calls Host.printHostname()
        print(":")
        printPort() // calls Connection.printPort()
    }

    fun connect() {
        /*...*/
        host.printConnectionString()   // calls the extension function
    }
}

/**
 * In case of a name conflict between the members of the 'dispatch' receiver and the 'extension'
 * receiver, the 'extension' receiver takes precedence. To refer to the member of the 'dispatch'
 * receiver you can use the qualified this syntax.
 */
class Connection1 {
    fun Host.getConnectionString() {
        toString() // calls Host.toString()
        this@Connection1.toString() // calls Connection.toString()
    }
}

/**
 * Extensions declared as members can be declared as open and overridden in subclasses. This means
 * that the dispatch of such functions is virtual with regard to the dispatch receiver type, but
 * static with regard to the extension receiver type.
 */
open class Base

class Derived : Base()

open class BaseCaller {
    open fun Base.printFunctionInfo() {
        println("Base extension function in BaseCaller")
    }

    open fun Derived.printFunctionInfo() {
        println("Derived extension function in BaseCaller")
    }

    fun call(b: Base) {
        b.printFunctionInfo() // call the extension function
    }
}

class DerivedCaller : BaseCaller() {
    override fun Base.printFunctionInfo() {
        println("Base extension function in DerivedCaller")
    }

    override fun Derived.printFunctionInfo() {
        println("Derived extension function in DerivedCaller")
    }
}

// BaseCaller().call(Base())       // "Base extension function in BaseCaller"
// DerivedCaller().call(Base())    // "Base extension function in DerivedCaller" - dispatch receiver is resolved virtually
// DerivedCaller().call(Derived()) // "Base extension function in DerivedCaller" - extension receiver is resolved statically
