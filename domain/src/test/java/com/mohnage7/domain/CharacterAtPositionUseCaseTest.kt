package com.mohnage7.domain

import com.mohnage7.domain.exception.InValidInputException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CharacterAtPositionUseCaseTest {

    @Mock
    lateinit var repository: BlogRepository

    lateinit var SUT: CharacterAtPositionUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        SUT = CharacterAtPositionUseCase(repository)
    }

    @Test
    fun `given valid char position, when CharacterAtPositionUseCase is invoked, return char at this position`() {
        runBlocking {
            // prepare
            Mockito.`when`(repository.getArticleContent()).thenReturn("Hello Test World!")
            // action
            val char = SUT.invoke(2)
            // assertion
            assertEquals('l', char)
        }
    }

    @Test(expected = InValidInputException::class)
    fun `given invalid char position, when CharacterAtPositionUseCase is invoked, throws exception`() {
        runBlocking {
            // prepare
            Mockito.`when`(repository.getArticleContent()).thenReturn("Hello Test World!")
            // action
            SUT.invoke(-1)
        }
    }
}