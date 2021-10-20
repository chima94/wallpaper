package com.example.detailsui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.detailsdata.DetailsDataState
import com.example.detailsdata.DetailsViewModel
import com.example.network.response.CommonPic
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun DetailUI(){

    val viewModel: DetailsViewModel = hiltViewModel()
    val lifecycleOwner = LocalLifecycleOwner.current
    val stateFlowLifecycleAware = remember(viewModel.state, lifecycleOwner){
        viewModel.state.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }
    val state by stateFlowLifecycleAware.collectAsState(initial = DetailsDataState())
    var loadImage by remember{ mutableStateOf(true)}

   Scaffold(
       topBar = {
           TopAppBar(
               title={
                   state.commonPic?.tags?.let {
                       Text(
                           text = it
                       )
                   }
               },
               navigationIcon = {
                    IconButton(onClick = { viewModel.navigateUp() }) {
                       Icon(
                           imageVector = Icons.Default.ArrowBack,
                           contentDescription = null
                       )
                    }
               },
               modifier = Modifier.statusBarsPadding(),
               elevation = 0.dp
           )
       },
       content = {
           Box(
               modifier = Modifier.fillMaxSize()
           ){
               if(state.isLoading || loadImage){
                   CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
               }
               state.commonPic?.let { commonPic -> ImageContent(
                   commonPic = commonPic
               ) }
               FloatingBar(modifier = Modifier
                   .align(Alignment.BottomCenter)
                   .padding(bottom = 92.dp))
           }
       },
       bottomBar = {
            BottomBar(modifier = Modifier.navigationBarsPadding())
       }
   )
}



@Composable
fun FloatingBar(modifier: Modifier){
    Box(
        modifier = modifier
    ) {
        FloatingActionButton(
            onClick = { /*TODO*/ }
        ) {
            Icon(imageVector = Icons.Filled.Save, contentDescription = null)
        }
    }
}


@Composable
fun ImageContent(
    commonPic: CommonPic
){

    val painter = rememberImagePainter(
        data = commonPic.fullHDURL
    )

    when(painter.state){
        is ImagePainter.State.Loading ->{
            Box(modifier = Modifier.fillMaxWidth(),contentAlignment = Alignment.Center){
            }
        }
        is ImagePainter.State.Success,  ImagePainter.State.Empty , is ImagePainter.State.Error->{
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){

                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(commonPic.height.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Composable
private fun BottomBar(
    modifier: Modifier = Modifier
){
    Surface(elevation = 8.dp, modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ){
            FavoriteButton(onClick = {})
            BookmarkButton(isBookmarked = false, onClick = { /*TODO*/ })
            ShareButton(onClick = {})
        }
    }
}


@Composable
fun FavoriteButton(onClick: () -> Unit) {
    IconButton(onClick) {
        Icon(
            imageVector = Icons.Filled.ThumbUpOffAlt,
            contentDescription = null
        )
    }
}



@Composable
fun BookmarkButton(
    isBookmarked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentAlpha: Float = ContentAlpha.high
) {
    val clickLabel = stringResource(
        if (isBookmarked) com.example.strings.R.string.unbookedmarked else com.example.strings.R.string.bookmark
    )
    CompositionLocalProvider(LocalContentAlpha provides contentAlpha) {
        IconToggleButton(
            checked = isBookmarked,
            onCheckedChange = { onClick() },
            modifier = modifier.semantics {
                this.onClick(label = clickLabel, action = null)
            }
        ) {
            Icon(
                imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                contentDescription = null // handled by click label of parent
            )
        }
    }
}


@Composable
fun ShareButton(onClick: () -> Unit) {
    IconButton(onClick) {
        Icon(
            imageVector = Icons.Filled.Share,
            contentDescription = stringResource(com.example.strings.R.string.share)
        )
    }
}