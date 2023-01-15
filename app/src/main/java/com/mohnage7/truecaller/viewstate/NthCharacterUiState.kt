package com.mohnage7.truecaller.viewstate

sealed class NthCharacterUiState {
    object IDLE : NthCharacterUiState()
    object Loading : NthCharacterUiState()
    data class Success(val chars: List<Char>) : NthCharacterUiState()
    data class Failure(val error: String?) : NthCharacterUiState()
}
