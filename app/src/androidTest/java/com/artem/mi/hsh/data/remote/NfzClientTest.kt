package com.artem.mi.hsh.data.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.artem.mi.hsh.data.model.VarietyType
import com.artem.mi.hsh.data.model.VoivodeshipType
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NfzClientTest {

    private val nfzClient = NfzClient()

    @Test
    fun test() = runTest {
        val input = RemoteSearchInput(
            pageNumber = "1",
            type = VarietyType.Immediately,
            serviceName = "PORADNIA DERMATOLOGICZNA",
            voivodeship = VoivodeshipType.LesserPoland,
            locality = "Kraków"
        )
        val pageHtml =  nfzClient.fetchNfzHospitals(input)
        assert(pageHtml.isNotEmpty())
    }
}