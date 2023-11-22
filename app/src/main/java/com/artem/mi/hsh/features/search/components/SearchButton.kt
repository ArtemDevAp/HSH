package com.artem.mi.hsh.features.search.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.artem.mi.hsh.R

@Composable
internal fun SearchButton(
    modifier: Modifier,
    onSearchPressed: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onSearchPressed
    ) {
        Text(text = stringResource(id = R.string.search_screen_button_search))
    }
}