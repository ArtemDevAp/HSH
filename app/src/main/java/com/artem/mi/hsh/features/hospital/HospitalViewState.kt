package com.artem.mi.hsh.features.hospital

data class HospitalUi(
    val uniqueId: Int,
    val label: String,
    val description: String,
    val profile: String,
    val address: String,
    val phoneNumber: String,
    val lastUpdateDate: String,
    val availableDate: String
)

data class HospitalState(
    val hospitals: List<HospitalUi> = emptyList()
)