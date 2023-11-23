package com.artem.mi.hsh.features.hospital

import com.artem.mi.hsh.data.NfzSchedulerRepository
import com.artem.mi.hsh.data.model.VoivodeshipType
import com.artem.mi.hsh.data.remote.RemoteSearchInput
import com.artem.mi.hsh.features.search.model.SearchOutputModel
import java.util.concurrent.CancellationException

class HospitalInteractor(
    private val nfzSchedulerRepositoryImpl: NfzSchedulerRepository,
    private val uiMapper: HospitalsUiMapper
) {

    suspend fun loadHospitals(input: SearchOutputModel?): HospitalViewState {
        return input?.let {
            try {
                val remoteInput = RemoteSearchInput(
                    serviceName = input.serviceName,
                    locality = input.locality,
                    voivodeship = VoivodeshipType.LesserPoland
                )
                val result = nfzSchedulerRepositoryImpl.fetchAllAvailableDays(remoteInput)
                uiMapper.map(result)
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                uiMapper.mapError(e)
            }
        } ?: HospitalViewState.EmptySearchQuery
    }
}