package com.example.movieapp.ui.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.movieapp.R
import com.example.movieapp.ui.theme.ButtonGradientBrush
import com.example.movieapp.ui.theme.SecondaryLightColor
import com.example.movieapp.ui.theme.SwitchBackgroundBrush

@Composable
fun CustomSwitch(
    isChecked : Boolean,
    onCheckedChange : () -> Unit
) {

    val animateDp by animateIntAsState(targetValue = if (isChecked) 56 else 16,
        label = "Switch Animation")

    val animateColor by animateColorAsState(targetValue =
    if (isChecked) Color.White else SecondaryLightColor,
        label = "Animate Color")

    Box(modifier = Modifier
        .clip(shape = RoundedCornerShape(size = 24.dp))
        .height(height = 24.dp)
        .background(brush = if (isChecked) ButtonGradientBrush else SwitchBackgroundBrush)
        .width(width = 40.dp)
        .clickable { onCheckedChange() },
        contentAlignment = Alignment.CenterStart
    ) {
        Box(modifier = Modifier
            .offset { IntOffset(x = animateDp, y = 0) }
            .size(size = dimensionResource(id = R.dimen.margin16))
            .clip(shape = CircleShape)
            .background(color = animateColor)
        )
    }
}