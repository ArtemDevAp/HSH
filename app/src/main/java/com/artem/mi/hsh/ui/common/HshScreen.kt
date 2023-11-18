package com.artem.mi.hsh.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.artem.mi.hsh.ui.theme.Dimens

@Composable
fun HshScreen(
    modifier: Modifier = Modifier,
    screenPadding: Dp = Dimens.screenDimens,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    windowInsetsPadding: WindowInsets = WindowInsets.safeDrawing,
    color: Color = MaterialTheme.colorScheme.background,
    header: @Composable ColumnScope.() -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(windowInsetsPadding),
        color = color
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = horizontalAlignment,
            verticalArrangement = verticalArrangement,
            content = header
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(screenPadding),
            horizontalAlignment = horizontalAlignment,
            verticalArrangement = verticalArrangement,
            content = content
        )
    }
}