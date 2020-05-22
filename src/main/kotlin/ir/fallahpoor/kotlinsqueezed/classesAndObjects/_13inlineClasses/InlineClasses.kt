package ir.fallahpoor.kotlinsqueezed.classesAndObjects._13inlineClasses

/**
 * Inline classes are available only since Kotlin 1.3 and currently are EXPERIMENTAL.
 */

/**
 * Sometimes it is necessary for business logic to create a wrapper around some type. However,
 * it introduces runtime overhead due to additional heap allocations. Moreover, if the wrapped
 * type is primitive, the performance hit is terrible, because primitive types are usually
 * heavily optimized by the runtime.
 *
 * To solve such issues, Kotlin introduces a special kind of class called an inline class.
 */
inline class Password(val value: String)

/**
 * An inline class MUST have a single property in its primary constructor. At runtime,
 * instances of the inline class will be represented using this single property.
 * This is the main feature of inline classes, which inspired the name "inline".
 */
// No actual instantiation of class 'Password' happens
// At runtime 'securePassword' contains just 'String'
val securePassword = Password("Don't try this in production")

/**
 * Inline classes support some functionality of regular classes. In particular, they are
 * allowed to declare properties and functions.
 * However, there are some restrictions for inline class members.
 * - Inline classes cannot have init blocks
 * - Inline class properties cannot have backing fields
 * - It follows that inline classes can only have simple computable properties
 *   (no lateinit/delegated properties)
 */
inline class Name(val s: String) {
    val length: Int
        get() = s.length

    fun greet() {
        println("Hello, $s")
    }
}

val name = Name("Kotlin")
//name.greet() // method `greet` is called as a static method
//println(name.length) // property getter is called as a static method

/**
 * Inline classes are allowed to inherit from interfaces. Inline classes cannot extend
 * other classes and must be final.
 */
interface Printable {
    fun prettyPrint(): String
}

inline class AnotherName(val s: String) : Printable {
    override fun prettyPrint(): String = "Let's $s!"
}

val anotherName = AnotherName("Kotlin")
//println(anotherName.prettyPrint()) // Still called as a static method

/**
 * In generated code, the Kotlin compiler keeps a wrapper for each inline class. Inline
 * class instances can be represented at runtime either as wrappers or as the underlying
 * type. This is similar to how Int can be represented either as a primitive int or as
 * the wrapper Integer.
 */

/**
 * Since inline classes are compiled to their underlying type, it may lead to various
 * obscure errors, for example unexpected platform signature clashes.
 */
inline class UInt(val x: Int)

// Represented as 'public final void compute(int x)' on the JVM
fun compute(x: Int) {}

// Also represented as 'public final void compute(int x)' on the JVM!
fun compute(x: UInt) {}

/**
 * To mitigate such issues, functions using inline classes are mangled by adding some
 * stable hashcode to the function name. Therefore, fun compute(x: UInt) will be
 * represented as public final void compute-<hashcode>(int x), which solves the clash
 * problem.
 * NOTE: '-' is an invalid symbol in Java, meaning that it's impossible to call functions
 * which accept inline classes from Java.
 */

/**
 * The crucial difference between type aliases and inline classes is that type aliases are
 * assignment-compatible with their underlying type (and with other type aliases with the
 * same underlying type), while inline classes are not.
 * In other words, inline classes introduce a truly new type, contrary to type aliases which
 * only introduce an alternative name (alias) for an existing type.
 */
typealias NameTypeAlias = String

inline class NameInlineClass(val s: String)

fun acceptString(s: String) {}
fun acceptNameTypeAlias(n: NameTypeAlias) {}
fun acceptNameInlineClass(p: NameInlineClass) {}

val nameAlias: NameTypeAlias = ""
val nameInlineClass: NameInlineClass = NameInlineClass("")
val string: String = ""

//    acceptString(nameAlias) // OK: pass alias instead of underlying type
//    acceptString(nameInlineClass) // Not OK: can't pass inline class instead of underlying type
//    acceptNameTypeAlias(string) // OK: pass underlying type instead of alias
//    acceptNameInlineClass(string) // Not OK: can't pass underlying type instead of inline class

