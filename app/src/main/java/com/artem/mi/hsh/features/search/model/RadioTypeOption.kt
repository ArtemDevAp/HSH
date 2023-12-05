package com.artem.mi.hsh.features.search.model

import com.artem.mi.hsh.data.model.VarietyType

data class RadioTypeOption(
    val title: String = "",
    val type: Int = DEFAULT_TYPE
) {
    private companion object {
        const val DEFAULT_TYPE = -1
    }
}

fun List<VarietyType>.mapFromRemote(): List<RadioTypeOption> {
    return map {
        RadioTypeOption(
            title = it.name,
            type = it.index.toInt()
        )
    }
}