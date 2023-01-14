package com.mohnage7.domain

import com.mohnage7.domain.exception.InValidInputException
import javax.inject.Inject

class EveryNthCharacterUseCase @Inject constructor(private val repository: BlogRepository) {

    suspend operator fun invoke(startingNIndex: Int = 10): List<Char> {
        val contentCharArray = repository.getArticleContent().toCharArray()

        if (isValidInput(startingNIndex, contentCharArray.size)) {
            var startingIndex = startingNIndex
            val charList = mutableListOf<Char>()

            while (startingIndex < contentCharArray.size) {
                charList.add(contentCharArray[startingIndex - 1])
                startingIndex += startingNIndex
            }
            return charList
        } else {
            throw InValidInputException("$startingNIndex is out of content scope.")
        }
    }

    private fun isValidInput(startingNIndex: Int, size: Int): Boolean {
        return startingNIndex in 1 until size
    }
}