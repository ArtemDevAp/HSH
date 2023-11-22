package com.artem.mi.hsh.core.features

import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
@Parcelize
data class HospitalSearchParametersModel(
    @SerialName("type_key") val type: Int,
    val serviceName: String,
    val locality: String,
    val voivodeship: String
) : Parcelable {
    fun toJson(): String {
        return Uri.encode(Json.encodeToString(this))
    }
}

val HospitalSearchParcelableType =
    object : NavType<HospitalSearchParametersModel>(isNullableAllowed = false) {

        override val name: String get() = "SearchOutputParameters"

        override fun get(bundle: Bundle, key: String): HospitalSearchParametersModel? {
            return bundle.getParcelable(key)
        }

        override fun parseValue(value: String): HospitalSearchParametersModel {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun put(bundle: Bundle, key: String, value: HospitalSearchParametersModel) {
            bundle.putParcelable(key, value)
        }
    }