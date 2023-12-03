package com.artem.mi.hsh.features.search.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.window.PopupProperties
import com.artem.mi.hsh.R
import com.artem.mi.hsh.features.search.model.Voivodeship
import com.artem.mi.hsh.ui.common.DevicesPreview
import com.artem.mi.hsh.ui.theme.HSHTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun VoivodeshipDropDown(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    items: List<Voivodeship>,
    selectedItem: Voivodeship,
    onExpandedChange: () -> Unit,
    onItemSelected: (Voivodeship) -> Unit
) {
    val focusManager = LocalFocusManager.current
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isExpanded,
        onExpandedChange = { onExpandedChange() }
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            readOnly = true,
            value = selectedItem.titleAsString(),
            onValueChange = {},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) }
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { onExpandedChange() }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item.titleAsString()) },
                    onClick = {
                        focusManager.clearFocus()
                        onItemSelected(item)
                    })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun StringDropDown(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    items: List<String>,
    selectedItem: String,
    onValueChanged: (String) -> Unit,
    onItemSelected: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isExpanded,
        onExpandedChange = { }
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            singleLine = true,
            value = selectedItem,
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            onValueChange = onValueChanged
        )
        DropdownMenu(
            modifier = Modifier.exposedDropdownSize(),
            expanded = isExpanded,
            onDismissRequest = {},
            properties = PopupProperties(
                focusable = false
            )
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        focusManager.clearFocus()
                        onItemSelected(item)
                    })
            }
        }
    }
}

@DevicesPreview
@Composable
private fun PreviewHospitalDropDown() {
    val list = listOf(
        Voivodeship(code = "01", titleResId = R.string.lesser_poland),
        Voivodeship(code = "00", titleResId = R.string.opolskie)
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(list[0]) }

    HSHTheme {
        VoivodeshipDropDown(
            isExpanded = expanded,
            items = list,
            selectedItem = selectedItem,
            onExpandedChange = {
                expanded = !expanded
            },
            onItemSelected = { selectedItem = it }
        )
    }
}