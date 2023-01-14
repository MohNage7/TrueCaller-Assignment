package com.mohnage7.domain

import com.mohnage7.domain.exception.InValidInputException
import javax.inject.Inject

class CharacterAtPositionUseCase @Inject constructor(private val repository: BlogRepository) {

    suspend operator fun invoke(position: Int): Char {
        return try {
            repository.getArticleContent()[position]
        } catch (e: IndexOutOfBoundsException) {
            throw InValidInputException("Char cannot be found within the content scope.")
        }
    }
}