package com.example.artui


import androidx.compose.animation.ExperimentalAnimationApi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems

import com.example.artdata.ArtViewModel
import com.example.detailsdestination.DetailsDestination
import com.example.errorcomponent.ErrorMessage

import com.example.image.ImagesUIContent
import com.example.paging.PagingUIProviderViewModel
import com.example.paging.appendState
import com.example.strings.R
import com.example.topappbar.WallpaperTopAppBar
import com.funkymuse.composed.core.rememberBooleanDefaultFalse
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ArtUI() {

    val viewModel : ArtViewModel = hiltViewModel()
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

    ImagesUIContent(
        pagingItems = pagingItems,
        progressVisibility = progressVisibility,
        retry = {
            retry()
        },
        onPaggingError = {
            pagingUIUIProvider.OnError(
                scope = scope,
                pagingItems = pagingItems,
                noInternetUI = {
                    ErrorMessage(text = R.string.images_no_connection)
                },
                errorUI = {
                    ErrorMessage(text = R.string.images_no_connection)
                    retry()
                }
            )
        },
        onclick = {id->
            viewModel.navigate(DetailsDestination.createWallPaperDetailsRoute(id.toString()))
        },
        topBarText = stringResource(id = R.string.art)
    )
}