package com.artem.mi.hsh.ui.common

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "phone",
    showSystemUi = true,
    device = "spec:width=411dp,height=891dp,dpi=480"
)
@Preview(
    name = "dark mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
annotation class DevicesPreview