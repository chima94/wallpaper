package com.example.loadingcomponent

import androidx.annotation.StringRes
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.funkymuse.composed.core.density

/*@Composable
fun LoadingBubbles(
    @StringRes text: Int = com.example.strings.R.string.loading,
    colorDurationTransition: Int = 3000,
    scaleDuration: Int = 2000
) {
    val infiniteTransition = rememberInfiniteTransition()
    val color by infiniteTransition.animateColor(
        initialValue = MaterialTheme.colors.secondary,
        targetValue = MaterialTheme.colors.primary,
        animationSpec = infiniteRepeatable(
            animation = tween(colorDurationTransition, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val scale by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(scaleDuration),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val (width, height) = size

        val radius = size.minDimension * scale / 6f

        drawCircle(color, radius = radius)
        //bottom right
        translate(left = width / 2, height / 2f) {
            drawCircle(color, radius = radius)
        }

        //bottom left
        translate(left = -width / 2, height / 2f) {
            drawCircle(color, radius = radius)
        }

        //top left
        translate(left = -width / 2, -height / 2f) {
            drawCircle(color, radius = radius)
        }

        //top right
        translate(left = width / 2, -height / 2f) {
            drawCircle(color, radius = radius)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(id = text), Modifier.graphicsLayer(scale, scale))
    }
}



@Composable
fun CardShimmer(
    imageHeight: Dp = 160.dp,
    imageWidth: Dp? = null,
    padding: Dp = 16.dp,
    shimmerDelayDuration: Int = 300,
    shimmerDuration: Int = 1600,
    colors: List<Color> = defaultColors,
    alphaDuration: Int = 400,
    cardShape: Shape = MaterialTheme.shapes.large
) {
    val modifier = if (imageWidth != null) {
        Modifier.size(imageWidth, imageHeight)
    } else {
        Modifier.fillMaxSize()
    }
    LoadingCard(
        modifier,
        padding,
        imageHeight,
        shimmerDuration,
        shimmerDelayDuration,
        alphaDuration,
        colors,
        cardShape
    )
}*/

private val defaultColors = listOf(
    Color.LightGray.copy(alpha = .9f),
    Color.LightGray.copy(alpha = .3f),
    Color.LightGray.copy(alpha = .9f),
)

@Composable
fun BoxShimmer(
    imageHeight: Dp = 160.dp,
    imageWidth: Dp? = null,
    padding: Dp = 16.dp,
    shimmerDelayDuration: Int = 300,
    shimmerDuration: Int = 1600,
    colors: List<Color> = defaultColors,
    alphaDuration: Int = 400,
) {
    val modifier = if (imageWidth != null) {
        Modifier.size(imageWidth, imageHeight)
    } else {
        Modifier.fillMaxSize()
    }
    LoadingCard(
        modifier,
        padding,
        imageHeight,
        shimmerDuration,
        shimmerDelayDuration,
        alphaDuration,
        colors,
        null
    )
}

@Composable
private fun LoadingCard(
    modifier: Modifier,
    padding: Dp,
    imageHeight: Dp,
    shimmerDuration: Int,
    shimmerDelayDuration: Int,
    alphaDuration: Int,
    colors: List<Color>,
    cardShape: Shape?
) {
    BoxWithConstraints(
        modifier = modifier
    ) {

        val cardWidthPx = with(density) { (maxWidth - (padding * 2)).toPx() }
        val cardHeightPx = with(density) { (imageHeight - padding).toPx() }
        val gradientWidth: Float = (0.2f * cardHeightPx)
        val tweenAnim = tweenParameters(
            shimmerDuration,
            shimmerDelayDuration
        )
        val infiniteTransition = rememberInfiniteTransition()

        val xCardShimmer = cardShimmerAxis(
            cardWidthPx = cardWidthPx,
            infiniteTransition = infiniteTransition,
            gradientWidth = gradientWidth,
            tweenAnim = tweenAnim
        )

        val yCardShimmer = cardShimmerAxis(
            cardWidthPx = cardHeightPx,
            infiniteTransition = infiniteTransition,
            gradientWidth = gradientWidth,
            tweenAnim = tweenAnim
        )

        val alpha by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = alphaDuration
                    0.7f at alphaDuration / 2
                },
                repeatMode = RepeatMode.Reverse
            )
        )


        ShimmerSurfaceItem(
            colors = colors,
            xShimmer = xCardShimmer.value,
            yShimmer = yCardShimmer.value,
            cardHeight = imageHeight,
            gradientWidth = gradientWidth,
            padding = padding,
            alpha = alpha,
            cardShape = cardShape
        )
    }
}

@Composable
private fun ShimmerSurfaceItem(
    colors: List<Color>,
    xShimmer: Float,
    yShimmer: Float,
    cardHeight: Dp,
    gradientWidth: Float,
    padding: Dp,
    alpha: Float = 1f,
    cardShape: Shape?
) {
    val brush = Brush.linearGradient(
        colors,
        start = Offset(xShimmer - gradientWidth, yShimmer - gradientWidth),
        end = Offset(xShimmer, yShimmer)
    )
    Column(
        modifier = Modifier
            .padding(padding)
            .alpha(alpha)
    ) {
        if (cardShape!=null){
            Surface(shape = cardShape){
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(cardHeight)
                        .background(brush = brush)
                )
            }
        }else {
            Surface{
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(cardHeight)
                        .background(brush = brush)
                )
            }
        }
    }
}

@Composable
private fun cardShimmerAxis(
    cardWidthPx: Float,
    infiniteTransition: InfiniteTransition,
    gradientWidth: Float,
    tweenAnim: DurationBasedAnimationSpec<Float>
): State<Float> {
    return infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (cardWidthPx + gradientWidth),
        animationSpec = infiniteRepeatable(
            animation = tweenAnim,
            repeatMode = RepeatMode.Restart
        )
    )
}

private fun tweenParameters(
    shimmerDuration: Int,
    shimmerDelayDuration: Int
): DurationBasedAnimationSpec<Float> {
    return tween(
        durationMillis = shimmerDuration,
        easing = LinearEasing,
        delayMillis = shimmerDelayDuration
    )
}
