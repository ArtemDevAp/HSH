package com.artem.mi.hsh.features.search

import com.artem.mi.hsh.features.search.model.RadioTypeOption
import com.artem.mi.hsh.features.search.model.Voivodeship


sealed interface SearchNavigationDirection {

    fun navigate(
        navigateToHospitalScreen: () -> Unit
    ) = Unit

    object NavigateToHospitalScreen : SearchNavigationDirection {
        override fun navigate(navigateToHospitalScreen: () -> Unit) {
            navigateToHospitalScreen.invoke()
        }
    }

    object Empty : SearchNavigationDirection
}

data class SearchViewState(
    val navigation: SearchNavigationDirection = SearchNavigationDirection.Empty,

    val referralOptions: List<RadioTypeOption> = emptyList(),
    val referralOptionSelected: RadioTypeOption = RadioTypeOption(""),

    val voivodeshipOptions: List<Voivodeship> = emptyList(),
    val voivodeshipIsExpanded: Boolean = false,
    val voivodeshipSelected: Voivodeship = Voivodeship(),

    val service: String = "",
    val town: String = ""
) {
    fun changeVoivodeshipExpanded() = copy(voivodeshipIsExpanded = !voivodeshipIsExpanded)
}