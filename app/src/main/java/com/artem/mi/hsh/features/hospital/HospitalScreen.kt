package com.artem.mi.hsh.features.hospital

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.artem.mi.hsh.R
import com.artem.mi.hsh.ui.common.DevicesPreview
import com.artem.mi.hsh.ui.common.widgets.HshHeader
import com.artem.mi.hsh.ui.common.widgets.HshHeaderIcon
import com.artem.mi.hsh.ui.theme.HSHTheme

@Composable
fun HospitalRoute() {
    val viewModel: HospitalViewModel = viewModel(factory = HospitalViewModel.Factory)
    val contentState by viewModel.contentState.collectAsStateWithLifecycle()

    val viewStateAction = ViewStateActions {
        // TODO handle retry action
    }

    HospitalScreen(
        contentState = contentState,
        viewStateActions = viewStateAction,
        onSearchIconClick = {
            // TODO open search
        }
    )
}

@Composable
private fun HospitalScreen(
    contentState: HospitalViewState,
    viewStateActions: ViewStateActions,
    onSearchIconClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            HshHeader(title = stringResource(id = contentState.headerTitle())) {
                if (contentState.dataLoaded()) {
                    Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                        HshHeaderIcon(
                            iconId = R.drawable.ic_search_24,
                            onClick = onSearchIconClick
                        )
                    }
                }
            }
            contentState.DrawState(vsActions = viewStateActions)
        }
    }
}

@DevicesPreview
@Composable
private fun PreviewHospitalScreen() {
    HSHTheme {
        HospitalScreen(
            HospitalViewState.Data(
                listOf(
                    HospitalUi(
                        uniqueId = 0,
                        label = "label",
                        description = "description",
                        profile = "profile",
                        address = "Address",
                        phoneNumber = "+48 34 364 02 59",
                        lastUpdateDate = "17.11.2023 r.",
                        availableDate = "20.11.2023 r."
                    )
                )
            ),
            viewStateActions = ViewStateActions { }
        ) {}
    }
}