package com.mohnage7.data.exception

data class ContentNotFoundException(val error: String) : RuntimeException(error)