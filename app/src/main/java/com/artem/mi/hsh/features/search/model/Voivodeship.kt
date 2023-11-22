package com.artem.mi.hsh.features.search.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.artem.mi.hsh.R
import com.artem.mi.hsh.data.model.VoivodeshipType

data class Voivodeship(
    val code: String = "",
    @StringRes private val titleResId: Int? = null
) {

    @Composable
    fun titleAsString() = titleResId?.let { stringResource(id = it) } ?: ""
}

fun List<VoivodeshipType>.mapFromRemote(): List<Voivodeship> {
    return map {
        val uiName = when (it) {
            VoivodeshipType.Empty -> R.string.empty_lesser
            VoivodeshipType.LesserPoland -> R.string.lesser_poland
            VoivodeshipType.Opolskie -> R.string.opolskie
            VoivodeshipType.LowerSilesia -> R.string.lower_silesia
        }
        Voivodeship(
            code = it.code,
            titleResId = uiName
        )
    }
}