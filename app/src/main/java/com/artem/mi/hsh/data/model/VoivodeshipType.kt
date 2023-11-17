package com.artem.mi.hsh.data.model

enum class VoivodeshipType(val number: String) {
    Empty(""),
    LowerSilesia("01"),
    LesserPoland("06"),
    Opole("08");

    companion object {
        fun list() = values().asList()
    }
}