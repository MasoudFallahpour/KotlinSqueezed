package ir.fallahpoor.kotlinsqueezed._2classesAndObjects._15delegatedProperties

import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * There are certain common kinds of properties, that, though we can implement them manually
 * every time we need them, would be very nice to implement once and for all. Examples include:
 * - lazy properties: the value gets computed only upon first access;
 * - observable properties: listeners get notified about changes to this property;
 * - storing properties in a map, instead of a separate field for each property.
 * To cover these (and other) cases, Kotlin supports delegated properties.
 */

class Example {
    var p: String by Delegate()
}

/**
 * The expression after 'by' is the delegate, because get() (and set()) corresponding to
 * the property will be delegated to its getValue() and setValue() methods.
 * Property delegates have to provide a getValue() function (and setValue() — for vars)
 */
class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}

/**
 * When we read from p that delegates to an instance of Delegate, the getValue() function
 * from Delegate is called, so that its first parameter is the object we read p from and the
 * second parameter holds a description of p itself (e.g. you can take its name).
 * Similarly, when we assign to p, the setValue() function is called. The first two parameters
 * are the same, and the third holds the value being assigned.
 */
val e = Example()
//println(e.p) // prints something like "Example@33a17727, thank you for delegating ‘p’ to me!"
//e.p = "NEW" // prints something like "NEW has been assigned to ‘p’ in Example@33a17727."

/**
 * The Kotlin standard library provides factory methods for several useful kinds of delegates.
 */

/**
 * lazy() is a function that takes a lambda and returns an instance of Lazy<T> which can serve
 * as a delegate for implementing a lazy property.
 */
val lazyValue: String by lazy {
    print("* computed! *")
    "Hello"
}

//println(lazyValue) // prints *computed!*Hello
//println(lazyValue) // prints Hello

/**
 * By default, the evaluation of lazy properties is synchronized. If the synchronization of
 * initialization delegate is not required, pass LazyThreadSafetyMode.PUBLICATION as a
 * parameter to the lazy() function. And if you're sure that the initialization will always
 * happen on the same thread as the one where you use the property, you can use
 * LazyThreadSafetyMode.NONE: it doesn't incur any thread-safety guarantees and the related
 * overhead.
 */

/**
 * Delegates.observable() takes two arguments: the initial value and a handler for
 * modifications. The handler is called every time we assign to the property (after
 * the assignment has been performed). It has three parameters: a property being
 * assigned to, the old value, and the new one.
 * If you want to intercept assignments and "veto" them, use vetoable() instead of
 * observable().
 */
class User {
    var name: String by Delegates.observable("<no name>") { prop, oldValue, newValue ->
        println("$oldValue -> $newValue")
    }
}

fun main() {
    val user = User()
    user.name = "first"
    user.name = "second"
}

/**
 * One common use case is storing the values of properties in a map. This comes up
 * often in applications like parsing JSON or doing other “dynamic” things. In this
 * case, you can use the map instance itself as the delegate for a delegated property.
 */
class Person(map: Map<String, Any?>) {
    val name: String by map
    val age: Int by map
}

val person = Person(
    mapOf(
        "name" to "John Doe",
        "age" to 25
    )
)

//println(user.name) // Prints "John Doe"
//println(user.age)  // Prints 25

/**
 * This works also for var’s properties if you use a MutableMap instead of read-only Map.
 */
class MutablePerson(map: MutableMap<String, Any?>) {
    var name: String by map
    var age: Int by map
}

/**
 * Since Kotlin 1.1 we can declare local variables as delegated properties.
 */

/**
 * Here we summarize requirements to delegate objects.
 *
 * For a read-only property (val), a delegate has to provide an operator function
 * getValue() with the following parameters:
 * - thisRef: must be the same or a supertype of the property owner.
 * - property: must be of type KProperty<*> or its supertype.
 * getValue() must return the same type as the property (or its subtype).
 *
 * For a mutable property (var), a delegate has to additionally provide an operator
 * function setValue() with the following parameters:
 * - thisRef: must be the same or a supertype of the property owner.
 * - property: must be of type KProperty<*> or its supertype.
 * - value: must be of the same type as the property (or its supertype).
 */
class Resource

class Owner {
    val valResource: Resource by ResourceDelegate()
}

class ResourceDelegate(private var resource: Resource = Resource()) {
    operator fun getValue(thisRef: Owner, property: KProperty<*>): Resource {
        return resource
    }

    operator fun setValue(thisRef: Owner, property: KProperty<*>, value: Any?) {
        if (value is Resource) {
            resource = value
        }
    }
}

/**
 * The delegate class may implement one of the interfaces ReadOnlyProperty and
 * ReadWriteProperty. These two interfaces are provided only for convenience.
 */
class ResourceDelegate1(private var resource: Resource = Resource()) : ReadWriteProperty<Owner, Resource> {
    override fun getValue(thisRef: Owner, property: KProperty<*>): Resource {
        return resource
    }

    override fun setValue(thisRef: Owner, property: KProperty<*>, value: Resource) {
        resource = value
    }

}

/**
 * Under the hood for every delegated property the Kotlin compiler generates an auxiliary
 * property and delegates to it. For instance, for the property prop the hidden property
 * prop$delegate is generated, and the code of the accessors simply delegates to this
 * additional property.
 */
class C {
    var prop: String by Delegate()
}

// This code is generated by the compiler instead:
//class C {
//    private val prop$delegate = Delegate()
//    var prop: String
//        get() = prop$delegate.getValue(this, this::prop)
//    set(value: String) = prop$delegate.setValue(this, this::prop, value)
//}

/**
 * The Kotlin compiler provides all the necessary information about prop in the arguments:
 * the first argument 'this' refers to an instance of the outer class C and this::prop is
 * a reflection object of the KProperty type describing prop itself.
 * Syntax this::prop is available only since Kotlin 1.1.
 */

/**
 * Since Kotlin 1.1 a delegate class can have a an operator function 'provideDelegate' that
 * creates the object to which the property implementation is delegated.
 * If the object used on the right hand side of 'by' defines provideDelegate as a member or
 * extension function, that function will be called to create the property delegate instance.
 * The parameters of provideDelegate() are the same as for getValue().
 */

/**
 * In the generated code, the provideDelegate method is called to initialize the auxiliary
 * prop$delegate property.
 */
class D {
    var prop: String by Delegate()
}

// this code is generated by the compiler when the 'provideDelegate' function is available:
//class D {
//    private val prop$delegate = Delegate().provideDelegate(this, this::prop)
//    var prop: String
//        get() = prop$delegate.getValue(this, this::prop)
//    set(value: String) = prop$delegate.setValue(this, this::prop, value)
//}