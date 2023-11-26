package com.artem.mi.hsh.features.search.model

import com.artem.mi.hsh.data.model.VoivodeshipType

data class Voivodeship(
    val code: String = "",
    val name: String = ""
)

fun List<VoivodeshipType>.mapFromRemote(): List<Voivodeship> {
    return map {
        Voivodeship(
            code = it.number,
            name = it.name
        )
    }
}