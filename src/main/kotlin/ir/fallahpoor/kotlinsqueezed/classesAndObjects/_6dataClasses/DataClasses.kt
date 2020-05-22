package ir.fallahpoor.kotlinsqueezed.classesAndObjects._6dataClasses

/**
 * We frequently create classes whose main purpose is to hold data. In Kotlin, this is called
 * a data class and is marked with keyword data.
 */
data class User(val name: String, val age: Int)

/**
 * The compiler automatically derives the following members from all properties declared in the
 * PRIMARY constructor:
 * - equals()/hashCode() pair;
 * - toString() of the form "User(name=John, age=42)";
 * - componentN() functions corresponding to the properties in their order of declaration;
 * - copy() function
 */

/**
 * To ensure consistency and meaningful behavior of the generated code, data classes have to
 * fulfill the following requirements:
 * - The primary constructor needs to have at least one parameter;
 * - All primary constructor parameters need to be marked as val or var;
 * - Data classes cannot be abstract, open, sealed or inner;
 * - (before 1.1) Data classes may only implement interfaces.
 */

/**
 * Additionally, the members generation follows these rules with regard to the members inheritance:
 * - If there are explicit implementations of equals(), hashCode() or toString() in the data class
 *   body or final implementations in a superclass, then these functions are not generated, and the
 *   existing implementations are used;
 * - If a supertype has the componentN() functions that are open and return compatible types, the
 *   corresponding functions are generated for the data class and override those of the supertype.
 *   If the functions of the supertype cannot be overridden due to incompatible signatures or being
 *   final, an error is reported;
 * - Deriving a data class from a type that already has a copy(...) function with a MATCHING signature
 *   is deprecated in Kotlin 1.2 and is prohibited in Kotlin 1.3.
 * - Providing explicit implementations for the componentN() and copy() functions is not allowed.
 */

/**
 * To exclude a property from the generated implementations, declare it inside the class body.
 */
data class Person(val name: String) {
    var age: Int = 0
}

/**
 * It's often the case that we need to copy an object altering some of its properties, but keeping the
 * rest unchanged. This is what copy() function is generated for. For the User class above, its
 * implementation would be as follows:
 *
 * fun copy(name: String = this.name, age: Int = this.age) = User(name, age)
 */
val jack = User(name = "Jack", age = 1)
val olderJack = jack.copy(age = 2)

/**
 * Component functions generated for data classes enable their use in destructuring declarations.
 */
fun main() {
    val jane = User("Jane", 35)
    val (name, age) = jane
    println("$name, $age years of age") // prints "Jane, 35 years of age"
}