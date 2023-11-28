package com.artem.mi.hsh.domain

import com.artem.mi.hsh.data.NfzFilterOptionsRepository
import com.artem.mi.hsh.domain.core.MutableObservableFilter
import com.artem.mi.hsh.domain.core.ObservableEmitterFilter
import com.artem.mi.hsh.domain.core.ObservableReceiverFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

data class TownInputFilterModel(
    val town: String,
    val voivodeship: String = ""
)

interface FetchTownSuggestionsUseCase :
    ObservableEmitterFilter<TownInputFilterModel>, ObservableReceiverFilter<List<String>>

class FetchTownSuggestionsUseCaseImpl(
    private val nfzFilterOptionsRepository: NfzFilterOptionsRepository,
    private val observableFilter: MutableObservableFilter<TownInputFilterModel>
) : FetchTownSuggestionsUseCase {

    override val observeChanges: Flow<List<String>> =
        observableFilter
            .observeChanges
            .mapLatest { townFilterModel ->
                if (townFilterModel.town.length < MIN_CHARACTER) {
                    emptyList()
                } else {
                    fetchTowns(townFilterModel)
                }
            }

    private suspend fun fetchTowns(input: TownInputFilterModel): List<String> {
        val towns = with(input) { nfzFilterOptionsRepository.townDictionary(town, voivodeship) }
        return if (towns.contains(input.town.trimIndent().uppercase())) {
            emptyList()
        } else {
            towns
        }
    }

    override suspend fun emitNext(param: TownInputFilterModel) {
        observableFilter.emitNext(param)
    }

    private companion object {
        const val MIN_CHARACTER = 3
    }
}