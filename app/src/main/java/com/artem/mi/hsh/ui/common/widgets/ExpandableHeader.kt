package com.artem.mi.hsh.ui.common.widgets

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.artem.mi.hsh.R
import com.artem.mi.hsh.ui.common.DevicesPreview

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun ExpandableHeader(
    title: String,
    onHeaderIconClick: () -> Unit,
    expanded: Boolean,
    expandedContent: @Composable () -> Unit
) {
    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }
    val transition = updateTransition(transitionState, label = "transition")

    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = EXPANSTION_TRANSITION_DURATION)
    }, label = "rotationDegreeTransition") {
        if (expanded) 180f else 0f
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.secondary)
            .padding(
                horizontal = 8.dp,
                vertical = 8.dp
            )
    ) {
        Column {
            Box {
                HeaderArrow(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    degrees = arrowRotationDegree,
                    onClick = onHeaderIconClick
                )
                HeaderTitle(title = title)
            }
            ExpandableContent(visible = expanded, expandedContent)
        }
    }
}

@Composable
private fun HeaderArrow(
    modifier: Modifier = Modifier,
    degrees: Float,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        content = {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_search_24),
                contentDescription = "Expandable Arrow",
                modifier = Modifier.rotate(degrees),
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

@Composable
private fun ExpandableContent(
    visible: Boolean = true,
    expandedContent: @Composable () -> Unit
) {
    val enterTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
        ) + fadeIn(
            initialAlpha = 0.3f,
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
        )
    }
    val exitTransition = remember {
        shrinkVertically(
            // Expand from the top.
            shrinkTowards = Alignment.Top,
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
        ) + fadeOut(
            // Fade in with the initial alpha of 0.3f.
            animationSpec = tween(EXPANSTION_TRANSITION_DURATION)
        )
    }

    AnimatedVisibility(
        visible = visible,
        enter = enterTransition,
        exit = exitTransition
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            expandedContent()
        }
    }
}

private const val EXPANSTION_TRANSITION_DURATION = 450

@DevicesPreview
@Composable
private fun PreviewExpandableContainer() {
    val expand = remember {
        mutableStateOf(false)
    }
    ExpandableHeader(
        title = stringResource(id = R.string.app_name),
        { expand.value = !expand.value },
        expand.value,
        {
            Text(
                text = "Expandable content here",
                textAlign = TextAlign.Center
            )
        }
    )
}