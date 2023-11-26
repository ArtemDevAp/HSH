package com.artem.mi.hsh.features.hospital

import com.artem.mi.hsh.common.suspendTryCatch
import com.artem.mi.hsh.data.NfzSchedulerRepository
import com.artem.mi.hsh.data.remote.model.RemoteSearchInput
import com.artem.mi.hsh.features.search.model.SearchOutputParameters

class HospitalInteractor(
    private val nfzSchedulerRepositoryImpl: NfzSchedulerRepository,
    private val uiMapper: HospitalsUiMapper
) {

    suspend fun loadHospitals(input: SearchOutputParameters?): HospitalViewState {
        return input?.let {
            suspendTryCatch(
                block = {
                    val remoteInput = input.mapToRemote()
                    val result = nfzSchedulerRepositoryImpl.fetchAllAvailableDays(remoteInput)
                    uiMapper.map(result)
                },
                exception = { e ->
                    uiMapper.mapError(e)
                }
            )
        } ?: HospitalViewState.EmptySearchQuery
    }

    private fun SearchOutputParameters.mapToRemote(): RemoteSearchInput {
        return RemoteSearchInput(
            type = type.toString(),
            serviceName = serviceName,
            locality = locality,
            voivodeship = voivodeship
        )
    }
}