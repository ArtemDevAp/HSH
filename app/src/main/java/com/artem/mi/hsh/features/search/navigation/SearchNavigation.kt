package com.artem.mi.hsh.features.search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.artem.mi.hsh.features.search.SearchRoute

const val SEARCH_ROUTE = "search_screen_route"

fun NavGraphBuilder.searchScreen(
    onSearchSelected: (String) -> Unit
) {
    composable(route = SEARCH_ROUTE) {
        SearchRoute(onSearchSelected = onSearchSelected)
    }
}