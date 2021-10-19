package com.example.religionui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.errorcomponent.ErrorMessage
import com.example.image.ImageUI
import com.example.paging.PagingUIProviderViewModel
import com.example.paging.appendState
import com.example.religiondata.ReligionViewModel
import com.example.topappbar.WallpaperTopAppBar
import com.funkymuse.composed.core.rememberBooleanDefaultFalse
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalAnimationApi::class,
    androidx.compose.foundation.ExperimentalFoundationApi::class
)
@Composable
fun ReligionUI(){

    val viewModel : ReligionViewModel = hiltViewModel()
    val pagingUIUIProvider: PagingUIProviderViewModel = hiltViewModel()
    val pagingItems = viewModel.data.collectAsLazyPagingItems()
    var progressVisibility by rememberBooleanDefaultFalse()
    progressVisibility = pagingUIUIProvider.progressBarVisibility(pagingItems)

    val scope = rememberCoroutineScope()

    pagingUIUIProvider.onPaginationReachError(
        pagingItems.appendState,
        com.example.strings.R.string.no_image_to_load
    )

    val retry = {
        pagingItems.refresh()
    }

    Scaffold(
        topBar = { WallpaperTopAppBar(text = "Religion")}
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedVisibility(
                visible = progressVisibility,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .wrapContentSize()
                    .padding(top = 8.dp)
                    .zIndex(2f)
            ) {
              CircularProgressIndicator()
            }

            pagingUIUIProvider.OnError(
                scope = scope,
                pagingItems = pagingItems,
                noInternetUI = {
                    ErrorMessage(text = com.example.strings.R.string.images_no_connection)
                },
                errorUI = {
                    ErrorMessage(text = com.example.strings.R.string.images_no_connection)
                    retry()
                }
            )
            val swipeToRefreshState = rememberSwipeRefreshState(isRefreshing = false)
            val columnState = rememberLazyListState()
            SwipeRefresh(
                state = swipeToRefreshState,
                onRefresh = {
                    swipeToRefreshState.isRefreshing = true
                    retry()
                    swipeToRefreshState.isRefreshing = false
                },
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    state = columnState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp),
                    contentPadding = rememberInsetsPaddingValues(
                        insets = LocalWindowInsets.current.navigationBars,
                        additionalBottom = 84.dp)
                ){

                    items(pagingItems, key = {it.id!!}){item ->
                        item ?: return@items
                        ImageUI(item)
                    }
                }

            }
        }
    }

}