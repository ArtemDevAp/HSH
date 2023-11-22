package com.artem.mi.hsh.features.search.navigation

import com.artem.mi.hsh.core.features.mockHospitalSearchParametersModel
import com.artem.mi.hsh.ui.common.navigation.ResetNavigation
import org.junit.Test
import org.junit.Assert.assertEquals
import kotlin.reflect.KClass

class SearchNavigationDirectionTest {

    @Test
    fun `given navigation to hospital screen state, when call navigate, then expect correct reset`() {
        val params = mockHospitalSearchParametersModel
        val navigateToHospital = SearchNavigationDirection.NavigateToHospitalScreen(params)
        val emptyClazz = SearchNavigationDirection.Empty::class
        val navigateClazz = navigateToHospital::class

        val result = mutableListOf<KClass<out SearchNavigationDirection>>()

        val resetNavigation = object : ResetNavigation {
            override fun resetNavigation() {
                result.add(emptyClazz)
            }
        }

        navigateToHospital.navigate(
            SearchNavigationDirection.Actions(
                onSearchSelected = { result.add(navigateClazz) },
                resetNavigation = resetNavigation
            )
        )

        val expected = mutableListOf(emptyClazz, navigateClazz)
        assertEquals(expected, result)
    }

    @Test
    fun `given navigation empty state, when call navigate, then expect zero interactions`() {
        val navigationEmpty = SearchNavigationDirection.Empty
        val emptyClazz = navigationEmpty::class

        val result = mutableListOf<KClass<out SearchNavigationDirection>>()

        val resetNavigation = object : ResetNavigation {
            override fun resetNavigation() {
                result.add(emptyClazz)
            }
        }

        navigationEmpty.navigate(
            SearchNavigationDirection.Actions(
                onSearchSelected = { result.add(emptyClazz) },
                resetNavigation = resetNavigation
            )
        )

        val expected = emptyList<KClass<out SearchNavigationDirection>>()
        assertEquals(expected, result)
    }
}