package com.mohnage7.data

import com.mohnage7.data.exception.ContentNotFoundException
import com.mohnage7.domain.BlogRepository
import com.mohnage7.remote.RemoteDataSource
import javax.inject.Inject

class BlogRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BlogRepository {

    override suspend fun getArticleContent(): String {
        val response = remoteDataSource.fetchContent()
        return if (response.isSuccessful) {
            response.body().toString().trim()
        } else throw ContentNotFoundException(response.message())
    }
}