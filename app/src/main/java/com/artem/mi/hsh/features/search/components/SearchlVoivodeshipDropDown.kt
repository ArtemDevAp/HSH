package com.artem.mi.hsh.features.search.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.window.PopupProperties
import com.artem.mi.hsh.features.search.model.Voivodeship
import com.artem.mi.hsh.ui.common.DevicesPreview
import com.artem.mi.hsh.ui.theme.HSHTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HospitalDropDown(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    items: List<Voivodeship>,
    selectedItem: Voivodeship,
    onExpandedChange: () -> Unit,
    onItemSelected: (Voivodeship) -> Unit
) {
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isExpanded,
        onExpandedChange = { onExpandedChange() }
    ) {
        TextField(
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            readOnly = true,
            value = selectedItem.name,
            onValueChange = {},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) }
        )
        ExposedDropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = isExpanded,
            onDismissRequest = { onExpandedChange() }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item.name) },
                    onClick = { onItemSelected(item) })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TownDropDown(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    items: List<String>,
    selectedItem: String,
    onValueChanged: (String) -> Unit,
    onItemSelected: (String) -> Unit
) {
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isExpanded,
        onExpandedChange = { }
    ) {
        TextField(
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            singleLine = true,
            value = TextFieldValue(
                text = selectedItem,
                selection = TextRange(selectedItem.length)
            ),
            onValueChange = {
                onValueChanged.invoke(it.text)
            },
            keyboardOptions = KeyboardOptions(
                autoCorrect = false
            )
        )
        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .exposedDropdownSize(),
            expanded = isExpanded,
            onDismissRequest = {},
            properties = PopupProperties(
                focusable = false
            )
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = { onItemSelected(item) })
            }
        }
    }
}

@DevicesPreview
@Composable
private fun PreviewHospitalDropDown() {
    val list = listOf(
        Voivodeship(code = "01", name = "Test1"),
        Voivodeship(code = "00", name = "Test0")
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(list[0]) }

    HSHTheme {
        HospitalDropDown(
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