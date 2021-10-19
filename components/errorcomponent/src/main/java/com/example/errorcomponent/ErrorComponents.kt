package com.example.errorcomponent

import androidx.annotation.StringRes
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Replay
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex


@Composable
fun ErrorAnimation(modifier: Modifier = Modifier) {

    val infiniteTransition = rememberInfiniteTransition()


    val color by infiniteTransition.animateColor(
        initialValue = MaterialTheme.colors.primary,
        targetValue = MaterialTheme.colors.secondary,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val scale by infiniteTransition.animateFloat(
        initialValue = 8f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1500
                13f at 400
                15f at 800 / 2
            },
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(
        modifier = modifier
    ) {
        val (width, height) = size

        val rightLineOffsetEnd = Offset(width, height)
        val startingPoint = Offset(width / 2, 0f)
        drawLine(
            color = color,
            start = startingPoint,
            end = rightLineOffsetEnd,
            strokeWidth = scale,
            cap = StrokeCap.Round
        )

        val leftLineHeightEnd = Offset(0f, height)
        drawLine(
            color = color,
            start = startingPoint,
            end = leftLineHeightEnd,
            strokeWidth = scale,
            cap = StrokeCap.Round
        )

        val startMiddleOffset = Offset(startingPoint.x / 2, height / 2)
        drawLine(
            color = color,
            start = startMiddleOffset,
            end = Offset((startingPoint.x / 2) * 3, startMiddleOffset.y),
            strokeWidth = scale,
            cap = StrokeCap.Round
        )
    }
}


@Composable
fun ErrorWithRetry(
    @StringRes text: Int,
    onRetryClicked: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize().zIndex(1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ErrorAnimation(Modifier.size(width = 250.dp, 250.dp))
        Text(
            text = stringResource(id = text),
            textAlign = TextAlign.Center, fontSize = 24.sp,
            modifier = Modifier.padding(24.dp)
        )
        RetryOption(onRetryClicked)
    }
}

@Composable
fun ErrorMessage(
    @StringRes text: Int,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ErrorAnimation(Modifier.size(width = 250.dp, 250.dp))
        Text(
            text = stringResource(id = text),
            textAlign = TextAlign.Center, fontSize = 24.sp,
            modifier = Modifier.padding(24.dp)
        )
    }
}

@Composable
@Preview
fun ErrorWithRetry() {
    ErrorWithRetry(text = com.example.strings.R.string.images_no_connection)
}

@Composable
@Preview
fun RetryOption(onRetryClicked: () -> Unit = {}) {
    Column(modifier = Modifier
        .clickable {
            onRetryClicked()
        }
        .padding(top = 16.dp)
        .wrapContentSize()
        .zIndex(3f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Replay,
            contentDescription = stringResource(com.example.strings.R.string.retry),
            modifier = Modifier
                .size(50.dp)
        )
        Text(text = stringResource(id = com.example.strings.R.string.retry), modifier = Modifier.padding(8.dp))
    }
}

