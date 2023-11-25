package com.artem.mi.hsh.features.search

import com.artem.mi.hsh.features.core.MainDispatcherRule
import com.artem.mi.hsh.features.core.collectionStateTest
import com.artem.mi.hsh.features.search.model.RadioTypeOption
import com.artem.mi.hsh.features.search.model.Voivodeship
import com.artem.mi.hsh.features.search.navigation.SearchNavigationDirection
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        viewModel = SearchViewModel()
    }

    @Test
    fun `given service text, when service text changed, expect correct text`() = runTest {
        val serviceTextTest = "test text"
        collectionStateTest(
            doOnStart = {
                viewModel.searchState.collect()
            },
            given = {
                viewModel.onServiceTextChanged(serviceTextTest)
            },
            assert = arrayOf({
                val actual = viewModel.searchState.value
                assertEquals(serviceTextTest, actual.service)
            })
        )
    }

    @Test
    fun `when voivodeship pressed, expect correct state`() = runTest {
        collectionStateTest(
            doOnStart = {
                viewModel.searchState.collect()
            },
            given = {
                viewModel.onVoivodeshipPressed()
            },
            assert = arrayOf({
                val actual = viewModel.searchState.value
                val expected = true
                assertEquals(expected, actual.voivodeshipIsExpanded)
            })
        )
    }

    @Test
    fun `given voivodeship, when voivodeship selected, expect correct state`() = runTest {
        val mockVoivodeship = Voivodeship(
            code = "00",
            name = "Test"
        )
        collectionStateTest(
            doOnStart = {
                viewModel.searchState.collect()
            },
            given = {
                viewModel.onVoivodeshipSelected(mockVoivodeship)
            },
            assert = arrayOf({
                val actual = viewModel.searchState.value
                assertEquals(mockVoivodeship, actual.voivodeshipSelected)
                assertEquals(false, actual.voivodeshipIsExpanded)
            })
        )
    }

    @Test
    fun `given town, when town changed, expect correct state`() = runTest {
        val mockTown = "Test"
        collectionStateTest(
            doOnStart = {
                viewModel.searchState.collect()
            },
            given = {
                viewModel.onTownChanged(mockTown)
            },
            assert = arrayOf({
                val actual = viewModel.searchState.value
                assertEquals(mockTown, actual.town)
            })
        )
    }

    @Test
    fun `given radioTypeOption, when referral option changed, expect correct state`() = runTest {
        val mockRadioType = RadioTypeOption(
            title = "Test",
            type = 0
        )
        collectionStateTest(
            doOnStart = {
                viewModel.searchState.collect()
            },
            given = {
                viewModel.onReferralOptionChanged(mockRadioType)
            },
            assert = arrayOf({
                val actual = viewModel.searchState.value
                assertEquals(mockRadioType, actual.referralOptionSelected)
            })
        )
    }

    @Test
    fun `when init screen, expect navigation empty`() = runTest {
        collectionStateTest(
            doOnStart = {
                viewModel.searchState.collect()
            },
            given = {},
            assert = arrayOf({
                val actual = viewModel.searchState.value
                val expected = SearchNavigationDirection.Empty
                assertEquals(expected, actual.navigation)
            })
        )
    }

    @Test
    fun `when search pressed, expect correct navigation`() = runTest {
        collectionStateTest(
            doOnStart = {
                viewModel.searchState.collect()
            },
            given = {
                viewModel.onSearchSelected()
            },
            assert = arrayOf({
                val actual = viewModel.searchState.value
                val expected = SearchNavigationDirection.NavigateToHospitalScreen("")
                assertEquals(expected, actual.navigation)
            })
        )
    }
}