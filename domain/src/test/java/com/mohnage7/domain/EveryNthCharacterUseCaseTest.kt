package com.mohnage7.domain

import com.mohnage7.domain.exception.InValidInputException
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class EveryNthCharacterUseCaseTest {

    @Mock
    lateinit var repository: BlogRepository

    lateinit var SUT: EveryNthCharacterUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        SUT = EveryNthCharacterUseCase(repository)
    }

    @Test
    fun `given EveryNthCharacterUseCase, when invoked, return 10th number`() {
        runBlocking {
            // prepare
            Mockito.`when`(repository.getArticleContent())
                .thenReturn("Have you ever wondered how it is to be an Android Developer that works on an app with over 250")
            // action
            val count = SUT.invoke()
            // assertion
            assertEquals('e', count[0])
            assertEquals('r', count[1])
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