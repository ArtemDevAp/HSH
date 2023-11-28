package com.artem.mi.hsh.core.mock.repository

import com.artem.mi.hsh.data.NfzFilterOptionsRepository
import com.artem.mi.hsh.data.model.VarietyType
import com.artem.mi.hsh.data.model.VoivodeshipType

class NfzFilterOptionsRepositoryMock : NfzFilterOptionsRepository {

    private var varieties: List<VarietyType> = emptyList()
    private var voivodeships: List<VoivodeshipType> = emptyList()
    private var townDictionary: List<String> = emptyList()
    private var serviceDictionary: List<String> = emptyList()

    override fun fetchVarietyTypes(): List<VarietyType> = varieties

    fun setVarietyTypes(list: List<VarietyType>) {
        varieties = list
    }

    override fun voivodeshipTypes(): List<VoivodeshipType> = voivodeships

    fun setVoivodeshipTypes(list: List<VoivodeshipType>) {
        voivodeships = list
    }

    override suspend fun townDictionary(
        town: String, voivodeship: String
    ): List<String> = townDictionary

    fun setTownDictionary(list: List<String>) {
        townDictionary = list
    }

    override suspend fun serviceDictionary(department: String): List<String> = serviceDictionary

    fun setServiceDictionary(list: List<String>) {
        serviceDictionary = list
    }
}