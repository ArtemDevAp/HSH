package com.artem.mi.hsh.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artem.mi.hsh.data.model.VoivodeshipType
import com.artem.mi.hsh.features.search.model.RadioTypeOption
import com.artem.mi.hsh.features.search.model.SearchOutputParameters
import com.artem.mi.hsh.features.search.model.Voivodeship
import com.artem.mi.hsh.features.search.navigation.SearchNavigationDirection
import com.artem.mi.hsh.ui.common.navigation.ResetNavigation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class SearchViewModel : ViewModel(), ResetNavigation {

    private val requestHeaderData: Flow<List<Voivodeship>> =
        flowOf(
            listOf(
                Voivodeship(code = "0", name = "Malopolskie vievodoship test"),
                Voivodeship(code = "01", name = "two"),
            )
        )

    private val requestOptionsData: Flow<List<RadioTypeOption>> =
        flowOf(
            listOf(
                RadioTypeOption(title = "One", type = 0),
                RadioTypeOption(title = "Two", type = 1)
            )
        )

    private val searchViewState = MutableStateFlow(SearchViewState())
    val searchState: StateFlow<SearchViewState> = combine(
        searchViewState, requestHeaderData, requestOptionsData
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
        searchViewState.update { it.copy(town = town) }
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
                serviceName = "Poradnia dermatologiczna",
                locality = "Kraków",
                voivodeship = VoivodeshipType.LesserPoland.number
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
}