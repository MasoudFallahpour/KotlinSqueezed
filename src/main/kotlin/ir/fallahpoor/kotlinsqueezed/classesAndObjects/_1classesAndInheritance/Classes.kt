package ir.fallahpoor.kotlinsqueezed.classesAndObjects._1classesAndInheritance

class Invoice {
    /* body */
}

/**
 * If a class has no body, curly braces can be omitted
 */
class Empty

class Person1 constructor(firstName: String) {
}

/**
 * If the primary constructor does not have any annotations or visibility modifiers,
 * the constructor keyword can be omitted:
 */
class Person2(firstName: String) {
}

/**
 * The primary constructor cannot contain any code. Initialization code can be placed
 * in initializer blocks, which are prefixed with the init keyword.
 */
class Person3(firstName: String) {
    init {
        println("First name is $firstName")
    }
}

/**
 * For declaring properties and initializing them from the primary constructor, Kotlin
 * has a concise syntax.
 */
class Person4(val firstName: String, val lastName: String, var age: Int) {
}

class Person5 {
    var children: MutableList<Person5> = mutableListOf()

    constructor(parent: Person5) {
        parent.children.add(this)
    }
}

/**
 * If the class has a primary constructor, each secondary constructor needs to delegate to
 * the primary constructor, either directly or indirectly through another secondary constructor(s).
 */
class Person6(val name: String) {
    var children: MutableList<Person6> = mutableListOf()

    constructor(name: String, parent: Person6) : this(name) {
        parent.children.add(this)
    }
}

/**
 * Delegation to the primary constructor happens as the first statement of a secondary constructor,
 * so the code in all initializer blocks and property initializers is executed before the secondary
 * constructor body.
 */
class Constructors {
    init {
        println("First this")
    }

    constructor(i: Int) {
        println("And them this")
    }
}

fun main() {
    val invoice = Invoice()
}