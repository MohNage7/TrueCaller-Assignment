package com.mohnage7.truecaller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mohnage7.truecaller.ui.ExpandableText
import com.mohnage7.truecaller.ui.WordsCountItem
import com.mohnage7.truecaller.ui.theme.TrueCallerTheme
import com.mohnage7.truecaller.viewstate.CharacterAtPositionUiState
import com.mohnage7.truecaller.viewstate.NthCharacterUiState
import com.mohnage7.truecaller.viewstate.WordsCountUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterActivity : ComponentActivity() {

    private val viewModel: CharacterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val wordsCountUiState: WordsCountUiState by viewModel.wordsCountUiState.collectAsState()
            TrueCallerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(1.0f)
                    ) {

                        LazyColumn {
                            item {
                                RenderToolbar()
                            }
                            item {
                                RenderCharacterAtPositionUiState()
                                Divider(color = Color.Gray, thickness = 1.dp)
                            }
                            item {
                                RenderNthCharacterUiState()
                                Divider(color = Color.Gray, thickness = 1.dp)
                            }
                            when (val state = wordsCountUiState) {
                                is WordsCountUiState.Failure -> item { RenderErrorState(state.error) }
                                WordsCountUiState.Loading -> item { ShowLoading(stringResource(R.string.msg_loading_words_count)) }
                                is WordsCountUiState.Success -> renderWordsCounterSuccessState(state.wordsCountMap)
                                WordsCountUiState.IDLE -> item {
                                    RenderIdlState(message = stringResource(id = R.string.msg_pending_words_count))
                                }
                            }
                        }

                        Button(
                            onClick = { fetchData() },
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .align(Alignment.BottomStart),
                        ) {
                            Text(text = getString(R.string.btn_title_run))
                        }

                    }

                }
            }
        }
    }

    private fun LazyListScope.renderWordsCounterSuccessState(wordsCountMap: Map<String, Int>) {
        wordsCountMap.forEach {
            item {
                WordsCountItem(it)
            }
        }
    }

    @Composable
    private fun RenderNthCharacterUiState() {
        val nthCharacterUiState: NthCharacterUiState by viewModel.nthCharacterUiState.collectAsState()
        when (val state = nthCharacterUiState) {
            is NthCharacterUiState.Failure -> RenderErrorState(state.error)
            is NthCharacterUiState.Loading -> ShowLoading(stringResource(R.string.msg_loading_nth_character))
            is NthCharacterUiState.Success -> {
                val chars = state.chars
                ExpandableText(text = chars.joinToString(","))
            }
            NthCharacterUiState.IDLE -> RenderIdlState(message = stringResource(R.string.msg_pending_nth_character_occurrences))
        }
    }

    @Composable
    private fun RenderCharacterAtPositionUiState() {
        val charAtPositionUiState: CharacterAtPositionUiState by viewModel.charAtPositionUiState.collectAsState()
        when (val state = charAtPositionUiState) {
            is CharacterAtPositionUiState.Failure -> RenderErrorState(state.error)
            CharacterAtPositionUiState.Loading -> ShowLoading(stringResource(R.string.msg_loading_char_at_position))
            is CharacterAtPositionUiState.Success -> Text(
                text = stringResource(R.string.msg_char_at_position, state.char),
                modifier = Modifier.padding(16.dp)
            )
            CharacterAtPositionUiState.IDLE -> RenderIdlState(message = stringResource(id = R.string.msg_pending_char_at_position))
        }
    }

    @Composable
    private fun RenderIdlState(message: String) {
        Text(text = message, modifier = Modifier.padding(16.dp))
    }

    @Composable
    private fun RenderErrorState(error: String?) {
        val errorMessage = error ?: stringResource(R.string.generic_error_message)
        Text(
            text = errorMessage,
            modifier = Modifier.padding(4.dp)
        )
    }

    @Composable
    private fun RenderToolbar() {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.app_name),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h5
                )
            }
        )
    }

    private fun fetchData() {
        viewModel.getNthCharacter(NTH_CHARACTER)
        viewModel.getCharAtPosition(CHAR_AT_POSITION_INDEX)
        viewModel.getWordsCount()
    }


    @Composable
    fun ShowLoading(message: String) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = message, modifier = Modifier.padding(16.dp))

            val strokeWidth = 5.dp
            CircularProgressIndicator(
                modifier = Modifier.drawBehind {
                    drawCircle(
                        Color.Red,
                        radius = size.width / 2 - strokeWidth.toPx() / 2,
                        style = Stroke(strokeWidth.toPx())
                    )
                },
                color = Color.LightGray,
                strokeWidth = strokeWidth
            )
        }
    }

    companion object {
        const val NTH_CHARACTER = 10
        const val CHAR_AT_POSITION_INDEX = 10
    }
}