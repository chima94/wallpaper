package com.example.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.loadingcomponent.BoxShimmer
import com.example.network.response.CommonPic
import com.google.accompanist.insets.statusBarsPadding
import kotlin.math.ceil

@Composable
fun ImageUI(commonPic: CommonPic){
    Image(commonPic)

}





@Composable
fun Image(commonPic: CommonPic){
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





