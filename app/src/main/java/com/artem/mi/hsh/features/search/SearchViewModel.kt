package com.artem.mi.hsh.features.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.artem.mi.hsh.data.NfzFilterOptionsRepository
import com.artem.mi.hsh.data.NfzFilterOptionsRepositoryImpl
import com.artem.mi.hsh.domain.FetchSuggestionTownUseCase
import com.artem.mi.hsh.features.search.model.RadioTypeOption
import com.artem.mi.hsh.features.search.model.SearchOutputParameters
import com.artem.mi.hsh.features.search.model.Voivodeship
import com.artem.mi.hsh.features.search.model.mapFromRemote
import com.artem.mi.hsh.features.search.navigation.SearchNavigationDirection
import com.artem.mi.hsh.ui.common.navigation.ResetNavigation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SearchViewModel(
    nfzFilterOptionsRepository: NfzFilterOptionsRepository,
    private val fetchSuggestionTownUseCase: FetchSuggestionTownUseCase
) : ViewModel(), ResetNavigation {

    private val requestVoivodeshipData: Flow<List<Voivodeship>> =
        flowOf(
            nfzFilterOptionsRepository
                .voivodeshipTypes()
                .mapFromRemote()
        )

    private val requestOptionsData: Flow<List<RadioTypeOption>> =
        flowOf(
            nfzFilterOptionsRepository
                .fetchVarietyTypes()
                .mapFromRemote()
        )

    private val onTownChanged = MutableStateFlow("")
    private val onTownChangedState = onTownChanged
        .debounce(SEARCH_DEBOUNCE)
        .mapLatest {
            val result = fetchSuggestionTownUseCase.invoke(
                searchViewState.value.town,
                searchViewState.value.voivodeshipSelected.code
            )
            result
        }

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
        viewModelScope.launch {
            onTownChangedState.collectLatest { townSuggestions ->
                searchViewState.update {
                    it.copy(
                        townSuggestion = townSuggestions,
                        townIsExpanded = townSuggestions.isNotEmpty()
                    )
                }
            }
        }
    }

    fun onServiceTextChanged(service: String) {
        searchViewState.update { it.copy(service = service) }
    }

    fun onVoivodeshipPressed() {
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
        Log.v("APP90", "town = $town")
        searchViewState.update { it.copy(town = town) }
        viewModelScope.launch { onTownChanged.emit(town) }
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

    override fun resetNavigation() {
        searchViewState.update { it.copy(navigation = SearchNavigationDirection.Empty) }
    }

    fun onSearchSelected() {
        val output = with(searchState.value) {
            SearchOutputParameters(
                type = referralOptionSelected.type,
                serviceName = service,
                locality = town,
                voivodeship = voivodeshipSelected.code
            )
        }
        searchViewState.update {
            it.copy(
                navigation = SearchNavigationDirection.NavigateToHospitalScreen(
                    Json.encodeToString(output)
                )
            )
        }
    }

    companion object {
        val factory = viewModelFactory {
            initializer {
                val nfzRepository = NfzFilterOptionsRepositoryImpl()
                SearchViewModel(
                    nfzRepository,
                    FetchSuggestionTownUseCase(nfzRepository)
                )
            }
        }

        private const val SEARCH_DEBOUNCE = 500L
    }
}