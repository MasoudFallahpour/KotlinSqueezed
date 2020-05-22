package ir.fallahpoor.kotlinsqueezed.classesAndObjects._9nestedClasses

/**
 * Classes can be nested in other classes.
 */
class Outer {
    private val bar: Int = 1

    class Nested {
        fun foo() = 2
    }
}

val demo = Outer.Nested().foo() // demo = 2

class Outer1 {
    private val bar: Int = 1

    inner class Inner {
        fun foo() = bar
    }
}

val demo1 = Outer1().Inner().foo() // demo = 1

/**
 * Anonymous inner class instances are created using an object expression.
 */
open class MouseAdapter {
    open fun mouseClicked() {
        // Some default behaviour
    }

    open fun mouseMoved() {
        // Some default behaviour
    }
}

fun handleMouseEvents(mouseAdapter: MouseAdapter) {
}

fun main() {
    handleMouseEvents(object : MouseAdapter() {
        override fun mouseClicked() {
        }

        override fun mouseMoved() {
        }
    })
}