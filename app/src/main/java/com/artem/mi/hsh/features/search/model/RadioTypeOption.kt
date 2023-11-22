package com.artem.mi.hsh.features.search.model

data class RadioTypeOption(
    val title: String,
    val type: Int = DEFAULT_TYPE
) {
    private companion object {
        const val DEFAULT_TYPE = -1
    }
}