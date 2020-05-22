package ir.fallahpoor.kotlinsqueezed.classesAndObjects._11objects

/**
 * Kotlin (after Scala) makes it easy to declare singletons.
 * This is called an 'object declaration', and it always has a name following the 'object' keyword.
 * Object declaration's initialization is thread-safe and done at first access.
 */
class DataProvider { /* ... */ }

object DataProviderManager {
    fun registerDataProvider(provider: DataProvider) {
    }

    val allDataProviders: Collection<DataProvider>?
        get() {
            return null
        }
}

/**
 * Such objects can have supertypes.
 */
object DefaultListener : MouseAdapter() {
    override fun mouseClicked() {
    }

    override fun mouseMoved() {
    }
}

/**
 * An object declaration inside a class can be marked with the 'companion' keyword. Members of
 * the companion object can be called by using simply the class name as the qualifier.
 * The name of the companion object can be omitted, in which case the name 'Companion' will be used.
 */
class MyClass {
    companion object Factory {
        fun create(): MyClass = MyClass()
    }
}

val myClass = MyClass.create()

/**
 * The name of a class used by itself (not as a qualifier to another name) acts as a reference to
 * the companion object of the class (whether named or not).
 */
class MyClass1 {
    companion object Named {}
}

val x = MyClass1

class MyClass2 {
    companion object {}
}

val y = MyClass2

/**
 * Note that, even though the members of companion objects look like static members in other
 * languages, at runtime those are still instance members of real objects, and can, for example,
 * implement interfaces.
 * However, on the JVM you can have members of companion objects generated as real static methods
 * and fields, if you use the @JvmStatic annotation.
 */
interface Factory<T> {
    fun create(): T
}

class MyOtherClass {
    companion object : Factory<MyOtherClass> {
        override fun create(): MyOtherClass = MyOtherClass()
    }
}

/**
 * There is one important semantic difference between object expressions and object declarations:
 * - object expressions are executed (and initialized) immediately, where they are used;
 * - object declarations are initialized lazily, when accessed for the first time;
 * - a companion object is initialized when the corresponding class is loaded (resolved)
 *   matching the semantics of a Java static initializer.
 */