package com.artem.mi.hsh.features.search

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.artem.mi.hsh.R
import com.artem.mi.hsh.features.search.components.HospitalDropDown

import com.artem.mi.hsh.features.search.model.RadioTypeOption
import com.artem.mi.hsh.features.search.model.Voivodeship
import com.artem.mi.hsh.features.search.navigation.SearchNavigationDirection
import com.artem.mi.hsh.ui.common.DevicesPreview
import com.artem.mi.hsh.ui.theme.HSHTheme

private data class SearchScreenAction(
    val onOptionSelected: (RadioTypeOption) -> Unit,
    val onServiceTextChanged: (String) -> Unit,
    val onTownTextChanged: (String) -> Unit,
    val onVoivodeshipPressed: () -> Unit,
    val onVoivodeshipSelected: (Voivodeship) -> Unit,
    val onSearchPressed: () -> Unit
)

@Composable
fun SearchRoute(
    viewModel: SearchViewModel = viewModel(),
    onSearchSelected: (String) -> Unit
) {
    val searchState by viewModel.searchState.collectAsStateWithLifecycle()

    val actions = SearchScreenAction(
        onOptionSelected = viewModel::onReferralOptionChanged,
        onServiceTextChanged = viewModel::onServiceTextChanged,
        onTownTextChanged = viewModel::onTownChanged,
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HospitalRadioGroup(
                options = headerState.referralOptions,
                selectedPosition = headerState.referralOptionSelected,
                onRadioSelected = screenAction.onOptionSelected
            )
            Spacer(modifier = Modifier.height(8.dp))
            SearchTextField(
                textBefore = stringResource(id = R.string.hospital_screen_department),
                textField = headerState.service,
                onValueChange = screenAction.onServiceTextChanged
            )
            Spacer(modifier = Modifier.height(8.dp))
            SearchTextField(
                textBefore = stringResource(R.string.hospital_screen_header_town),
                textField = headerState.town,
                onValueChange = screenAction.onTownTextChanged
            )
            Spacer(modifier = Modifier.height(8.dp))
            HospitalVoivodeshipDropDown(
                textBefore = stringResource(R.string.hospital_screen_header_voivodeship),
                isExpanded = headerState.voivodeshipIsExpanded,
                items = headerState.voivodeshipOptions,
                selectedItem = headerState.voivodeshipSelected,
                onExpandedChange = screenAction.onVoivodeshipPressed,
                onItemSelected = screenAction.onVoivodeshipSelected
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = screenAction.onSearchPressed
            ) { Text(text = "search") }
        }
    }
}

@DevicesPreview
@Composable
private fun PreviewSearchScreen() {
    HSHTheme {
        SearchScreen(
            headerState = SearchViewState(),
            SearchScreenAction({}, {}, {}, {}, {}, {})
        )
    }
}

@Composable
private fun HospitalVoivodeshipDropDown(
    textBefore: String,
    isExpanded: Boolean,
    items: List<Voivodeship>,
    selectedItem: Voivodeship,
    onExpandedChange: () -> Unit,
    onItemSelected: (Voivodeship) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = textBefore
        )
        HospitalDropDown(
            modifier = Modifier.weight(1f),
            isExpanded = isExpanded,
            items = items,
            selectedItem = selectedItem,
            onExpandedChange = onExpandedChange,
            onItemSelected = onItemSelected
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchTextField(
    textBefore: String,
    textField: String,
    onValueChange: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = textBefore
        )
        TextField(
            modifier = Modifier.weight(1f),
            value = textField, onValueChange = onValueChange
        )
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