package com.example.network

import com.example.constants.Constants
import com.example.network.response.pixabay.PicDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService{

    @GET(Constants.key)
    suspend fun getPixabayPictures(
        @Query("q") query: String,
        @Query("page") index: Int
    ): Response<PicDto>

    @GET(Constants.key)
    suspend fun getPixabayPicturesId(
        @Query("id") id: Int
    ): Response<PicDto>
}