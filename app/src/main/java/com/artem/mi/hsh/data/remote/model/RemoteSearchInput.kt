package com.artem.mi.hsh.data.remote.model

import java.net.URLEncoder
import java.util.Locale

data class RemoteSearchInput(
    private val search: Boolean = true,
    private val pageNumber: String = "",
    private val type: String = "",
    private val children: Boolean = false,
    private val serviceName: String = "",
    private val voivodeship: String = "",
    private val locality: String = "",
) {
    private val searchUrl = "?search=true"
    private val pageNumberUrl = "&page=$pageNumber"
    private val typeUrl = "&Case=$type"
    private val childrenUrl = "&ForChildren=$children"
    private val serviceNameUrl = "&ServiceName=${
        URLEncoder.encode(serviceName, "UTF-8").uppercase(Locale.getDefault())
    }"
    private val voivodeshipUrl = "&State=$voivodeship"
    private val localityUrl = "&Locality=$locality"

    val searchParams =
        searchUrl + pageNumberUrl + typeUrl + childrenUrl + serviceNameUrl + voivodeshipUrl + localityUrl
}