package com.example.paging.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.crazylegend.internetdetector.InternetDetector
import com.crazylegend.toaster.Toaster
import com.example.paging.R
import com.example.paging.appendState
import com.example.paging.prependState
import com.example.paging.refreshState
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@ViewModelScoped
class PagingUIProvider @Inject constructor(
    private val toaster: Toaster,
    private val internetDetector: InternetDetector
) : PagingUIProviderContract{

    override fun <T : Any> isDataEmptyWithError(pagingItems: LazyPagingItems<T>): Boolean {

        val append = pagingItems.appendState
        val refresh = pagingItems.refreshState
        val prepend = pagingItems.prependState
        val itemCount = pagingItems.itemCount
        return ((append is LoadState.Error || refresh is LoadState.Error || prepend is LoadState.Error) && itemCount == 0)
    }

    override fun <T : Any> isDataEmpty(pagingItems: LazyPagingItems<T>): Boolean {
        return pagingItems.itemCount == 0
    }

    override fun <T : Any> progressBarVisibility(pagingItems: LazyPagingItems<T>): Boolean {
        val append = pagingItems.appendState
        val refresh = pagingItems.refreshState
        return append is LoadState.Loading || refresh is LoadState.Loading
    }

    override fun <T : Any> isSwipeToRefreshEnabled(pagingItems: LazyPagingItems<T>): Boolean {
        val append = pagingItems.appendState
        val refresh = pagingItems.refreshState
        return append !is LoadState.Loading || refresh !is LoadState.Loading
    }


    private var isToastShown = false

    override fun onPaginationReachError(append: LoadState, errorMessage: Int) {
        if(append.endOfPaginationReached){
            if(!isToastShown){
                toaster.shortToast(errorMessage)
                isToastShown = true
            }
        }
    }

    @Composable
    override fun <T : Any> OnError(
        scope: CoroutineScope,
        pagingItems: LazyPagingItems<T>,
        noInternetUI: @Composable () -> Unit,
        errorUI: @Composable () -> Unit
    ) {
        val append = pagingItems.appendState
        val refresh = pagingItems.refreshState
        val prepend = pagingItems.prependState
        if (isLoadStateNoConnectionException(refresh) ||
            isLoadStateNoConnectionException(append) ||
            isLoadStateNoConnectionException(prepend)
        ) {

            if (pagingItems.itemCount == 0) {
                noInternetUI()
            } else {
                toaster.longToast(com.example.strings.R.string.no_internet_connection)
            }

            LaunchedEffect(scope) {
                internetDetector.state.collectLatest {
                    if (it) pagingItems.retry()
                }
            }

        } else {
            if (isDataEmptyWithError(pagingItems)) {
                errorUI()
            }
        }
    }


}