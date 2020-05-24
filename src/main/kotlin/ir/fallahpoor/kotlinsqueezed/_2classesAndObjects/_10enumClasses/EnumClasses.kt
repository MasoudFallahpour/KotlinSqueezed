package ir.fallahpoor.kotlinsqueezed._2classesAndObjects._10enumClasses

import java.util.function.BinaryOperator

enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

enum class Color(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
}

/**
 * Enum constants can also declare their own anonymous classes with their corresponding
 * methods, as well as overriding base methods.
 */
enum class ProtocolState {
    WAITING {
        override fun signal() = TALKING
    },

    TALKING {
        override fun signal() = WAITING
    }; // Semicolon is required here!

    abstract fun signal(): ProtocolState
}

/**
 * An enum class may implement an interface (but not derive from a class). This is done by
 * adding the interface(s) to the enum class declaration as follows.
 */
enum class IntArithmetics : BinaryOperator<Int> {
    PLUS {
        override fun apply(t: Int, u: Int): Int = t + u
    },
    TIMES {
        override fun apply(t: Int, u: Int): Int = t * u
    }
}

/**
 * Enum classes in Kotlin have synthetic methods allowing to list the defined enum constants
 * and to get an enum constant by its name.
 */
val d = Direction.valueOf("NORTH") // d = NORTH
val a: Array<Direction> = Direction.values() // a = [NORTH, SOUTH, WEST, EAST]