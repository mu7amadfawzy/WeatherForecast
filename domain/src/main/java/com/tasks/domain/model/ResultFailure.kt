package com.tasks.domain.model

enum class FailureCodeParser(val description: String) {
    DEFAULT("Unknown Error");

    companion object {
        fun parse(errorCode: String) =
            entries.find { it.name == errorCode.trim().uppercase() } ?: DEFAULT
    }
}

class FailureModel(
    val code: String? = null,
    message: String? = null,
    localMessage: String? = null
) : RuntimeException(message)
