package com.artem.mi.hsh.features.search.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.artem.mi.hsh.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchTopBar() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            actionIconContentColor = MaterialTheme.colorScheme.background
        ),
        title = {
            Text(text = stringResource(id = R.string.search_screen_header_title))
        }
    )
}