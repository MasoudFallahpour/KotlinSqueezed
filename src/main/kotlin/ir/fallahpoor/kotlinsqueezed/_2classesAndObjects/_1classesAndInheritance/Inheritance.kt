package ir.fallahpoor.kotlinsqueezed._2classesAndObjects._1classesAndInheritance

/**
 * All classes in Kotlin have a common superclass [Any], that is the default superclass for
 * a class with no supertypes declared.
 */
class Example

/**
 * By default, Kotlin classes are final: they can’t be inherited. To make a class inheritable,
 * mark it with the open keyword.
 */
open class Base(p: Int)

class Derived(p: Int) : Base(p)

/**
 * Kotlin requires explicit modifiers for overridable members (we call them open) and for overrides.
 */
open class Shape {
    open fun draw() {
    }

    fun fill() {
    }
}

class Circle : Shape() {
    override fun draw() {
    }
}

/**
 * A member marked override is itself open, i.e. it may be overridden in subclasses. If you
 * want to prohibit re-overriding, use final.
 */
open class Rectangle : Shape() {
    final override fun draw() {
    }
}

open class Shape1 {
    open val vertexCount: Int = 0
}

class Rectangle2 : Shape1() {
    override val vertexCount = 4
}

/**
 * You can override a val property with a var property, but not vice versa. This is allowed
 * because a val property essentially declares a get method, and overriding it as a var
 * additionally declares a set method in the derived class.
 */

/**
 * During construction of a new instance of a derived class, the base class initialization is
 * done as the first step and thus happens before the initialization logic of the derived class
 * is run.
 */

/**
 * Code in a derived class can call its superclass functions and property accessors implementations
 * using the super keyword.
 */
open class Rectangle3 {
    open fun draw() {
        println("Drawing a rectangle")
    }

    open val borderColor: String get() = "black"
}

class FilledRectangle1 : Rectangle3() {
    override fun draw() {
        super.draw()
        println("Filling the rectangle")
    }

    val fillColor: String get() = super.borderColor
}

/**
 * Inside an inner class, accessing the superclass of the outer class is done with the super
 * keyword qualified with the outer class name.
 */
class FilledRectangle2 : Rectangle3() {
    override fun draw() {
    }

    override val borderColor: String get() = "black"

    inner class Filler {
        fun fill() {
        }

        fun drawAndFill() {
            super@FilledRectangle2.draw()
            fill()
            println("Drawn a filled rectangle with color ${super@FilledRectangle2.borderColor}")
        }
    }
}

/**
 * if a class inherits multiple implementations of the same member from its immediate superclasses,
 * it must override this member and provide its own implementation.
 * To denote the supertype from which the inherited implementation is taken, we use super qualified
 * by the supertype name in angle brackets.
 */
open class Rectangle4 {
    open fun draw() {
    }
}

interface Polygon {
    fun draw() {
    }
}

class Square : Rectangle4(), Polygon {
    override fun draw() {
        super<Rectangle4>.draw() // call to Rectangle4.draw()
        super<Polygon>.draw() // call to Polygon.draw()
    }
}