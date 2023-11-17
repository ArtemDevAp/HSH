package com.artem.mi.hsh.data.remote

import com.artem.mi.hsh.data.model.NfzNetworkModel

interface NfzHospitalRemote {
    suspend fun fetchHospitals(input: RemoteSearchInput): List<NfzNetworkModel>
}

class NfzHospitalRemoteImpl(
    private val nfzClient: NfzClient = NfzClient()
) : NfzHospitalRemote {
    override suspend fun fetchHospitals(input: RemoteSearchInput): List<NfzNetworkModel> {
        return nfzClient.fetchNfzHospitals(input)
    }
}