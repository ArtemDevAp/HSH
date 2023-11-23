package com.artem.mi.hsh.features.hospital

import com.artem.mi.hsh.data.NfzSchedulerRepository
import com.artem.mi.hsh.data.remote.RemoteSearchInput
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import java.util.concurrent.CancellationException

class HospitalInteractor(
    private val nfzSchedulerRepositoryImpl: NfzSchedulerRepository,
    private val uiMapper: HospitalsUiMapper
) {

    fun loadHospitals(input: RemoteSearchInput) =  flow {
        val result = try {
            val result = nfzSchedulerRepositoryImpl.fetchAllAvailableDays(input)
            uiMapper.map(result)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            uiMapper.mapError(e)
        }
        emit(result)
    }.onStart {
        emit(HospitalViewState.Loading)
    }
}