package com.artem.mi.hsh.features.hospital

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.artem.mi.hsh.data.NfzSchedulerRepositoryImpl
import com.artem.mi.hsh.data.model.VarietyType
import com.artem.mi.hsh.data.model.VoivodeshipType
import com.artem.mi.hsh.data.remote.RemoteSearchInput
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HospitalViewModel(
    private val searchInput: RemoteSearchInput,
    private val hospitalInteractor: HospitalInteractor
) : ViewModel() {

    private val mutableRetry = MutableSharedFlow<Unit>()
    private val retry = mutableRetry.onStart {
        emit(Unit)
    }.shareIn(viewModelScope, SharingStarted.Lazily)

    val contentState = retry.flatMapLatest {
        hospitalInteractor.loadHospitals(searchInput)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        HospitalViewState.Init
    )

    fun retry() {
        viewModelScope.launch {
            mutableRetry.emit(Unit)
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                HospitalViewModel(
                    RemoteSearchInput(
                        type = VarietyType.Stable,
                        serviceName = "Poradnia dermatologiczna",
                        locality = "Kraków",
                        voivodeship = VoivodeshipType.LesserPoland
                    ),
                    HospitalInteractor(
                        NfzSchedulerRepositoryImpl(),
                        HospitalsUiMapperImpl()
                    )
                )
            }
        }
    }
}