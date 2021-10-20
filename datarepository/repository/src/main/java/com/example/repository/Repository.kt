package com.example.repository

import android.util.Log
import com.example.dispatchers.IoDispatcher
import com.example.network.ApiService
import com.example.network.response.CommonPic
import com.example.network.response.pixabay.PicDto
import com.example.repository.retrofitextension.handleUseCaseException
import com.example.util.DataState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
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


    fun getImageById(id: Int): Flow<DataState<CommonPic>> = flow{
        emit(DataState.loading())
        val result = apiService.getPixabayPicturesId(id)
        val listResults = result.body()?.hits?.map {
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
        emit(DataState.data(data = listResults?.get(0), response = null))
    }.catch { e ->
        emit(handleUseCaseException(e))
    }
}