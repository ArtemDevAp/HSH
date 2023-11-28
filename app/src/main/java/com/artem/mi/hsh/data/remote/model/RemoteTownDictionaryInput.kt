package com.artem.mi.hsh.data.remote.model

import java.net.URLEncoder
import java.util.Locale

data class RemoteTownDictionaryInput(
    val town: String,
    val voivodeship: String
) {
    private val townUrl = "&name=${
        URLEncoder.encode(town, "UTF-8").uppercase(Locale.getDefault())
    }"
    private val state = "&state=${
        URLEncoder.encode(voivodeship, "UTF-8").uppercase(Locale.getDefault())
    }"
    val asUrl = "$townUrl$state"
}