package com.artem.mi.hsh.ui.common.widgets


import android.widget.TextView
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.core.text.HtmlCompat.fromHtml

@Composable
fun HtmlText(
    modifier: Modifier = Modifier,
    html: String,
    textColor: Color = MaterialTheme.colorScheme.secondary
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            TextView(context).apply {
                setTextColor(textColor.toArgb())
            }
        },
        update = { it.text = fromHtml(html, FROM_HTML_MODE_LEGACY) },
    )
}