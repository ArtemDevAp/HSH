package com.artem.mi.hsh.data.model

enum class VarietyType(val index: String) {
    Stable("1"),
    Immediately("2");

    companion object {
        fun list(): List<VarietyType> = values().asList()
    }
}