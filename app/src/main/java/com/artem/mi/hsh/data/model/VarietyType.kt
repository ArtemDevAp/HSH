package com.artem.mi.hsh.data.model

enum class VarietyType(val numeric: String) {
    EMPTY(""),
    Stable("1"),
    Immediately("2");

    companion object {
        fun list(): List<VarietyType> = values().asList()
    }
}