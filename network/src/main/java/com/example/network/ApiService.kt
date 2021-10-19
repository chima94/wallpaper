package com.example.network

import com.example.network.response.pixabay.PicDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService{

    @GET("")
    suspend fun getPixabayPictures(
        @Query("q") query: String,
        @Query("page") index: Int
    ): Response<PicDto>
}