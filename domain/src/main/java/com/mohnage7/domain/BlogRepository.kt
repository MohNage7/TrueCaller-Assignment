package com.mohnage7.domain

interface BlogRepository {
    suspend fun getArticleContent(): String
}