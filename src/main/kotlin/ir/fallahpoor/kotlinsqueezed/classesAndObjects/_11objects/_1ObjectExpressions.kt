package ir.fallahpoor.kotlinsqueezed.classesAndObjects._11objects

/**
 * Sometimes we need to create an object of a slight modification of some class, without
 * explicitly declaring a new subclass for it. Kotlin handles this case with 'object
 * expressions' and 'object declarations'.
 */

/**
 * To create an object of an anonymous class that inherits from some type (or types), we do it
 * as follow.
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

/**
 * If a supertype has a constructor, appropriate constructor parameters must be passed to it.
 * Many supertypes may be specified as a comma-separated list after the colon.
 */
open class A(x: Int) {
    open val y: Int = x
}

interface B { /*...*/ }

val ab: A = object : A(1), B {
    override val y = 15
}

/**
 * When we need "just an object", with no nontrivial supertypes, we can simply say:
 */
fun foo() {
    val adHoc = object {
        var x: Int = 0
        var y: Int = 0
    }
    print(adHoc.x + adHoc.y)
}

/**
 * If you use an anonymous object as a return type of a public function or the type of
 * a public property, the actual type of that function or property will be the declared
 * supertype of the anonymous object, or Any if you didn't declare any supertype.
 */
class C {
    // Private function, so the return type is the anonymous object type
    private fun foo() = object {
        val x: String = "x"
    }

    // Public function, so the return type is Any
    fun publicFoo() = object {
        val x: String = "x"
    }

    fun bar() {
        val x1 = foo().x        // Works
//        val x2 = publicFoo().x  // ERROR: Unresolved reference 'x'
    }
}