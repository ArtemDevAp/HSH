package com.artem.mi.hsh.ui.common.widgets

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.artem.mi.hsh.R
import com.artem.mi.hsh.ui.common.DevicesPreview

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun HshHeader(
    title: String,
    headerIcons: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.secondary)
            .padding(
                horizontal = 8.dp,
                vertical = 8.dp
            )
    ) {
        Box {
            headerIcons()
            HeaderTitle(title = title)
        }
    }
}

@Composable
fun HshHeaderIcon(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        content = {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = "Expandable Arrow"
            )
        }
    )
}

@Composable
private fun HeaderTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        textAlign = TextAlign.Center,
    )
}

@DevicesPreview
@Composable
private fun PreviewExpandableContainer() {
    HshHeader(
        title = stringResource(id = R.string.app_name),
        headerIcons = {
            Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                HshHeaderIcon(iconId = R.drawable.ic_launcher_foreground) {}
            }
        }
    )
}