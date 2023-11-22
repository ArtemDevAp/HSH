package com.artem.mi.hsh.core.mock.usecase

import com.artem.mi.hsh.domain.FetchServiceSuggestionsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.mapLatest

class ServiceSuggestionsUseCaseMock(
    private val mockChanges: List<String> = emptyList()
) : FetchServiceSuggestionsUseCase {

    private val testSharedFlow = MutableSharedFlow<String>()

    override suspend fun emitNext(param: String) {
        testSharedFlow.emit(param)
    }

    override val observeChanges: Flow<List<String>> = testSharedFlow.mapLatest { mockChanges }
}