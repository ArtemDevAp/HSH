package com.artem.mi.hsh.features.search

import com.artem.mi.hsh.features.search.model.RadioTypeOption
import com.artem.mi.hsh.features.search.model.Voivodeship
import com.artem.mi.hsh.features.search.navigation.SearchNavigationDirection

data class SearchViewState(
    val navigation: SearchNavigationDirection = SearchNavigationDirection.Empty,

    val service: String = "",
    val serviceIsExpanded: Boolean = false,
    val serviceSuggestion: List<String> = emptyList(),

    val referralOptions: List<RadioTypeOption> = emptyList(),
    val referralOptionSelected: RadioTypeOption = RadioTypeOption(),

    val voivodeshipOptions: List<Voivodeship> = emptyList(),
    val voivodeshipIsExpanded: Boolean = false,
    val voivodeshipSelected: Voivodeship = Voivodeship(),

    val town: String = "",
    val townIsExpanded: Boolean = false,
    val townSuggestion: List<String> = emptyList()
) {
    fun changeVoivodeshipExpanded() = copy(voivodeshipIsExpanded = !voivodeshipIsExpanded)
}