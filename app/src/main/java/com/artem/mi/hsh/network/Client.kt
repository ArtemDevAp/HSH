package com.artem.mi.hsh.network

import java.net.URL

object Client {
    fun fetch(url: String): String = URL(url).readText()
}