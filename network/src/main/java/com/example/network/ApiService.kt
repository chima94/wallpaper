package com.example.network

import com.example.network.response.pixabay.PicDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService{

    @GET("?key=23893744-79719072ba4601b17f3955f8d")
    suspend fun getPixabayPictures(
        @Query("q") query: String,
        @Query("page") index: Int
    ): Response<PicDto>
}