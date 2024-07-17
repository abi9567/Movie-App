package com.abi.movieapp.ui.common

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.abi.movieapp.R
import com.abi.movieapp.ui.theme.SecondaryLightColor

@Composable
fun CustomLoading() {

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) { CircularLoadingView() }
}

@Composable
fun CircularLoadingView(isPaginationLoading : Boolean = false) {
    val infiniteTransition = rememberInfiniteTransition(label = "Infinite transition")
    val rotationWithAnimated by infiniteTransition.animateFloat(
        label = "Rotate Float with Animated",
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            repeatMode = RepeatMode.Restart,
            animation = tween(durationMillis = 1300,
                easing = LinearEasing)
        )
    )

    Box(modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center)
    {
        Canvas(modifier = Modifier
            .align(alignment = Alignment.Center)
            .graphicsLayer { rotationZ = rotationWithAnimated }
            .size(size = if (isPaginationLoading) 25.dp else 50.dp)) {

            drawArc(
                color = SecondaryLightColor,
                size = Size(width = size.width, height = size.height),
                style = Stroke(width = 15F, cap = StrokeCap.Round),
                startAngle = 0F,
                sweepAngle = 90F,
                useCenter = false
            )

            drawArc(
                color = SecondaryLightColor,
                style = Stroke(width = 15F, cap = StrokeCap.Round),
                size = Size(width = size.width, height = size.height),
                startAngle = 180F,
                sweepAngle = 90F,
                useCenter = false
            )
        }
    }
}

@Composable
fun PaginationErrorView(
    onRetry : () -> Unit
) {

    Box(modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(modifier = Modifier
            .background(color = SecondaryLightColor, shape = MaterialTheme.shapes.medium)
            .padding(vertical = dimensionResource(id = R.dimen.margin8))
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Text(text = "Some Error Occurred",
                style = MaterialTheme.typography.titleSmall)

            CustomWidthSpacer(dimenResId = R.dimen.margin16)

            CustomGradientButton(text = "Retry",
                textStyle = MaterialTheme.typography.titleSmall,
                onClick = onRetry)
        }
    }
}