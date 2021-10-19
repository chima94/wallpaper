package com.example.religiondata

import android.content.Context
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.crazylegend.common.isOnline
import com.crazylegend.retrofit.throwables.NoConnectionException
import com.example.network.response.CommonPic
import com.example.paging.pagedResult
import com.example.repository.Repository
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext

class Datasource @AssistedInject constructor(
    private val repository: Repository,
    @ApplicationContext private val context: Context
): PagingSource<Int, CommonPic>(){


    @AssistedFactory
    interface DatasourceFactory{
        fun create(): Datasource
    }

    override fun getRefreshKey(state: PagingState<Int, CommonPic>): Int? = state.anchorPosition


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CommonPic> {
        val page = params.key ?: 1
        return if(context.isOnline){
            try {
                val data = repository.getPictures("religion", page)
                pagedResult(data, page)
            }catch (throwable: Throwable){
                LoadResult.Error(throwable)
            }
        }else{
            LoadResult.Error(NoConnectionException())
        }
    }
}