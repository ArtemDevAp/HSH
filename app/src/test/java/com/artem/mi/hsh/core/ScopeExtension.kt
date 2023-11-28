package com.artem.mi.hsh.core


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher

inline fun CoroutineScope.collectionStateTest(
    dispatcher: TestCoroutineScheduler = TestCoroutineScheduler(),
    crossinline doOnStart: suspend () -> Unit,
    crossinline given: suspend () -> Unit,
    vararg assert: () -> Unit = emptyArray()
) = launch {
    val job = launch(UnconfinedTestDispatcher(dispatcher)) {
        doOnStart.invoke()
    }

    given.invoke()

    assert.forEach {
        it.invoke()
    }

    job.cancel()
}