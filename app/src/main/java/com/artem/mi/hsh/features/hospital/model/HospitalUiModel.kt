package com.artem.mi.hsh.features.hospital.model

data class HospitalUiModel(
    val uniqueId: Int,
    val label: String,
    val description: String,
    val profile: String,
    val address: String,
    val phoneNumber: String,
    val lastUpdateDate: String,
    val availableDate: String
)