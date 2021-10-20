package com.example.natureui


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.*

import androidx.hilt.navigation.compose.hiltViewModel

import androidx.paging.compose.collectAsLazyPagingItems

import com.example.errorcomponent.ErrorMessage

import com.example.image.ImagesUIContent
import com.example.naturedata.NatureViewModel

import com.example.paging.PagingUIProviderViewModel
import com.example.paging.appendState

import com.funkymuse.composed.core.rememberBooleanDefaultFalse


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NatureUI(){
    val viewModel : NatureViewModel = hiltViewModel()
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
                    ErrorMessage(text = com.example.strings.R.string.images_no_connection)
                },
                errorUI = {
                    ErrorMessage(text = com.example.strings.R.string.images_no_connection)
                    retry()
                }
            )
        },
        onclick = {}
    )
}

