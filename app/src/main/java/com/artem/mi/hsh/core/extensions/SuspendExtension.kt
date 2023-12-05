package com.artem.mi.hsh.core.extensions

import java.util.concurrent.CancellationException

inline fun <T> suspendTryCatch(
    block: () -> T,
    exception: (error: Exception) -> T
): T {
    return try {
        block()
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        exception(e)
    }
}