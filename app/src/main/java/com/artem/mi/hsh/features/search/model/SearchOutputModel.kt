package com.artem.mi.hsh.features.search.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchOutputModel(
    val serviceName: String,
    val locality: String,
    val voivodeship: String
)