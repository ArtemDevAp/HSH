package com.artem.mi.hsh.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.artem.mi.hsh.features.hospital.navigation.hospitalScreen
import com.artem.mi.hsh.features.hospital.navigation.navigateToHospital
import com.artem.mi.hsh.features.search.navigation.SEARCH_ROUTE
import com.artem.mi.hsh.features.search.navigation.searchScreen

@Composable
fun HshNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = SEARCH_ROUTE) {
        searchScreen { navController.navigateToHospital(it) }
        hospitalScreen { navController.navigateUp() }
    }
}