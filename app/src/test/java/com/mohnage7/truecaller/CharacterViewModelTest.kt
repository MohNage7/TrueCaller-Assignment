package com.mohnage7.truecaller

import com.mohnage7.domain.CharacterAtPositionUseCase
import com.mohnage7.domain.EveryNthCharacterUseCase
import com.mohnage7.domain.WordCounterUseCase
import com.mohnage7.domain.exception.InValidInputException
import com.mohnage7.truecaller.viewstate.CharacterAtPositionUiState
import com.mohnage7.truecaller.viewstate.NthCharacterUiState
import com.mohnage7.truecaller.viewstate.WordsCountUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterViewModelTest {

    @Mock
    lateinit var characterAtPositionUseCase: CharacterAtPositionUseCase

    @Mock
    lateinit var everyNthCharacterUseCase: EveryNthCharacterUseCase

    @Mock
    lateinit var wordCounterUseCase: WordCounterUseCase


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `given CharacterViewModel, when initialized, WordsCountUiState state is IDLE`() = runTest {
        val viewModel = initViewModel()
        assertEquals(viewModel.wordsCountUiState.value, WordsCountUiState.IDLE)
    }

    @Test
    fun `given CharacterViewModel, when initialized, NthCharacterUiState state is IDLE`() =
        runTest {
            val viewModel = initViewModel()
            assertEquals(viewModel.nthCharacterUiState.value, NthCharacterUiState.IDLE)
        }

    @Test
    fun `given CharacterViewModel, when initialized, CharacterAtPositionUiState state is IDLE`() =
        runTest {
            val viewModel = initViewModel()
            assertEquals(viewModel.charAtPositionUiState.value, CharacterAtPositionUiState.IDLE)
        }


    @Test
    fun `given CharacterViewModel, when getCharAtPosition is invoked, CharacterAtPositionUiState state is success`() =
        runTest {
            // prepare
            val viewModel = initViewModel()
            val char = 'c'
            `when`(characterAtPositionUseCase.invoke(10)).thenReturn(char)
            // action
            viewModel.getCharAtPosition(10)
            // assertion
            advanceUntilIdle()
            assertEquals(
                CharacterAtPositionUiState.Success(char),
                viewModel.charAtPositionUiState.value
            )
        }

    @Test
    fun `given CharacterViewModel, when getNthCharacter is invoked, NthCharacterUiState state is success`() =
        runTest {
            // prepare
            val viewModel = initViewModel()
            val result = listOf('c')
            `when`(everyNthCharacterUseCase.invoke(10)).thenReturn(result)
            // action
            viewModel.getNthCharacter(10)
            // assertion
            advanceUntilIdle()
            assertEquals(
                NthCharacterUiState.Success(result),
                viewModel.nthCharacterUiState.value
            )
        }


    @Test
    fun `given CharacterViewModel, when getWordsCount is invoked, WordsCountUiState state is success`() =
        runTest {
            // prepare
            val viewModel = initViewModel()
            val result = mapOf<String, Int>()
            `when`(wordCounterUseCase.invoke()).thenReturn(result)
            // action
            viewModel.getWordsCount()
            // assertion
            advanceUntilIdle()
            assertEquals(
                WordsCountUiState.Success(result),
                viewModel.wordsCountUiState.value
            )
        }

    @Test
    fun `given CharacterViewModel, when getCharAtPosition is invoked, CharacterAtPositionUiState state is failure`() =
        runTest {
            // prepare
            val viewModel = initViewModel()
            `when`(characterAtPositionUseCase.invoke(-1)).thenThrow(InValidInputException(""))
            // action
            viewModel.getCharAtPosition(-1)
            // assertion
            advanceUntilIdle()
            assertEquals(
                CharacterAtPositionUiState.Failure(""),
                viewModel.charAtPositionUiState.value
            )
        }

    @Test
    fun `given CharacterViewModel, when getNthCharacter is invoked, NthCharacterUiState state is failure`() =
        runTest {
            // prepare
            `when`(everyNthCharacterUseCase.invoke(-1)).thenThrow(InValidInputException(""))
            val viewModel = initViewModel()
            // action
            viewModel.getNthCharacter(-1)
            // assertion
            advanceUntilIdle()
            assertEquals(
                NthCharacterUiState.Failure(""),
                viewModel.nthCharacterUiState.value
            )
        }


    @Test
    fun `given CharacterViewModel, when getWordsCount is invoked, WordsCountUiState state is failure`() =
        runTest {
            // prepare
            val viewModel = initViewModel()
            `when`(wordCounterUseCase.invoke()).thenThrow(RuntimeException())
            // action
            viewModel.getWordsCount()
            // assertion
            advanceUntilIdle()
            assertEquals(
                WordsCountUiState.Failure(null),
                viewModel.wordsCountUiState.value
            )
        }


    private fun TestScope.initViewModel(): CharacterViewModel {
        val dispatcher = StandardTestDispatcher(testScheduler)
        return CharacterViewModel(
            characterAtPositionUseCase,
            wordCounterUseCase,
            everyNthCharacterUseCase, dispatcher
        )
    }
}