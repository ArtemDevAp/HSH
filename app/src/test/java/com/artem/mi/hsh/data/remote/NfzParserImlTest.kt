package com.artem.mi.hsh.data.remote

import com.artem.mi.hsh.data.model.NfzNetworkModel
import com.artem.mi.hsh.data.remote.mock.MockHtmlPage
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.assertEquals

class NfzParserImlTest {

    private val nfzParser: NfzContentParser = NfzContentParserImpl

    private val first = NfzNetworkModel(
        lastUpdateDate = "14.11.2023 r.",
        availableDate = "15.11.2023 r.",
        hospitalName = "NZOZ PANACEUM SPÓŁKA Z OGRANICZONĄ ODPOWIEDZIALNOŚCIĄ",
        service = "PORADNIA DERMATOLOGICZNA",
        address = "RUDA ŚLĄSKA, UL. SOLIDARNOSCI 12",
        number = "+48 32 242 62 19"
    )
    private val second = NfzNetworkModel(
        lastUpdateDate = "14.11.2023 r.",
        availableDate = "15.11.2023 r.",
        hospitalName = """NIEPUBLICZNY ZAKŁAD OPIEKI ZDROWOTNEJ "DERMED" S.C.""",
        service = "PORADNIA DERMATOLOGICZNA",
        address = "ŻYWIEC, UL. AL. PIŁSUDSKIEGO 76",
        number = "+48 33 861 00 71"
    )

    @Test
    fun `parse full html page then expect correct result`() = runBlocking {
        val result = nfzParser.parse(MockHtmlPage.fullPage)
        val expect = listOf(first, second)
        assertEquals(expect, result.dropLast(8))
    }
}