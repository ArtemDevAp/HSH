package com.artem.mi.hsh.core.mock.repository

import com.artem.mi.hsh.data.NfzFilterOptionsRepository
import com.artem.mi.hsh.data.model.VarietyType
import com.artem.mi.hsh.data.model.VoivodeshipType

class NfzFilterOptionsRepositoryMock(
    private val varieties: List<VarietyType> = emptyList(),
    private val voivodeships: List<VoivodeshipType> = emptyList(),
    private val townDictionary: List<String> = emptyList(),
    private val serviceDictionary: List<String> = emptyList()
) : NfzFilterOptionsRepository {

    override fun fetchVarietyTypes(): List<VarietyType> = varieties

    override fun voivodeshipTypes(): List<VoivodeshipType> = voivodeships

    override suspend fun townDictionary(
        town: String, voivodeship: String
    ): List<String> = townDictionary

    override suspend fun serviceDictionary(department: String): List<String> = serviceDictionary

}