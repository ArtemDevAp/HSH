//package com.artem.mi.hsh.features.hospital
//
//import com.artem.mi.hsh.core.MainDispatcherRule
//import com.artem.mi.hsh.core.collectionStateTest
//import kotlinx.coroutines.flow.collect
//import kotlinx.coroutines.test.runTest
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.Assert.assertEquals
//
//// TODO NSN-5 add tests for source(repo/use case) after added
//class HospitalViewModelTest {
//
//    @get:Rule
//    val mainDispatcherRule = MainDispatcherRule()
//
//    private lateinit var viewModel: HospitalViewModel
//
//    @Before
//    fun setup() {
//        viewModel = HospitalViewModel()
//    }
//
//    @Test
//    fun `when init screen, expect header search is close`() = runTest {
//        collectionStateTest(
//            doOnStart = {
//                viewModel.headerState.collect()
//            },
//            given = {},
//            assert = arrayOf({
//                val actual = viewModel.headerState.value
//                val expected = false
//                assertEquals(expected, actual.searchIsExpanded)
//            })
//        )
//    }
//
//    @Test
//    fun `when search icon pressed, expect correct state`() =
//        runTest {
//            collectionStateTest(
//                doOnStart = {
//                    viewModel.headerState.collect()
//                },
//                given = {
//                    viewModel.onSearchIconPressed()
//                },
//                assert = arrayOf({
//                    val actual = viewModel.headerState.value
//                    val expected = true
//                    assertEquals(expected, actual.searchIsExpanded)
//                })
//            )
//        }
//
//    @Test
//    fun `given service text, when service text changed, expect correct text`() = runTest {
//        val serviceTextTest = "test text"
//        collectionStateTest(
//            doOnStart = {
//                viewModel.headerState.collect()
//            },
//            given = {
//                viewModel.onServiceTextChanged(serviceTextTest)
//            },
//            assert = arrayOf({
//                val actual = viewModel.headerState.value
//                assertEquals(serviceTextTest, actual.service)
//            })
//        )
//    }
//
//    @Test
//    fun `when voivodeship pressed, expect correct state`() = runTest {
//        collectionStateTest(
//            doOnStart = {
//                viewModel.headerState.collect()
//            },
//            given = {
//                viewModel.onVoivodeshipPressed()
//            },
//            assert = arrayOf({
//                val actual = viewModel.headerState.value
//                val expected = true
//                assertEquals(expected, actual.voivodeshipIsExpanded)
//            })
//        )
//    }
//
//    @Test
//    fun `given voivodeship, when voivodeship selected, expect correct state`() = runTest {
//        val mockVoivodeship = Voivodeship(
//            code = "00",
//            name = "Test"
//        )
//        collectionStateTest(
//            doOnStart = {
//                viewModel.headerState.collect()
//            },
//            given = {
//                viewModel.onVoivodeshipSelected(mockVoivodeship)
//            },
//            assert = arrayOf({
//                val actual = viewModel.headerState.value
//                assertEquals(mockVoivodeship, actual.voivodeshipSelected)
//                assertEquals(false, actual.voivodeshipIsExpanded)
//            })
//        )
//    }
//
//    @Test
//    fun `given town, when town changed, expect correct state`() = runTest {
//        val mockTown = "Test"
//        collectionStateTest(
//            doOnStart = {
//                viewModel.headerState.collect()
//            },
//            given = {
//                viewModel.onTownChanged(mockTown)
//            },
//            assert = arrayOf({
//                val actual = viewModel.headerState.value
//                assertEquals(mockTown, actual.town)
//            })
//        )
//    }
//
//    @Test
//    fun `given radioTypeOption, when referral option changed, expect correct state`() = runTest {
//        val mockRadioType = RadioTypeOption(
//            title = "Test",
//            type = 0
//        )
//        collectionStateTest(
//            doOnStart = {
//                viewModel.headerState.collect()
//            },
//            given = {
//                viewModel.onReferralOptionChanged(mockRadioType)
//            },
//            assert = arrayOf({
//                val actual = viewModel.headerState.value
//                assertEquals(mockRadioType, actual.referralOptionSelected)
//            })
//        )
//    }
//
//    // TODO NSN-5 Add verification for the search trigger.
//    @Test
//    fun `when search pressed, expect correct state`() = runTest {
//        collectionStateTest(
//            doOnStart = {
//                viewModel.headerState.collect()
//            },
//            given = {
//                viewModel.onSearchPressed()
//            },
//            assert = arrayOf({
//                val actual = viewModel.headerState.value
//                val expected = false
//                assertEquals(expected, actual.searchIsExpanded)
//            })
//        )
//    }
//}