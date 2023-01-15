package com.mohnage7.truecaller.viewstate

sealed class CharacterAtPositionUiState {
    object IDLE : CharacterAtPositionUiState()
    object Loading : CharacterAtPositionUiState()
    data class Success(val char: Char) : CharacterAtPositionUiState()
    data class Failure(val error: String?) : CharacterAtPositionUiState()
}
