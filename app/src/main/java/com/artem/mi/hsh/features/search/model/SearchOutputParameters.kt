package com.artem.mi.hsh.features.search.model

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
@Parcelize
data class SearchOutputParameters(
    val type: Int,
    val serviceName: String,
    val locality: String,
    val voivodeship: String
) : Parcelable

val SearchOutputParametersType =
    object : NavType<SearchOutputParameters>(isNullableAllowed = false) {

        override val name: String get() = "SearchOutputParameters"

        override fun get(bundle: Bundle, key: String): SearchOutputParameters? {
            return bundle.getParcelable(key)
        }

        override fun parseValue(value: String): SearchOutputParameters {
            return Json.decodeFromString(value)
        }

        override fun put(bundle: Bundle, key: String, value: SearchOutputParameters) {
            bundle.putParcelable(key, value)
        }
    }