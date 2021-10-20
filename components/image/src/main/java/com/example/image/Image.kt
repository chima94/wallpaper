package com.example.image

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.errorcomponent.ErrorMessage
import com.example.loadingcomponent.BoxShimmer
import com.example.network.response.CommonPic
import com.example.paging.PagingUIProviderViewModel
import com.example.strings.R
import com.example.topappbar.WallpaperTopAppBar
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlin.math.ceil


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ImagesUIContent(
    pagingItems: LazyPagingItems<CommonPic>,
    progressVisibility: Boolean,
    retry: () -> Unit,
    onPaggingError: @Composable () -> Unit,
    onclick: (commonPic: Int) -> Unit
) {
    Scaffold(
        topBar = { WallpaperTopAppBar(text = "Nature")}
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

            onPaggingError()

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
                        Image(item, onclick = onclick)
                    }
                }

            }
        }
    }
}


@Composable
fun Image(commonPic: CommonPic, onclick: (id : Int) ->Unit){
    val painter = rememberImagePainter(
        data = commonPic.url,
        builder = {
            crossfade(true)
        }
    )

    Surface(
        color = MaterialTheme.colors.surface,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onclick(commonPic.id!!) },
        elevation = 2.dp
    ) {
        when(painter.state){
            is ImagePainter.State.Loading ->{
                BoxShimmer(
                    padding = 0.dp,
                    imageWidth = 320.dp,
                    imageHeight = 320.dp
                )
            }
            is ImagePainter.State.Success, ImagePainter.State.Empty, is ImagePainter.State.Error->{
                Image(
                    painter = rememberImagePainter(data = commonPic.url),
                    contentDescription = null,
                    modifier = Modifier.size(250.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }


    }
}





