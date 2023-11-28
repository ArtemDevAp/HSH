package com.artem.mi.hsh.core.mock.usecase

import com.artem.mi.hsh.domain.FetchTownSuggestionsUseCase
import com.artem.mi.hsh.domain.TownInputFilterModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.mapLatest

class TownSuggestionsUseCaseMock(
    private val mockChanges: List<String> = emptyList()
) : FetchTownSuggestionsUseCase {

    private val testSharedFlow = MutableSharedFlow<TownInputFilterModel>()

    override suspend fun emitNext(param: TownInputFilterModel) {
        testSharedFlow.emit(param)
    }

    override val observeChanges: Flow<List<String>> = testSharedFlow.mapLatest { mockChanges }
}