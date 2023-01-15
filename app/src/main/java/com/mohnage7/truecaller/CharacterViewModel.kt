package com.mohnage7.truecaller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohnage7.domain.CharacterAtPositionUseCase
import com.mohnage7.domain.EveryNthCharacterUseCase
import com.mohnage7.domain.WordCounterUseCase
import com.mohnage7.truecaller.di.IoDispatcher
import com.mohnage7.truecaller.viewstate.CharacterAtPositionUiState
import com.mohnage7.truecaller.viewstate.NthCharacterUiState
import com.mohnage7.truecaller.viewstate.WordsCountUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    val characterAtPositionUseCase: CharacterAtPositionUseCase,
    val wordCounterUseCase: WordCounterUseCase,
    val everyNthCharacterUseCase: EveryNthCharacterUseCase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {


    private val _charAtPositionUiState =
        MutableStateFlow<CharacterAtPositionUiState>(CharacterAtPositionUiState.IDLE)
    val charAtPositionUiState: StateFlow<CharacterAtPositionUiState> = _charAtPositionUiState

    private val _nthCharacterUiState =
        MutableStateFlow<NthCharacterUiState>(NthCharacterUiState.IDLE)
    val nthCharacterUiState: StateFlow<NthCharacterUiState> = _nthCharacterUiState

    private val _wordsCountUiState = MutableStateFlow<WordsCountUiState>(WordsCountUiState.IDLE)
    val wordsCountUiState: StateFlow<WordsCountUiState> = _wordsCountUiState


    fun getNthCharacter(nthNumber: Int) {
        viewModelScope.launch(dispatcher) {
            _nthCharacterUiState.value = NthCharacterUiState.Loading
            try {
                _nthCharacterUiState.value =
                    NthCharacterUiState.Success(everyNthCharacterUseCase(nthNumber))
            } catch (e: Exception) {
                _nthCharacterUiState.value =
                    NthCharacterUiState.Failure(e.localizedMessage)
            }
        }
    }

    fun getCharAtPosition(charPosition: Int) {
        viewModelScope.launch(dispatcher) {
            _charAtPositionUiState.value = CharacterAtPositionUiState.Loading
            try {
                _charAtPositionUiState.value =
                    CharacterAtPositionUiState.Success(characterAtPositionUseCase(charPosition))
            } catch (e: Exception) {
                _charAtPositionUiState.value =
                    CharacterAtPositionUiState.Failure(e.localizedMessage)
            }
        }
    }


    fun getWordsCount() {
        viewModelScope.launch(dispatcher) {
            _wordsCountUiState.value = WordsCountUiState.Loading
            try {
                _wordsCountUiState.value =
                    WordsCountUiState.Success(wordCounterUseCase())
            } catch (e: Exception) {
                _wordsCountUiState.value =
                    WordsCountUiState.Failure(e.localizedMessage)
            }
        }
    }
}