package com.artem.mi.hsh.data.remote

import com.artem.mi.hsh.BuildConfig
import com.artem.mi.hsh.data.model.NfzNetworkModel
import com.artem.mi.hsh.network.Client

class NfzClient(
    private val client: Client = Client,
    private val baseUrl: String = BuildConfig.BASE_URL,
    private val nfzParser: NfzContentParser = NfzContentParserImpl
) {
    suspend fun fetchNfzHospitals(
        input: RemoteSearchInput
    ): List<NfzNetworkModel> {
        val finalUrl = baseUrl + input.searchParams
        val source = client.fetch(finalUrl.trimIndent())
        return nfzParser.parse(source)
    }
}