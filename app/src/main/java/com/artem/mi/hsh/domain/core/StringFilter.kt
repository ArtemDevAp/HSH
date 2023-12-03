package com.artem.mi.hsh.domain.core

class StringFilter {

    fun minLength(input: String, minLength: Int): Boolean {
        return input.trimIndent().length < minLength
    }

    fun match(input: String, iterable: Iterable<String>): Boolean {
        val uppercaseList = iterable.map { it.uppercase() }
        val uppercaseInput = input.trimIndent().uppercase()
        return uppercaseInput in uppercaseList
    }
}