package com.example.paging

import android.content.Context
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.compose.LazyPagingItems
import com.crazylegend.common.isOnline
import com.crazylegend.retrofit.throwables.NoConnectionException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

val LazyPagingItems<*>.appendState get() = loadState.append
val LazyPagingItems<*>.refreshState get() = loadState.refresh
val LazyPagingItems<*>.prependState get() = loadState.prepend


fun <T : Any> canNotLoadMoreContent() : PagingSource.LoadResult.Page<Int, T> =
    PagingSource.LoadResult.Page(emptyList(), null, null)


suspend fun <T : Any> fetchPaginatedContent(
    context: Context,
    dispatcher: CoroutineDispatcher,
    params: PagingSource.LoadParams<Int>,
    loadData: (page : Int) -> PagingSource.LoadResult<Int, T>
): PagingSource.LoadResult<Int, T>{

    val page = params.key ?: 1

    return if(context.isOnline){
        try{
            withContext(dispatcher){
                loadData(page)
            }
        }catch (throwable: Throwable){
            return PagingSource.LoadResult.Error(throwable)
        }
    }else{
        return PagingSource.LoadResult.Error(NoConnectionException())
    }

}


fun <T : Any> pagedResult(
    list: List<T>,
    page: Int
): PagingSource.LoadResult.Page<Int, T>{
    return if(list.isNullOrEmpty()){
        canNotLoadMoreContent()
    }else{
        val prevKey = if(!list.isNullOrEmpty()) if(page == 1) null else page - 1 else null
        val nextKey = if(list.count() == 0) null else page.plus(1)
        PagingSource.LoadResult.Page(list.toList(), prevKey, nextKey)
    }
}

