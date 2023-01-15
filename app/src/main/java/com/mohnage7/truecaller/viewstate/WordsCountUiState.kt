package com.mohnage7.truecaller.viewstate

sealed class WordsCountUiState {
    object IDLE : WordsCountUiState()
    object Loading : WordsCountUiState()
    data class Success(val wordsCountMap: Map<String, Int>) : WordsCountUiState()
    data class Failure(val error: String?) : WordsCountUiState()
}
