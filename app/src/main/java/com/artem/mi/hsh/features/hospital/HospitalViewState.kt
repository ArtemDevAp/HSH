package com.artem.mi.hsh.features.hospital

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.artem.mi.hsh.R
import com.artem.mi.hsh.features.hospital.model.HospitalUiModel

data class ViewStateActions(
    val onRetryPressed: () -> Unit
)

sealed interface HospitalViewState {

    @Composable
    fun DrawState(vsActions: ViewStateActions) = Unit

    fun showSearchIcon(): Boolean = false

    fun headerTitle(): Int = R.string.hospital_screen_header_name

    class Data(private val hospitals: List<HospitalUiModel>) : HospitalViewState {
        @Composable
        override fun DrawState(vsActions: ViewStateActions) {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = hospitals,
                    key = { it.uniqueId }
                ) {
                    HospitalListItem(
                        modifier = Modifier.background(MaterialTheme.colorScheme.onPrimary),
                        data = it
                    )
                }
            }
        }

        override fun showSearchIcon(): Boolean = true
    }

    object Loading : HospitalViewState {
        @Composable
        override fun DrawState(vsActions: ViewStateActions) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        override fun headerTitle(): Int = R.string.hospital_loading
    }

    data class Error(@StringRes private val idRes: Int) : HospitalViewState {
        @Composable
        override fun DrawState(vsActions: ViewStateActions) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(id = idRes))
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = vsActions.onRetryPressed) {
                        Text(text = stringResource(id = R.string.retry))
                    }
                }
            }
        }
    }

    object Empty : HospitalViewState {
        @Composable
        override fun DrawState(vsActions: ViewStateActions) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(id = R.string.hospital_screen_select_search))
                }
            }
        }

        override fun showSearchIcon(): Boolean = true
    }
}