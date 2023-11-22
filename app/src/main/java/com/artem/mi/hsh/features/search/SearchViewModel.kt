@file:OptIn(ExperimentalCoroutinesApi::class)

package com.artem.mi.hsh.features.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.artem.mi.hsh.core.features.HospitalSearchParametersModel
import com.artem.mi.hsh.data.NfzFilterOptionsRepository
import com.artem.mi.hsh.data.NfzFilterOptionsRepositoryImpl
import com.artem.mi.hsh.domain.FetchServiceSuggestionsUseCase
import com.artem.mi.hsh.domain.FetchServiceSuggestionsUseCaseImpl
import com.artem.mi.hsh.domain.FetchTownSuggestionsUseCase
import com.artem.mi.hsh.domain.FetchTownSuggestionsUseCaseImpl
import com.artem.mi.hsh.domain.TownInputFilterModel
import com.artem.mi.hsh.domain.core.ObservableFilterImpl
import com.artem.mi.hsh.domain.core.StringFilter
import com.artem.mi.hsh.features.search.model.RadioTypeOption
import com.artem.mi.hsh.features.search.model.Voivodeship
import com.artem.mi.hsh.features.search.model.mapFromRemote
import com.artem.mi.hsh.features.search.navigation.SearchNavigationDirection
import com.artem.mi.hsh.ui.common.navigation.ResetNavigation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    nfzFilterOptionsRepository: NfzFilterOptionsRepository,
    private val townSuggestionsUseCase: FetchTownSuggestionsUseCase,
    private val serviceSuggestionsUseCase: FetchServiceSuggestionsUseCase
) : ViewModel(), ResetNavigation {

    private val requestVoivodeshipData: Flow<List<Voivodeship>> =
        flowOf(nfzFilterOptionsRepository.voivodeshipTypes().mapFromRemote())

    private val requestOptionsData: Flow<List<RadioTypeOption>> =
        flowOf(nfzFilterOptionsRepository.fetchVarietyTypes().mapFromRemote())

    private val searchViewState = MutableStateFlow(SearchViewState())
    val searchState: StateFlow<SearchViewState> = combine(
        searchViewState, requestVoivodeshipData, requestOptionsData
    ) { state, voivodeship, options ->
        state.copy(
            voivodeshipOptions = voivodeship,
            referralOptions = options
        )
    }.stateIn(
        scope = viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        SearchViewState()
    )

    init {
        observeSuggestions()
    }

    fun onServiceTextChanged(service: String) {
        searchViewState.update { it.copy(service = service) }
        viewModelScope.launch { serviceSuggestionsUseCase.emitNext(service) }
    }

    fun onServiceSelected(text: String) {
        searchViewState.update {
            it.copy(service = text, serviceIsExpanded = false, serviceSuggestion = emptyList())
        }
    }

    fun onChangeVoivodeshipExpanded() {
        searchViewState.update { it.changeVoivodeshipExpanded() }
    }

    fun onVoivodeshipSelected(voivodeship: Voivodeship) {
        searchViewState.update {
            it.copy(
                voivodeshipSelected = voivodeship,
                voivodeshipIsExpanded = false
            )
        }
    }

    fun onTownChanged(town: String) {
        searchViewState.update { it.copy(town = town) }
        viewModelScope.launch {
            townSuggestionsUseCase.emitNext(
                TownInputFilterModel(town, searchState.value.voivodeshipSelected.code)
            )
        }
    }

    fun onTownSelected(town: String) {
        searchViewState.update {
            it.copy(townIsExpanded = false, townSuggestion = emptyList(), town = town)
        }
    }

    fun onReferralOptionChanged(option: RadioTypeOption) {
        searchViewState.update {
            it.copy(referralOptionSelected = option)
        }
    }

    fun onSearchSelected() {
        val navigationParams = with(searchState.value) {
            HospitalSearchParametersModel(
                type = referralOptionSelected.type,
                serviceName = service,
                locality = town,
                voivodeship = voivodeshipSelected.code
            )
        }
        searchViewState.update {
            it.copy(
                navigation = SearchNavigationDirection.NavigateToHospitalScreen(navigationParams)
            )
        }
    }

    override fun resetNavigation() {
        searchViewState.update { it.copy(navigation = SearchNavigationDirection.Empty) }
    }

    private fun observeSuggestions() {
        townSuggestionsUseCase.observeChanges.mapLatest { townSuggestions ->
            searchViewState.update {
                it.copy(
                    townSuggestion = townSuggestions,
                    townIsExpanded = townSuggestions.isNotEmpty()
                )
            }
        }.catch {
            Log.v("APP", "observeServiceSuggestions $it")
        }.launchIn(viewModelScope)
        serviceSuggestionsUseCase.observeChanges.mapLatest { serviceSuggestions ->
            searchViewState.update {
                it.copy(
                    serviceSuggestion = serviceSuggestions,
                    serviceIsExpanded = serviceSuggestions.isNotEmpty()
                )
            }
        }.catch {
            Log.v("APP", "observeServiceSuggestions $it")
        }.launchIn(viewModelScope)
    }

    companion object {
        val factory = viewModelFactory {
            initializer {
                val nfzRepository = NfzFilterOptionsRepositoryImpl()
                val stringFilter = StringFilter()
                SearchViewModel(
                    nfzRepository,
                    townSuggestionsUseCase = FetchTownSuggestionsUseCaseImpl(
                        nfzRepository,
                        ObservableFilterImpl(),
                        stringFilter
                    ),
                    serviceSuggestionsUseCase = FetchServiceSuggestionsUseCaseImpl(
                        nfzRepository,
                        ObservableFilterImpl(),
                        stringFilter
                    )
                )
            }
        }
    }
}