package com.artem.mi.hsh.domain.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce

interface ObservableEmitterFilter<T> {
    suspend fun emitNext(param: T)
}

interface ObservableReceiverFilter<O> {
    val observeChanges: Flow<O>
}

interface MutableObservableFilter<T> : ObservableEmitterFilter<T>, ObservableReceiverFilter<T>

class ObservableFilterImpl<T>(debounce: Long = DEFAULT_DEBOUNCE) : MutableObservableFilter<T> {

    private val mutableSharedEmitter = MutableSharedFlow<T>()

    override val observeChanges: Flow<T> = mutableSharedEmitter.debounce(debounce)

    override suspend fun emitNext(param: T) {
        mutableSharedEmitter.emit(param)
    }

    private companion object {
        const val DEFAULT_DEBOUNCE = 500L
    }
}