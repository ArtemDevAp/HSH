package com.artem.mi.hsh.features.search.navigation

import com.artem.mi.hsh.ui.common.navigation.ResetNavigation

sealed interface SearchNavigationDirection {

    fun navigate(navigationActions: Actions) {
        navigationActions.resetNavigation.resetNavigation()
    }

    data class NavigateToHospitalScreen(
        private val string: String
    ) : SearchNavigationDirection {
        override fun navigate(navigationActions: Actions) {
            super.navigate(navigationActions)
            navigationActions.onSearchSelected(string)
        }
    }

    object Empty : SearchNavigationDirection {
        override fun navigate(navigationActions: Actions) = Unit
    }

    data class Actions(
        val onSearchSelected: (String) -> Unit,
        val resetNavigation: ResetNavigation
    )
}