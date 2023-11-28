package com.artem.mi.hsh.domain

import com.artem.mi.hsh.data.NfzFilterOptionsRepository
import com.artem.mi.hsh.domain.core.MutableObservableFilter
import com.artem.mi.hsh.domain.core.ObservableEmitterFilter
import com.artem.mi.hsh.domain.core.ObservableReceiverFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

interface FetchServiceSuggestionsUseCase :
    ObservableEmitterFilter<String>, ObservableReceiverFilter<List<String>>

class FetchServiceSuggestionsUseCaseImpl(
    private val nfzFilterOptionsRepository: NfzFilterOptionsRepository,
    private val observableFilter: MutableObservableFilter<String>
) : FetchServiceSuggestionsUseCase {

    override val observeChanges: Flow<List<String>> =
        observableFilter
            .observeChanges
            .mapLatest { service ->
                if (service.length < MIN_CHARACTER) {
                    emptyList()
                } else {
                    fetchServices(service)
                }
            }

    private suspend fun fetchServices(service: String): List<String> {
        val services = nfzFilterOptionsRepository.serviceDictionary(service)
        return if (services.contains(service.trimIndent().uppercase())) {
            emptyList()
        } else {
            services
        }
    }

    override suspend fun emitNext(param: String) {
        observableFilter.emitNext(param)
    }

    private companion object {
        const val MIN_CHARACTER = 3
    }
}