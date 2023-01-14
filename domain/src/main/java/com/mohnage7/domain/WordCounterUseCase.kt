package com.mohnage7.domain

import javax.inject.Inject

class WordCounterUseCase @Inject constructor(private val repository: BlogRepository) {

    suspend operator fun invoke(): Map<String, Int> {
        val wordsArray = getBlogContent()
        val wordsCountMap = mutableMapOf<String, Int>()

        wordsArray.forEach { word ->
            val count = wordsCountMap.getOrDefaultInsensitive(word, 0)
            wordsCountMap.putInsensitive(word, count + 1)
        }
        return wordsCountMap
    }

    private suspend fun getBlogContent(): List<String> {
        return repository.getArticleContent().replace(Regex("\\s+"), " ").split(Regex("\\s+"))
    }

    private fun MutableMap<String, Int>.putInsensitive(key: String, value: Int) {
        put(key.lowercase(), value)
    }

    private fun MutableMap<String, Int>.getOrDefaultInsensitive(
        key: String,
        defaultValue: Int
    ): Int {
        return getOrDefault(key.lowercase(), defaultValue)
    }
}