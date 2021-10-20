package com.example.detailsui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.detailsdata.DetailsViewModel
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun DetailUI(){

    val viewModel: DetailsViewModel = hiltViewModel()

   Scaffold(
       topBar = {
           TopAppBar(
               title={},
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
               Text(
                   text = "Details",
                   modifier = Modifier.align(Alignment.Center)
               )
           }
       },
       bottomBar = {
            BottomBar(modifier = Modifier.navigationBarsPadding())
       }
   )
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