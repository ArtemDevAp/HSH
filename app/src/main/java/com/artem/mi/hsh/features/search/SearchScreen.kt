package com.artem.mi.hsh.features.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.artem.mi.hsh.R
import com.artem.mi.hsh.core.features.HospitalSearchParametersModel
import com.artem.mi.hsh.features.search.components.SearchButton
import com.artem.mi.hsh.features.search.components.SearchRadioGroup
import com.artem.mi.hsh.features.search.components.SearchScreenDropDown
import com.artem.mi.hsh.features.search.components.SearchTopBar
import com.artem.mi.hsh.features.search.components.StringDropDown
import com.artem.mi.hsh.features.search.components.VoivodeshipDropDown
import com.artem.mi.hsh.features.search.model.RadioTypeOption
import com.artem.mi.hsh.features.search.model.Voivodeship
import com.artem.mi.hsh.features.search.navigation.SearchNavigationDirection
import com.artem.mi.hsh.ui.common.DevicesPreview
import com.artem.mi.hsh.ui.common.HshScreen
import com.artem.mi.hsh.ui.theme.Dimens
import com.artem.mi.hsh.ui.theme.HSHTheme

private data class SearchScreenAction(
    val onOptionSelected: (RadioTypeOption) -> Unit,
    val onServiceTextChanged: (String) -> Unit,
    val onServiceSelected: (String) -> Unit,
    val onTownTextChanged: (String) -> Unit,
    val onTownSelected: (String) -> Unit,
    val onChangeVoivodeshipExpanded: () -> Unit,
    val onVoivodeshipSelected: (Voivodeship) -> Unit,
    val onSearchPressed: () -> Unit
)

@Composable
fun SearchRoute(
    viewModel: SearchViewModel,
    onSearchSelected: (searchParams: HospitalSearchParametersModel) -> Unit
) {
    val searchState by viewModel.searchState.collectAsStateWithLifecycle()

    val actions = SearchScreenAction(
        onOptionSelected = viewModel::onReferralOptionChanged,

        onServiceTextChanged = viewModel::onServiceTextChanged,
        onServiceSelected = viewModel::onServiceSelected,

        onTownTextChanged = viewModel::onTownChanged,
        onTownSelected = viewModel::onTownSelected,

        onChangeVoivodeshipExpanded = viewModel::onChangeVoivodeshipExpanded,
        onVoivodeshipSelected = viewModel::onVoivodeshipSelected,

        onSearchPressed = viewModel::onSearchSelected
    )

    val navigationActions = SearchNavigationDirection.Actions(
        onSearchSelected = onSearchSelected,
        resetNavigation = viewModel
    )

    SearchNavigation(
        direction = searchState.navigation,
        navigationActions = navigationActions
    )

    SearchScreen(
        headerState = searchState,
        screenAction = actions
    )
}

@Composable
private fun SearchNavigation(
    direction: SearchNavigationDirection,
    navigationActions: SearchNavigationDirection.Actions
) {
    LaunchedEffect(key1 = direction) {
        direction.navigate(navigationActions)
    }
}

@Composable
private fun SearchScreen(
    headerState: SearchViewState,
    screenAction: SearchScreenAction
) {
    val state = rememberScrollState()
    HshScreen(screenPadding = 0.dp) {
        SearchTopBar()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state)
        ) {
            SearchFilterFields(
                modifier = Modifier.padding(Dimens.screenDimens),
                headerState = headerState,
                screenAction = screenAction
            )
            Spacer(modifier = Modifier.weight(1f))
            SearchButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.screenDimens)
                    .navigationBarsPadding(),
                onSearchPressed = screenAction.onSearchPressed
            )
        }
    }
}

@Composable
private fun SearchFilterFields(
    modifier: Modifier = Modifier,
    headerState: SearchViewState,
    screenAction: SearchScreenAction
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        val focusManager = LocalFocusManager.current
        SearchRadioGroup(
            options = headerState.referralOptions,
            selectedPosition = headerState.referralOptionSelected,
            onRadioSelected = screenAction.onOptionSelected
        )
        SearchScreenDropDown(
            textBefore = stringResource(R.string.hospital_screen_department),
        ) {
            StringDropDown(
                modifier = Modifier.fillMaxWidth(),
                isExpanded = headerState.serviceIsExpanded,
                items = headerState.serviceSuggestion,
                selectedItem = headerState.service,
                onItemSelected = screenAction.onServiceSelected,
                onValueChanged = screenAction.onServiceTextChanged
            )
        }
        SearchScreenDropDown(
            textBefore = stringResource(R.string.hospital_screen_header_town),
        ) {
            StringDropDown(
                modifier = Modifier.fillMaxWidth(),
                isExpanded = headerState.townIsExpanded,
                items = headerState.townSuggestion,
                selectedItem = headerState.town,
                onItemSelected = screenAction.onTownSelected,
                onValueChanged = screenAction.onTownTextChanged
            )
        }
        SearchScreenDropDown(
            textBefore = stringResource(R.string.hospital_screen_header_voivodeship),
        ) {
            VoivodeshipDropDown(
                modifier = Modifier.fillMaxWidth(),
                isExpanded = headerState.voivodeshipIsExpanded,
                items = headerState.voivodeshipOptions,
                selectedItem = headerState.voivodeshipSelected,
                onExpandedChange = screenAction.onChangeVoivodeshipExpanded,
                onItemSelected = {
                    focusManager.clearFocus()
                    screenAction.onVoivodeshipSelected(it)
                }
            )
        }
    }
}

@DevicesPreview
@Composable
private fun PreviewSearchScreen() {
    HSHTheme {
        SearchScreen(
            headerState = SearchViewState(),
            SearchScreenAction({}, {}, {}, {}, {}, {}, {}, {})
        )
    }
}