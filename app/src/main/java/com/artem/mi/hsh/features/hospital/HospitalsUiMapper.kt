package com.artem.mi.hsh.features.hospital

import com.artem.mi.hsh.R
import com.artem.mi.hsh.data.model.NfzNetworkModel
import com.artem.mi.hsh.features.hospital.model.HospitalUiModel
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

interface HospitalsUiMapper {
    suspend fun map(list: List<NfzNetworkModel>): HospitalViewState
    fun mapError(exception: Exception): HospitalViewState
}

class HospitalsUiMapperImpl : HospitalsUiMapper {

    override suspend fun map(list: List<NfzNetworkModel>): HospitalViewState {
        return if (list.isEmpty()) {
            HospitalViewState.Empty
        } else {
            val hospitalsUi = list.mapIndexed { index, nfzNetworkModel ->
                coroutineContext.ensureActive()
                with(nfzNetworkModel) {
                    HospitalUiModel(
                        uniqueId = index,
                        label = hospitalName,
                        description = service,
                        profile = service,
                        address = address,
                        phoneNumber = number,
                        lastUpdateDate = lastUpdateDate,
                        availableDate = availableDate
                    )
                }
            }
            HospitalViewState.Data(hospitalsUi)
        }
    }

    override fun mapError(exception: Exception): HospitalViewState {
        return HospitalViewState.Error(R.string.something_went_wrong)
    }
}