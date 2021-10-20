package com.example.religionui

import androidx.compose.animation.ExperimentalAnimationApi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.detailsdestination.DetailsDestination
import com.example.errorcomponent.ErrorMessage
import com.example.image.ImagesUIContent
import com.example.paging.PagingUIProviderViewModel
import com.example.paging.appendState
import com.example.religiondata.ReligionViewModel
import com.example.strings.R

import com.funkymuse.composed.core.rememberBooleanDefaultFalse

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

        onclick = {imageUri ->
            viewModel.navigate(DetailsDestination.createWallPaperDetailsRoute(imageUri = "boy"))
        }
    )
}