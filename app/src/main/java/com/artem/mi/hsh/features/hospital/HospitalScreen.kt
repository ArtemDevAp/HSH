package com.artem.mi.hsh.features.hospital

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.artem.mi.hsh.R
import com.artem.mi.hsh.features.hospital.model.HospitalUiModel
import com.artem.mi.hsh.ui.common.DevicesPreview
import com.artem.mi.hsh.ui.common.widgets.HshHeaderIcon
import com.artem.mi.hsh.ui.theme.HSHTheme

@Composable
fun HospitalRoute(
    viewModel: HospitalViewModel,
    onSearchIconClick: () -> Unit
) {
    val contentState by viewModel.uiState.collectAsStateWithLifecycle()

    val viewStateAction = ViewStateActions(
        onRetryPressed = viewModel::retry
    )

    HospitalScreen(
        contentState = contentState,
        viewStateActions = viewStateAction,
        onSearchIconClick = onSearchIconClick
    )
}

@Composable
private fun HospitalScreen(
    contentState: HospitalViewState,
    viewStateActions: ViewStateActions,
    onSearchIconClick: () -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            HospitalTopBar(
                headerTitle = contentState.headerTitle(),
                showSearchIcon = contentState.showSearchIcon(),
                onSearchIconClick = onSearchIconClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            contentState.DrawState(vsActions = viewStateActions)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HospitalTopBar(
    headerTitle: Int,
    showSearchIcon: Boolean,
    onSearchIconClick: () -> Unit
) {
    TopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            actionIconContentColor = MaterialTheme.colorScheme.background
        ),
        title = {
            SlideContent(content = headerTitle) { idRes ->
                Text(text = stringResource(id = idRes))
            }
        },
        actions = {
            if (showSearchIcon) {
                HshHeaderIcon(
                    iconId = R.drawable.ic_search_24,
                    onClick = onSearchIconClick
                )
            }
        }
    )
}

@Composable
private fun <T> SlideContent(
    content: T,
    contentScope: @Composable (T) -> Unit
) {
    AnimatedContent(
        targetState = content,
        transitionSpec = {
            slideInVertically(tween(350, easing = LinearOutSlowInEasing)) { fullSize ->
                fullSize
            } togetherWith slideOutVertically(
                tween(
                    350,
                    easing = LinearOutSlowInEasing
                )
            ) { fullSize -> -fullSize }
        }, label = ""
    ) { targetContent ->
        contentScope(targetContent)
    }
}

@DevicesPreview
@Composable
private fun PreviewHospitalScreen() {
    HSHTheme {
        HospitalScreen(
            HospitalViewState.Data(
                listOf(
                    HospitalUiModel(
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