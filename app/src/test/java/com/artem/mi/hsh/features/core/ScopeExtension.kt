package com.artem.mi.hsh.features.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher

inline fun CoroutineScope.collectionStateTest(
    crossinline doOnStart: suspend () -> Unit,
    crossinline given: suspend () -> Unit,
    vararg assert: () -> Unit = emptyArray()
) = launch {
    val job = launch(UnconfinedTestDispatcher()) {
        doOnStart.invoke()
    }

    given.invoke()

    assert.forEach {
        it.invoke()
    }

    job.cancel()
}