package com.artem.mi.hsh.features.hospital

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn

class HospitalViewModel : ViewModel() {

    //region TODO NSN-5 Replace with real source
    private fun fetchHospitals() = flowOf(
        HospitalState(
            listOf(
                HospitalUi(
                    uniqueId = 0,
                    label = "label",
                    description = "description",
                    profile = "profile",
                    address = "Address",
                    phoneNumber = "+48 34 364 02 59",
                    lastUpdateDate = "17.11.2023 r.",
                    availableDate = "20.11.2023 r."
                )
            )
        )
    )
    //endregion

    val contentState = fetchHospitals()
        .stateIn(
            scope = viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            HospitalState()
        )

    companion object {
        val Factory = viewModelFactory {
            initializer {
                HospitalViewModel()
            }
        }
    }
}