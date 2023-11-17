package com.artem.mi.hsh.data.remote

import com.artem.mi.hsh.data.model.VarietyType
import com.artem.mi.hsh.data.model.VoivodeshipType
import java.net.URLEncoder

data class RemoteSearchInput(
    private val pageNumber: String = "",
    private val type: VarietyType = VarietyType.EMPTY,
    private val children: Boolean = false,
    private val serviceName: String = "",
    private val voivodeship: VoivodeshipType = VoivodeshipType.Empty,
    private val locality: String = "",
) {
    private val pageNumberUrl = "&page=$pageNumber"
    private val typeUrl = "&Case=${type.numeric}"
    private val childrenUrl = "&ForChildren=$children"
    private val serviceNameUrl = "ServiceName=${URLEncoder.encode(serviceName, "UTF-8")}"
    private val voivodeshipUrl = "&State=${voivodeship.number}"
    private val localityUrl = "&Locality=$locality"

    val searchParams =
        pageNumberUrl + typeUrl + childrenUrl + serviceNameUrl + voivodeshipUrl + localityUrl
}