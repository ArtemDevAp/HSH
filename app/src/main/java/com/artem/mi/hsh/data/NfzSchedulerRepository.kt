package com.artem.mi.hsh.data

import com.artem.mi.hsh.data.model.NfzNetworkModel
import com.artem.mi.hsh.data.remote.NfzHospitalRemote
import com.artem.mi.hsh.data.remote.NfzHospitalRemoteImpl
import com.artem.mi.hsh.data.remote.RemoteSearchInput
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface NfzSchedulerRepository {
    suspend fun fetchAllAvailableDays(input: RemoteSearchInput): List<NfzNetworkModel>
}

class NfzSchedulerRepositoryImpl(
    private val nfzHospitalRemote: NfzHospitalRemote = NfzHospitalRemoteImpl(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : NfzSchedulerRepository {

    override suspend fun fetchAllAvailableDays(
        input: RemoteSearchInput
    ): List<NfzNetworkModel> = withContext(dispatcher) {
        nfzHospitalRemote.fetchHospitals(input)
    }
}