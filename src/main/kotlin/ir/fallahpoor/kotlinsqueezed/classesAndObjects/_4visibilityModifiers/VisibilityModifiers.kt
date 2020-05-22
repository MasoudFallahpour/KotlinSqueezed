package ir.fallahpoor.kotlinsqueezed.classesAndObjects._4visibilityModifiers

/**
 * Classes, objects, interfaces, constructors, functions, properties and their setters can
 * have visibility modifiers.
 * There are four visibility modifiers in Kotlin: private, protected, internal and public.
 * The default visibility, used if there is no explicit modifier, is public.
 */

private fun foo() {} // visible inside this file only

public var bar: Int = 5 // property is visible everywhere
    private set         // setter is visible only in this file

internal val baz = 6    // visible inside the same module

open class Outer {
    private val a = 1
    protected open val b = 2
    internal val c = 3
    val d = 4  // public by default

    protected class Nested {
        public val e: Int = 5
    }
}

class Subclass : Outer() {
    // a is not visible
    // b, c and d are visible
    // Nested and e are visible

    override val b = 5   // 'b' is protected
}

class Unrelated(o: Outer) {
    // o.a, o.b are not visible
    // o.c and o.d are visible (same module)
    // Outer.Nested is not visible, and Nested::e is not visible either
}

/**
 * To specify a visibility of the primary constructor of a class, use the following syntax
 * (note that you need to add an explicit constructor keyword).
 */
class C private constructor(a: Int) {
}