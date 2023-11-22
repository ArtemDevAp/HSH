package com.artem.mi.hsh.features.hospital

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.artem.mi.hsh.R
import com.artem.mi.hsh.data.NfzSchedulerRepository
import com.artem.mi.hsh.data.NfzSchedulerRepositoryImpl
import com.artem.mi.hsh.data.model.VarietyType
import com.artem.mi.hsh.data.model.VoivodeshipType
import com.artem.mi.hsh.data.remote.RemoteSearchInput
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import java.util.concurrent.atomic.AtomicInteger

class HospitalViewModel(
    private val nfzSchedulerRepositoryImpl: NfzSchedulerRepository
) : ViewModel() {

    private val atomicInt = AtomicInteger(0)

    val contentState = flow<HospitalViewState> {
        val result = nfzSchedulerRepositoryImpl.fetchAllAvailableDays(
            RemoteSearchInput(
                type = VarietyType.Stable,
                serviceName = "Poradnia dermatologiczna",
                locality = "Kraków",
                voivodeship = VoivodeshipType.LesserPoland
            )
        ).map {
            HospitalUi(
                uniqueId = atomicInt.incrementAndGet(),
                label = it.hospitalName,
                description = it.service,
                profile = it.service,
                address = it.address,
                phoneNumber = it.number,
                lastUpdateDate = it.lastUpdateDate,
                availableDate = it.availableDate
            )
        }
        emit(HospitalViewState.Data(result))
    }.catch {
        // TODO mapper for error
        emit(HospitalViewState.Error(R.string.something_went_wrong))
    }.stateIn(
        scope = viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        HospitalViewState.Loading
    )

    companion object {
        val Factory = viewModelFactory {
            initializer {
                HospitalViewModel(NfzSchedulerRepositoryImpl())
            }
        }
    }
}