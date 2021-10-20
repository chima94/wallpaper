package com.example.repository

import com.example.dispatchers.IoDispatcher
import com.example.network.ApiService
import com.example.network.response.CommonPic
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) {


    suspend fun getPictures(query: String, page: Int): List<CommonPic>{
        val list = mutableListOf<CommonPic>()

        withContext(dispatcher){
            val results = apiService.getPixabayPictures(query, page)
            val listResults = results.body()?.hits?.map {
                CommonPic(
                    url = it.webformatURL.orEmpty(),
                    width = it.imageWidth,
                    height = it.imageHeight,
                    tags = it.tags,
                    imageURL = it.largeImageURL,
                    fullHDURL = it.webformatURL,
                    id = it.id,
                    videoId = ""
                )
            }
            listResults?.let { list.addAll(it) }
        }
        return list
    }
}