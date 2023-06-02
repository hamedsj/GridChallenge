package me.hamedsj.gridchallenge.utils

sealed class Response<out V, out E>

data class Success<out V>(val value: V) : Response<V, Nothing>()

data class Failure<out E>(val error: E) : Response<Nothing, E>()

fun <V, E> Response<V, E>.isSuccessful(): Boolean {
    return this is Success
}

inline infix fun <E> Failure<E>?.otherwise(block: (E) -> Unit) {
    if (this != null) {
        block.invoke(error)
    }
}

inline fun <V, E> Response<V, E>.ifSuccessful(success: (V) -> Unit): Failure<E>? {
    return when (this) {
        is Success -> {
            success.invoke(this.value)
            null
        }
        is Failure -> {
            this
        }
    }
}

inline fun <V, E> Response<V, E>.ifNotSuccessful(failure: (E) -> Unit) {
    if (this is Failure) {
        failure.invoke(this.error)
    }
}

fun <V, E> Response<V, E>.getSuccessResponse(): Success<V> {
    if (this is Success) {
        return this
    } else {
        throw IllegalStateException("The selected response is a failure.")
    }
}

fun <V, E> Response<V, E>.getFailureResponse(): Failure<E> {
    if (this is Failure) {
        return this
    } else {
        throw IllegalStateException("The selected response is a success.")
    }
}
