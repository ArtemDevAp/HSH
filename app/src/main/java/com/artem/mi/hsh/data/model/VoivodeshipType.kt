package com.artem.mi.hsh.data.model

enum class VoivodeshipType(val code: String) {
    Empty(""),
    LowerSilesia("01"),
    LesserPoland("06"),
    Opolskie("08");

    companion object {
        fun list() = values().asList()
    }
}