package com.artem.mi.hsh.data.remote

import com.artem.mi.hsh.BuildConfig
import com.artem.mi.hsh.data.model.NfzNetworkModel
import com.artem.mi.hsh.data.remote.model.RemoteSearchInput
import com.artem.mi.hsh.data.remote.model.RemoteTownDictionaryInput
import com.artem.mi.hsh.network.Client
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class TownDictionaryResponse(
    val data: List<String>
)

class NfzClient(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val client: Client = Client,
    private val baseUrl: String = BuildConfig.BASE_URL,
    private val nfzParser: NfzContentParser = NfzContentParserImpl,
    private val json: Json = Json { ignoreUnknownKeys = true }
) {
    suspend fun fetchNfzHospitals(
        input: RemoteSearchInput
    ): List<NfzNetworkModel> = withContext(dispatcher) {
        val finalUrl = baseUrl + input.searchParams
        val source = client.fetch(finalUrl.trimIndent())
        nfzParser.parse(source)
    }

    suspend fun fetchTownDictionary(
        input: RemoteTownDictionaryInput
    ): List<String> = withContext(dispatcher) {
        val url = baseUrl + LOCATION_DICTIONARY_PATH + input.asUrl
        val response = client.fetch(url)
        val listOfTown = json.decodeFromString<TownDictionaryResponse>(response)
        listOfTown.data
    }

    private companion object {
        const val LOCATION_DICTIONARY_PATH = "/LocalitiesDictionary?"
    }
}