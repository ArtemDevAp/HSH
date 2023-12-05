package com.artem.mi.hsh.features.search.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.artem.mi.hsh.core.features.HospitalSearchParametersModel
import com.artem.mi.hsh.features.search.SearchRoute
import com.artem.mi.hsh.features.search.SearchViewModel

const val SEARCH_ROUTE = "search_screen_route"

fun NavGraphBuilder.searchScreen(
    onSearchSelected: (params: HospitalSearchParametersModel) -> Unit
) {
    composable(route = SEARCH_ROUTE) {
        val viewModel: SearchViewModel = viewModel(factory = SearchViewModel.factory)
        SearchRoute(viewModel = viewModel, onSearchSelected = onSearchSelected)
    }
}