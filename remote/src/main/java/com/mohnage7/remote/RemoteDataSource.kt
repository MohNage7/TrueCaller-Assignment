package com.mohnage7.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteDataSource {

    /**
     * We can fetch any article content by passing articleName path to the endpoint.
     */
    @GET("life-at-truecaller/{articleName}")
    suspend fun fetchContent(@Path("articleName") articleName: String = "life-as-an-android-engineer"): Response<String>
}