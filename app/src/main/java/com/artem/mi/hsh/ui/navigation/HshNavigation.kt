package com.artem.mi.hsh.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Surface
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
    Surface {
        NavHost(
            navController = navController,
            startDestination = SEARCH_ROUTE,
            enterTransition = { fadeIn(animationSpec = tween(0)) },
            exitTransition = { fadeOut(animationSpec = tween(0)) }
        ) {
            searchScreen(onSearchSelected = navController::navigateToHospital)
            hospitalScreen { navController.navigateUp() }
        }
    }
}