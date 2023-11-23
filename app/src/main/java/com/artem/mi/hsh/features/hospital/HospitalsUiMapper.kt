package com.artem.mi.hsh.features.hospital

import com.artem.mi.hsh.R
import com.artem.mi.hsh.data.model.NfzNetworkModel
import kotlinx.coroutines.ensureActive
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.coroutineContext

interface HospitalsUiMapper {
    suspend fun map(list: List<NfzNetworkModel>): HospitalViewState
    fun mapError(throwable: Exception): HospitalViewState
}

class HospitalsUiMapperImpl : HospitalsUiMapper {

    private val atomicInt = AtomicInteger(0)

    override suspend fun map(list: List<NfzNetworkModel>): HospitalViewState {
        val mutableNfzUiList = mutableListOf<HospitalUi>()
        for (it in list) {
            coroutineContext.ensureActive()
            val hospitalUi = HospitalUi(
                uniqueId = atomicInt.incrementAndGet(),
                label = it.hospitalName,
                description = it.service,
                profile = it.service,
                address = it.address,
                phoneNumber = it.number,
                lastUpdateDate = it.lastUpdateDate,
                availableDate = it.availableDate
            )
            mutableNfzUiList.add(hospitalUi)
        }
        return HospitalViewState.Data(mutableNfzUiList)
    }

    override fun mapError(throwable: Exception): HospitalViewState {
        return HospitalViewState.Error(R.string.something_went_wrong)
    }
}