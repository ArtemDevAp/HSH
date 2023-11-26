package com.artem.mi.hsh.domain

import com.artem.mi.hsh.data.NfzFilterOptionsRepository

class FetchSuggestionTownUseCase(
    private val nfzFilterOptionsRepository: NfzFilterOptionsRepository
) {
    suspend operator fun invoke(
        town: String,
        voivodeship: String
    ): List<String> =
        if (town.isShort()) emptyList()
        else nfzFilterOptionsRepository.townDictionary(town, voivodeship)

    private fun String.isShort(): Boolean {
        return this.length < MIN_CHARACTER
    }

    private companion object {
        const val MIN_CHARACTER = 3
    }
}