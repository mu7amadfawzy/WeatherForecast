package com.tasks.domain.model

enum class ServerErrorCode(val description: String) {

    DEFAULT("Unknown Error");

    companion object {
        fun parse(errorCode: String) =
            entries.find { it.name == errorCode.trim().uppercase() } ?: DEFAULT
    }
}