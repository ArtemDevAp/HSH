package com.artem.mi.hsh.data

import com.artem.mi.hsh.data.model.VarietyType
import com.artem.mi.hsh.data.model.VoivodeshipType

interface NfzFilterOptionsRepository {
    fun fetchVarietyTypes(): List<VarietyType>
    fun voivodeshipTypes(): List<VoivodeshipType>
}

class NfzFilterOptionsRepositoryImpl : NfzFilterOptionsRepository {

    override fun fetchVarietyTypes(): List<VarietyType> = VarietyType.list()

    override fun voivodeshipTypes(): List<VoivodeshipType> = VoivodeshipType.list()
}