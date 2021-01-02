KotlinSqueezed
================
KotlinSqueezed is a trimmed version of [Kotlin's official language guide](https://kotlinlang.org/docs/reference) in form of a
Gradle-based Kotlin project.

## Package Structure
Each chapter and subchapter of Kotlin's official language guide has a corresponding package. For instance,
there is a package named `basics` for chapter `Basics`. When you enter into package `basics` you can find
packages `basicTypes`, `packagesAndImports`, `controlFlow`, and `returnsAndJumps`. Each of
these inner packages has a corresponding subchapter in chapter `Basics`.

## Package Contents
Inside a leaf package there are `.kt` files, each demonstrating an aspect of
Kotlin. For example [Numbers.kt](https://github.com/MasoodFallahpoor/KotlinSqueezed/blob/master/src/main/kotlin/ir/fallahpoor/kotlinsqueezed/_1basics/_1basicTypes/_1Numbers.kt)
is all about numeric types of Kotlin.

