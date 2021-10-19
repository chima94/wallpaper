package com.example.paging.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.crazylegend.retrofit.throwables.NoConnectionException
import kotlinx.coroutines.CoroutineScope

interface PagingUIProviderContract {

    fun <T : Any> isDataEmptyWithError(pagingItems: LazyPagingItems<T>):Boolean

    fun <T: Any> isDataEmpty(pagingItems: LazyPagingItems<T>): Boolean

    fun <T : Any> progressBarVisibility(pagingItems: LazyPagingItems<T>): Boolean

    fun <T : Any> isSwipeToRefreshEnabled(pagingItems: LazyPagingItems<T>): Boolean

    fun onPaginationReachError(append: LoadState, @StringRes errorMessage: Int)

    @Composable
    fun <T : Any> OnError(
        scope: CoroutineScope,
        pagingItems: LazyPagingItems<T>,
        noInternetUI: @Composable () -> Unit,
        errorUI: @Composable () -> Unit
    )

    fun isLoadStateNoConnectionException(state: LoadState): Boolean =
        state is LoadState.Error && state.error is NoConnectionException
}