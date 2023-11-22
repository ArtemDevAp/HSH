package com.artem.mi.hsh.features.hospital

import com.artem.mi.hsh.core.extensions.suspendTryCatch
import com.artem.mi.hsh.core.features.HospitalSearchParametersModel
import com.artem.mi.hsh.data.NfzSchedulerRepository
import com.artem.mi.hsh.data.remote.model.RemoteSearchInput

class HospitalInteractor(
    private val nfzSchedulerRepositoryImpl: NfzSchedulerRepository,
    private val uiMapper: HospitalsUiMapper
) {

    suspend fun loadHospitals(input: HospitalSearchParametersModel): HospitalViewState {
        return suspendTryCatch(
            block = {
                val remoteInput = input.mapToRemote()
                val result = nfzSchedulerRepositoryImpl.fetchAllAvailableDays(remoteInput)
                uiMapper.map(result)
            },
            exception = uiMapper::mapError
        )
    }

    private fun HospitalSearchParametersModel.mapToRemote(): RemoteSearchInput {
        return RemoteSearchInput(
            type = type.toString(),
            serviceName = serviceName,
            locality = locality,
            voivodeship = voivodeship
        )
    }
}