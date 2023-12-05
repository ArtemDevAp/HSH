package com.artem.mi.hsh.features.hospital.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.artem.mi.hsh.core.features.HospitalSearchParametersModel
import com.artem.mi.hsh.core.features.HospitalSearchParcelableType
import com.artem.mi.hsh.features.hospital.HospitalRoute
import com.artem.mi.hsh.features.hospital.HospitalViewModel

private const val HOSPITAL_ROUTE = "hospitalScreen"
private const val HOSPITAL_INPUT_ARG = "hospitalInputArg"

internal class HospitalArgs(val hospitalSearch: HospitalSearchParametersModel) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        checkNotNull(savedStateHandle.get<HospitalSearchParametersModel>(HOSPITAL_INPUT_ARG))
    )
}

fun NavController.navigateToHospital(params: HospitalSearchParametersModel) {
    val searchJson = params.toJson()
    navigate("$HOSPITAL_ROUTE/${searchJson}")
}

fun NavGraphBuilder.hospitalScreen(onSearchIconClick: () -> Unit) {
    composable(
        route = "$HOSPITAL_ROUTE/{$HOSPITAL_INPUT_ARG}",
        arguments = listOf(
            navArgument(HOSPITAL_INPUT_ARG) { type = HospitalSearchParcelableType }
        )
    ) {
        val viewModel: HospitalViewModel = viewModel(factory = HospitalViewModel.factory)
        HospitalRoute(viewModel = viewModel, onSearchIconClick)
    }
}