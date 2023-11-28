package com.artem.mi.hsh.features.hospital

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.artem.mi.hsh.data.NfzSchedulerRepositoryImpl
import com.artem.mi.hsh.features.hospital.navigation.HOSPITAL_SEARCH_ARG
import com.artem.mi.hsh.features.search.model.SearchOutputParameters
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HospitalViewModel(
    savedStateHandle: SavedStateHandle,
    private val hospitalInteractor: HospitalInteractor
) : ViewModel() {

    private val query = savedStateHandle.get<SearchOutputParameters>(HOSPITAL_SEARCH_ARG)

    private val mutableUiState = MutableStateFlow<HospitalViewState>(HospitalViewState.Loading)
    val uiState: StateFlow<HospitalViewState> get() = mutableUiState

    init {
        refreshData()
    }

    private fun refreshData() {
        viewModelScope.launch {
            mutableUiState.emit(HospitalViewState.Loading)
            val loadedHospitals = hospitalInteractor.loadHospitals(query)
            mutableUiState.emit(loadedHospitals)
        }
    }

    fun retry() {
        refreshData()
    }

    companion object {
        val factory = viewModelFactory {
            initializer {
                HospitalViewModel(
                    savedStateHandle = createSavedStateHandle(),
                    hospitalInteractor = HospitalInteractor(
                        NfzSchedulerRepositoryImpl(),
                        HospitalsUiMapperImpl()
                    )
                )
            }
        }
    }
}