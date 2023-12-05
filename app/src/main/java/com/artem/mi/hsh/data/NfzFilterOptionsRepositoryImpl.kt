package com.artem.mi.hsh.data

import com.artem.mi.hsh.data.model.VarietyType
import com.artem.mi.hsh.data.model.VoivodeshipType
import com.artem.mi.hsh.data.remote.NfzHospitalRemote
import com.artem.mi.hsh.data.remote.NfzHospitalRemoteImpl
import com.artem.mi.hsh.data.remote.model.RemoteTownDictionaryInput

interface NfzFilterOptionsRepository {
    fun fetchVarietyTypes(): List<VarietyType>
    fun voivodeshipTypes(): List<VoivodeshipType>
    suspend fun townDictionary(town: String, voivodeship: String): List<String>
    suspend fun serviceDictionary(department: String): List<String>
}

class NfzFilterOptionsRepositoryImpl(
    private val nfzClient: NfzHospitalRemote = NfzHospitalRemoteImpl()
) : NfzFilterOptionsRepository {

    override fun fetchVarietyTypes(): List<VarietyType> =
        VarietyType.list().filter { it.index.isNotEmpty() }

    override fun voivodeshipTypes(): List<VoivodeshipType> = VoivodeshipType.list()

    override suspend fun townDictionary(
        town: String,
        voivodeship: String
    ): List<String> {
        val remoteInput = RemoteTownDictionaryInput(town, voivodeship)
        return nfzClient.fetchTownDictionary(remoteInput)
    }

    override suspend fun serviceDictionary(department: String): List<String> {
        return nfzClient.fetchServiceDictionary(department)
    }
}