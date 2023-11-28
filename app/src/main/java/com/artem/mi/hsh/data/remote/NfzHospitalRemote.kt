package com.artem.mi.hsh.data.remote

import com.artem.mi.hsh.data.model.NfzNetworkModel
import com.artem.mi.hsh.data.remote.model.RemoteSearchInput
import com.artem.mi.hsh.data.remote.model.RemoteTownDictionaryInput

interface NfzHospitalRemote {
    suspend fun fetchHospitals(input: RemoteSearchInput): List<NfzNetworkModel>
    suspend fun fetchTownDictionary(input: RemoteTownDictionaryInput): List<String>
    suspend fun fetchServiceDictionary(input: String): List<String>
}

class NfzHospitalRemoteImpl(
    private val nfzClient: NfzClient = NfzClient()
) : NfzHospitalRemote {
    override suspend fun fetchHospitals(input: RemoteSearchInput): List<NfzNetworkModel> {
        return nfzClient.fetchNfzHospitals(input)
    }

    override suspend fun fetchTownDictionary(input: RemoteTownDictionaryInput): List<String> {
        return nfzClient.fetchTownDictionary(input)
    }

    override suspend fun fetchServiceDictionary(input: String): List<String> {
        return nfzClient.fetchServiceDictionary(input)
    }
}