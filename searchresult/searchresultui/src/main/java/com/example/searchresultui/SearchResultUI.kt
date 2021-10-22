package com.example.searchresultui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.detailsdestination.DetailsDestination
import com.example.errorcomponent.ErrorMessage
import com.example.image.ImagesUIContent
import com.example.paging.PagingUIProviderViewModel
import com.example.paging.appendState
import com.example.searchresultdata.SearchResultViewModel
import com.example.strings.R
import com.funkymuse.composed.core.rememberBooleanDefaultFalse

@Composable
fun SearchResult(){

   val viewModel: SearchResultViewModel = hiltViewModel()

    val pagingUIUIProvider: PagingUIProviderViewModel = hiltViewModel()
    val pagingItems = viewModel.data.collectAsLazyPagingItems()
    var progressVisibility by rememberBooleanDefaultFalse()
    progressVisibility = pagingUIUIProvider.progressBarVisibility(pagingItems)

    val scope = rememberCoroutineScope()

    pagingUIUIProvider.onPaginationReachError(
        pagingItems.appendState,
        R.string.no_image_to_load
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
        topBarText = viewModel.query
    )
}