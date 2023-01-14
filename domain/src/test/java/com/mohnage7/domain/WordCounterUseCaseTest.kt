package com.mohnage7.domain

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class WordCounterUseCaseTest {

    @Mock
    lateinit var repository: BlogRepository

    lateinit var SUT: WordCounterUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        SUT = WordCounterUseCase(repository)
    }

    @Test
    fun `given text with white spaces and no duplicates, when WordCounterUseCase is invoked, return correct words count`() {
        runBlocking {
            // prepare
            Mockito.`when`(repository.getArticleContent()).thenReturn("Hello Test World!")
            // action
            val count = SUT.invoke()
            // assertion
            assertEquals(count["hello"], 1)
        }
    }

    @Test
    fun `given text with white tabs and no duplicates, when WordCounterUseCase is invoked, return correct words count`() {
        runBlocking {
            // prepare
            Mockito.`when`(repository.getArticleContent()).thenReturn("Hello    Test World!")
            // action
            val count = SUT.invoke()
            // assertion
            assertEquals(count["hello"], 1)
        }
    }

    @Test
    fun `given text with white newline and no duplicates, when WordCounterUseCase is invoked, return correct words count`() {
        runBlocking {
            // prepare
            Mockito.`when`(repository.getArticleContent()).thenReturn("Hello\nTest World!")
            // action
            val count = SUT.invoke()
            // assertion
            assertEquals(count["hello"], 1)
        }
    }

    @Test
    fun `given text with white spaces and duplicates, when WordCounterUseCase is invoked, return correct words count`() {
        runBlocking {
            // prepare
            Mockito.`when`(repository.getArticleContent()).thenReturn("Hello Hello Test World!")
            // action
            val count = SUT.invoke()
            // assertion
            assertEquals(count["hello"], 2)
        }
    }

    @Test
    fun `given text with white spaces and duplicates, when WordCounterUseCase is invoked, ignore case sensitivity`() {
        runBlocking {
            // prepare
            Mockito.`when`(repository.getArticleContent()).thenReturn("Hello hello Test World!")
            // action
            val count = SUT.invoke()
            // assertion
            assertEquals(count["hello"], 2)
        }
    }
}