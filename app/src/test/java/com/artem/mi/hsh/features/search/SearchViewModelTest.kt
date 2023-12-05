package com.artem.mi.hsh.features.search

import com.artem.mi.hsh.core.MainDispatcherRule
import com.artem.mi.hsh.core.collectionStateTest
import com.artem.mi.hsh.core.mock.repository.NfzFilterOptionsRepositoryMock
import com.artem.mi.hsh.core.mock.usecase.ServiceSuggestionsUseCaseMock
import com.artem.mi.hsh.core.mock.usecase.TownSuggestionsUseCaseMock
import com.artem.mi.hsh.data.model.VarietyType
import com.artem.mi.hsh.data.model.VoivodeshipType
import com.artem.mi.hsh.features.search.model.RadioTypeOption
import com.artem.mi.hsh.features.search.model.Voivodeship
import com.artem.mi.hsh.features.search.navigation.SearchNavigationDirection
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.artem.mi.hsh.R
import com.artem.mi.hsh.core.features.mockHospitalSearchParametersModel
import com.artem.mi.hsh.domain.TownInputFilterModel

class SearchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val varieties = listOf(VarietyType.Stable)
    private val voivodeships = listOf(VoivodeshipType.LesserPoland)
    private lateinit var viewModel: SearchViewModel
    private val nfzOptions = NfzFilterOptionsRepositoryMock(
        varieties = varieties,
        voivodeships = voivodeships
    )

    private val stringSuggestions = listOf("Test", "Test1")
    private val townSuggestionsUseCaseMock = TownSuggestionsUseCaseMock(stringSuggestions)
    private val serviceSuggestionsUseCaseMock = ServiceSuggestionsUseCaseMock(stringSuggestions)

    @Before
    fun setup() {
        viewModel = SearchViewModel(
            nfzFilterOptionsRepository = nfzOptions,
            townSuggestionsUseCase = townSuggestionsUseCaseMock,
            serviceSuggestionsUseCase = serviceSuggestionsUseCaseMock
        )
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
                viewModel.onChangeVoivodeshipExpanded()
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
            titleResId = null
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
                val params = mockHospitalSearchParametersModel
                val expected = SearchNavigationDirection.NavigateToHospitalScreen(params)
                assertEquals(expected, actual.navigation)
            })
        )
    }

    @Test
    fun `when load variety, expect correct state`() = runTest {
        collectionStateTest(
            doOnStart = {
                viewModel.searchState.collect()
            },
            given = {},
            assert = arrayOf({
                val actual = viewModel.searchState.value
                val expected = listOf(
                    RadioTypeOption(
                        title = VarietyType.Stable.name,
                        type = VarietyType.Stable.index.toInt()
                    )
                )
                assertEquals(expected, actual.referralOptions)
            })
        )
    }

    @Test
    fun `when load voivodeships, expect correct state`() = runTest {
        collectionStateTest(
            doOnStart = {
                viewModel.searchState.collect()
            },
            given = {},
            assert = arrayOf({
                val actual = viewModel.searchState.value
                val expected = listOf(
                    Voivodeship(
                        code = VoivodeshipType.LesserPoland.code,
                        titleResId = R.string.lesser_poland
                    )
                )
                assertEquals(expected, actual.voivodeshipOptions)
            })
        )
    }

    @Test
    fun `given town suggestion input filter, when town input changes, expect load suggestions`() =
        runTest {
            val param = TownInputFilterModel(town = "", voivodeship = "")
            collectionStateTest(
                doOnStart = {
                    viewModel.searchState.collect()
                },
                given = {
                    townSuggestionsUseCaseMock.emitNext(param)
                },
                assert = arrayOf({
                    val actual = viewModel.searchState.value
                    assertEquals(stringSuggestions, actual.townSuggestion)
                })
            )
        }

    @Test
    fun `given service filter string, when town input changes, expect load suggestions`() =
        runTest {
            val param = "test"
            collectionStateTest(
                doOnStart = {
                    viewModel.searchState.collect()
                },
                given = {
                    serviceSuggestionsUseCaseMock.emitNext(param)
                },
                assert = arrayOf({
                    val actual = viewModel.searchState.value
                    assertEquals(stringSuggestions, actual.serviceSuggestion)
                })
            )
        }

    @Test
    fun `when call reset navigation, expect correct state`() = runTest {
        collectionStateTest(
            doOnStart = {
                viewModel.searchState.collect()
            },
            given = {
                viewModel.resetNavigation()
            },
            assert = arrayOf({
                val actual = viewModel.searchState.value
                val expected = SearchNavigationDirection.Empty
                assertEquals(expected, actual.navigation)
            })
        )
    }
}