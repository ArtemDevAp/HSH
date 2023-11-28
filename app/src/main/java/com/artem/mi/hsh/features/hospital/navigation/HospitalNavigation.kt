package com.artem.mi.hsh.features.hospital.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.artem.mi.hsh.features.hospital.HospitalRoute
import com.artem.mi.hsh.features.hospital.HospitalViewModel
import com.artem.mi.hsh.features.search.model.SearchOutputParametersType

private const val HOSPITAL_ROUTE = "hospitalScreen"
internal const val HOSPITAL_SEARCH_ARG = "searchArg"

fun NavController.navigateToHospital(params: String) = navigate("$HOSPITAL_ROUTE/${params}")

fun NavGraphBuilder.hospitalScreen(onSearchIconClick: () -> Unit) {
    composable(
        route = "$HOSPITAL_ROUTE/{$HOSPITAL_SEARCH_ARG}",
        arguments = listOf(
            navArgument(HOSPITAL_SEARCH_ARG) { type = SearchOutputParametersType }
        )
    ) {
        val viewModel: HospitalViewModel = viewModel(factory = HospitalViewModel.factory)
        HospitalRoute(viewModel = viewModel, onSearchIconClick)
    }
}