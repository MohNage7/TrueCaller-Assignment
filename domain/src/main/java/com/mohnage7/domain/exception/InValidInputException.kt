package com.mohnage7.domain.exception

data class InValidInputException(val error: String) : RuntimeException(error)