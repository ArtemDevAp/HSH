package com.artem.mi.hsh.domain

import com.artem.mi.hsh.data.NfzFilterOptionsRepository
import com.artem.mi.hsh.domain.core.MutableObservableFilter
import com.artem.mi.hsh.domain.core.ObservableEmitterFilter
import com.artem.mi.hsh.domain.core.ObservableReceiverFilter
import com.artem.mi.hsh.domain.core.StringFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

interface FetchServiceSuggestionsUseCase :
    ObservableEmitterFilter<String>, ObservableReceiverFilter<List<String>>

class FetchServiceSuggestionsUseCaseImpl(
    private val nfzFilterOptionsRepository: NfzFilterOptionsRepository,
    private val observableFilter: MutableObservableFilter<String>,
    private val stringFilter: StringFilter
) : FetchServiceSuggestionsUseCase {

    override val observeChanges: Flow<List<String>> =
        observableFilter
            .observeChanges
            .mapLatest { service ->
                val minLengthNotPassed =
                    stringFilter.minLength(service, MIN_CHARACTER_LENGTH)
                if (minLengthNotPassed) emptyList()
                else fetchServices(service)
            }

    private suspend fun fetchServices(service: String): List<String> {
        val services = nfzFilterOptionsRepository.serviceDictionary(service)
        val servicesContainService = stringFilter.match(service, services)
        return if (servicesContainService) emptyList() else services
    }

    override suspend fun emitNext(param: String) {
        observableFilter.emitNext(param)
    }

    private companion object {
        const val MIN_CHARACTER_LENGTH = 3
    }
}