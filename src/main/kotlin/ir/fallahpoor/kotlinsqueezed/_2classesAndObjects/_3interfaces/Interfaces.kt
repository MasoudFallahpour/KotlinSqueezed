package ir.fallahpoor.kotlinsqueezed._2classesAndObjects._3interfaces

interface MyInterface {
    fun bar()
    fun foo() {
        // optional body
    }
}

class Child : MyInterface {
    override fun bar() {
        // body
    }
}

/**
 * You can declare properties in interfaces. A property declared in an interface can either be
 * abstract, or it can provide implementations for accessors. Properties declared in interfaces
 * can't have backing fields, and therefore accessors declared in interfaces can't reference them.
 */
interface MyInterface1 {
    val prop: Int // abstract

    val propertyWithImplementation: String
        get() = "foo"

    fun foo() {
        print(prop)
    }
}

class Child1 : MyInterface1 {
    override val prop: Int = 29
}

/**
 * if a class inherits multiple implementations of the same member from its immediate superclasses,
 * it must override this member and provide its own implementation.
 * To denote the supertype from which the inherited implementation is taken, we use super qualified
 * by the supertype name in angle brackets.
 */
interface A {
    fun foo() {
        print("A")
    }

    fun bar()
}

interface B {
    fun foo() {
        print("B")
    }

    fun bar() {
        print("bar")
    }
}

class D : A, B {
    override fun foo() {
        super<A>.foo()
        super<B>.foo()
    }

    override fun bar() {
        super<B>.bar()
    }
}