package com.artem.mi.hsh.features.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun SearchScreenDropDown(
    modifier: Modifier = Modifier,
    textBefore: String,
    dropDownView: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(bottom = 4.dp),
            text = textBefore
        )
        dropDownView()
    }
}