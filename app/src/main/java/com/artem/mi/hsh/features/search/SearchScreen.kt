package com.artem.mi.hsh.features.search

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.artem.mi.hsh.R
import com.artem.mi.hsh.features.search.components.HospitalDropDown
import com.artem.mi.hsh.features.search.components.StringDropDown
import com.artem.mi.hsh.features.search.model.RadioTypeOption
import com.artem.mi.hsh.features.search.model.Voivodeship
import com.artem.mi.hsh.features.search.navigation.SearchNavigationDirection
import com.artem.mi.hsh.ui.common.DevicesPreview
import com.artem.mi.hsh.ui.theme.HSHTheme

private data class SearchScreenAction(
    val onOptionSelected: (RadioTypeOption) -> Unit,
    val onServiceTextChanged: (String) -> Unit,
    val onServiceSelected: (String) -> Unit,
    val onTownTextChanged: (String) -> Unit,
    val onTownSelected: (String) -> Unit,
    val onVoivodeshipPressed: () -> Unit,
    val onVoivodeshipSelected: (Voivodeship) -> Unit,
    val onSearchPressed: () -> Unit
)

@Composable
fun SearchRoute(
    viewModel: SearchViewModel,
    onSearchSelected: (String) -> Unit
) {
    val searchState by viewModel.searchState.collectAsStateWithLifecycle()

    val actions = SearchScreenAction(
        onOptionSelected = viewModel::onReferralOptionChanged,

        onServiceTextChanged = viewModel::onServiceTextChanged,
        onServiceSelected = viewModel::onServiceSelected,

        onTownTextChanged = viewModel::onTownChanged,
        onTownSelected = viewModel::onTownSelected,

        onVoivodeshipPressed = viewModel::onVoivodeshipPressed,
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
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            SearchContent(headerState = headerState, screenAction = screenAction)
            Spacer(modifier = Modifier.weight(1f))
            SearchButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
            ) { screenAction.onSearchPressed() }
        }
    }
}

@Composable
private fun SearchButton(
    modifier: Modifier,
    onSearchPressed: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onSearchPressed
    ) { Text(text = stringResource(id = R.string.search_screen_button_search)) }
}

@Composable
private fun SearchContent(
    headerState: SearchViewState,
    screenAction: SearchScreenAction
) {
    HospitalRadioGroup(
        options = headerState.referralOptions,
        selectedPosition = headerState.referralOptionSelected,
        onRadioSelected = screenAction.onOptionSelected
    )
    Spacer(modifier = Modifier.height(8.dp))
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
    Spacer(modifier = Modifier.height(16.dp))
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
    Spacer(modifier = Modifier.height(16.dp))
    SearchScreenDropDown(
        textBefore = stringResource(R.string.hospital_screen_header_voivodeship),
    ) {
        HospitalDropDown(
            modifier = Modifier.fillMaxWidth(),
            isExpanded = headerState.voivodeshipIsExpanded,
            items = headerState.voivodeshipOptions,
            selectedItem = headerState.voivodeshipSelected,
            onExpandedChange = screenAction.onVoivodeshipPressed,
            onItemSelected = screenAction.onVoivodeshipSelected
        )
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

@Composable
private fun SearchScreenDropDown(
    textBefore: String,
    dropDownView: @Composable ColumnScope.() -> Unit
) {
    Column {
        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            text = textBefore
        )
        dropDownView()
    }
}

@Composable
private fun HospitalRadioGroup(
    options: List<RadioTypeOption>,
    selectedPosition: RadioTypeOption,
    onRadioSelected: (RadioTypeOption) -> Unit
) {
    Column {
        Text(
            text = stringResource(R.string.hospital_screen_header_referral_type),
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            options.forEach { option ->
                HshRadioButton(
                    option = option,
                    selectedPosition = selectedPosition,
                    onRadioSelected = onRadioSelected
                )
            }
        }
    }
}

@Composable
private fun HshRadioButton(
    option: RadioTypeOption,
    selectedPosition: RadioTypeOption,
    onRadioSelected: (RadioTypeOption) -> Unit
) {
    Row(
        modifier = Modifier
            .selectable(
                selected = false,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    radius = 42.dp
                )
            ) {
                onRadioSelected.invoke(option)
            }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        RadioButton(selected = option == selectedPosition, onClick = null)
        Text(
            text = option.title,
            modifier = Modifier.padding(8.dp)
        )
    }
}