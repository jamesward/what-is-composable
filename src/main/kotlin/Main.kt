import arrow.core.Either
import arrow.core.continuations.either
import arrow.core.flatMap
import kotlin.random.Random

var mutableI = 0

fun addOneImpure(i: Int): Int {
    if (mutableI < 1) {
        mutableI = i + 1
    }
    else {
        throw Exception("asdf")
    }

    return mutableI
}

fun multTwo(i: Int): Int = i * 2

fun addOneImpureAndMultTwo(i: Int) = multTwo(addOneImpure(i))

fun addOne(i: Int) = i + 1


// what does composability mean?
// composeability usually refers to just function associativity
// direction is not specified
// other traits like commutativity are not implied but could exist
fun addOneAndAddOne(i: Int) = addOne(addOne(i))
fun addOneAndMultTwo(i: Int) = multTwo(addOne(i)) // (i + 1) * 2 is left associative
fun multTwoAndAddOne(i: Int) = addOne(multTwo(i))

// HOFs and composability
//
// maybe (in pure function land)
// a function that takes a function as a param but has no value to operate on
// can only validly return another function
//
fun addOneThen(f: (Int) -> Int): (Int) -> Int = {
    f(it + 1)
}

fun useAddOneThen() {
    val f = addOneThen {
        it * 3
    }

    println(f(4))
}

fun hofsAndAssociativity() {
    val l = listOf(1, 2, 3)
    val o1 = l.map(::addOne).map(::multTwo)
    val o2 = l.map(::multTwo).map(::addOne)
    val o = l.map(::addOneAndMultTwo)
    require(o1 == o)
    require(o2 != o)
}

// composability in FP means referential transparency / pure functions
// composing pure functions produces pure functions

// in FP is composition only associtivity?
// monads are data structures that are associative
// monads "compose" because they are immutable values with an associative function
// enables sequential application, like with pure functions
// but why can't we do everything we need with just pure functions?

suspend fun monadsCompose() {
    val e1 = Either.conditionally(Random.nextBoolean(), { "asdf" }, { 1 })
    val e2 = Either.conditionally(Random.nextBoolean(), { "zxcv" }, { 2 })

    println(e1)
    println(e2)

    val e: Either<String, Int> = either {
        val e1V = e1.bind()
        // breaks here if e1 is Left
        val e2V = e2.bind()
        // breaks here if e2 is Left
        e1V + e2V
    }

    println(e)
}

suspend fun main() {
    monadsCompose()
}