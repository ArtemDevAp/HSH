package com.artem.mi.hsh.features.search.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.artem.mi.hsh.R
import com.artem.mi.hsh.features.search.model.RadioTypeOption

@Composable
internal fun SearchRadioGroup(
    modifier: Modifier = Modifier,
    options: List<RadioTypeOption>,
    selectedPosition: RadioTypeOption,
    onRadioSelected: (RadioTypeOption) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.hospital_screen_header_referral_type),
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            options.forEach { option ->
                SearchRadioButton(
                    option = option,
                    selectedPosition = selectedPosition,
                    onRadioSelected = onRadioSelected
                )
            }
        }
    }
}

@Composable
private fun SearchRadioButton(
    option: RadioTypeOption,
    selectedPosition: RadioTypeOption,
    onRadioSelected: (RadioTypeOption) -> Unit
) {
    Row(
        modifier = Modifier
            .selectable(
                selected = false,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(radius = 42.dp)
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