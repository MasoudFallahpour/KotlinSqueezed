package ir.fallahpoor.kotlinsqueezed.classesAndObjects._2propertiesAndFields

/**
 * Properties in Kotlin classes can be declared either as mutable using the var keyword,
 * or as read-only using the val keyword.
 */
class Address {
    val id: Int = 123456
    var name: String = "Holmes, Sherlock"
    var street: String = "Baker"
    var city: String = "London"
    var state: String? = null
    var zip: String = "123456"
}

/**
 * To use a property, we simply refer to it by name.
 */
fun main() {
    val address = Address()
    println(address.name)
}

/**
 * We can define custom accessors for a property. If we define a custom getter, it will be
 * called every time we access the property (this allows us to implement a computed property).
 */
class Stack {
    private var size: Int = 0
    val isEmpty: Boolean
        get() = (size == 0)
}

class Number {
    var stringRepresentation: String
        get() = this.toString()
        set(value) {
            setDataFromString(value) // parses the string and assigns values to other properties
        }

    private fun setDataFromString(value: String) {
    }
}

/**
 * If you need to change the visibility of an accessor or to annotate it, but don't need to change
 * the default implementation, you can define the accessor without defining its body.
 */
var setterVisibility: String = "abc"
    private set // the setter is private and has the default implementation

/**
 * Fields cannot be declared directly in Kotlin classes. However, when a property needs a backing
 * field, Kotlin provides it automatically. This backing field can be referenced in the accessors
 * using the field identifier:
 */
var counter = 0 // Note: the initializer assigns the backing field directly
    set(value) {
        if (value >= 0) field = value
    }

/**
 * A backing field will be generated for a property if it uses the default implementation of at
 * least one of the accessors, or if a custom accessor references it through the field identifier.
 */

/**
 * If you want to do something that does not fit into this "implicit backing field" scheme, you can
 * always fall back to having a backing property.
 */
class Table {
    private var _table: Map<String, Int>? = null
    val table: Map<String, Int>
        get() {
            if (_table == null) {
                _table = HashMap() // Type parameters are inferred
            }
            return _table ?: throw AssertionError("Set to null by another thread")
        }
}

/**
 * Normally, properties declared as having a non-null type must be initialized in the constructor.
 * However, fairly often this is not convenient.
 * In this case, you cannot supply a non-null initializer in the constructor, but you still want
 * to avoid null checks when referencing the property inside the body of a class.
 *
 * To check whether a lateinit var has already been initialized, use .isInitialized on the reference
 * to that property (since 1.2).
 */
class Test {

    private lateinit var table: Table

    fun prepareTable() {
        table = Table()
    }

    fun useTable() {
        if (this::table.isInitialized) {
            /* Use table */
        }
    }

}