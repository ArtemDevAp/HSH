package com.artem.mi.hsh.domain

import com.artem.mi.hsh.data.NfzFilterOptionsRepository

class FetchSuggestionServiceUseCase(
    private val nfzFilterOptionsRepository: NfzFilterOptionsRepository
) {

    suspend operator fun invoke(input: String): List<String> =
        if (input.length <= MIN_CHARACTER) emptyList()
        else nfzFilterOptionsRepository.serviceDictionary(input)

    private companion object {
        const val MIN_CHARACTER = 3
    }
}