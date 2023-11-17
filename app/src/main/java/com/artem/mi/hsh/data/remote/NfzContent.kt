package com.artem.mi.hsh.data.remote

import com.artem.mi.hsh.data.model.NfzNetworkModel
import kotlinx.coroutines.ensureActive
import java.util.regex.Pattern
import kotlin.coroutines.coroutineContext

interface NfzContentParser {
    suspend fun parse(input: String): List<NfzNetworkModel>
}

object NfzContentParserImpl : NfzContentParser {

    private val pattern = Pattern.compile(
        """
            date-info">stan na (.*?)</span>.*?<p class="result-date">(.*?)</p>.*?<p class="swd-name line-height-xs margin0px">.*?<span class="visuallyhidden">.*?</span>(.*?)\n.*?</span>(.*?)\n.*?Adres:</span><strong>(.*?)</.*?fon:</span><strong>(.*?)</strong
        """.trimIndent(),
        Pattern.DOTALL
    )

    override suspend fun parse(
        input: String
    ): List<NfzNetworkModel> {
        val matcher = pattern.matcher(input)
        val massive = mutableListOf<NfzNetworkModel>()

        while (matcher.find()) {
            coroutineContext.ensureActive()
            massive += NfzNetworkModel(
                lastUpdateDate = matcher.group(1).orEmpty(),
                availableDate = matcher.group(2).orEmpty(),
                hospitalName = matcher.group(3).orEmpty(),
                service = matcher.group(4).orEmpty(),
                address = matcher.group(5).orEmpty(),
                number = matcher.group(6).orEmpty()
            )
        }
        return massive
    }
}