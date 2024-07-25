package com.abi.movieapp.ui.common

import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.abi.movieapp.R
import com.abi.movieapp.ui.theme.AppBackgroundColorGradient
import com.abi.movieapp.ui.theme.ButtonGradientBrush

@Composable
fun CustomRadioButton(isSelected : Boolean,
                      unSelectedBrush : Brush = AppBackgroundColorGradient,
                      size : Dp = 24.dp,
                      selectedBrush : Brush = ButtonGradientBrush
) {
    Box(modifier = Modifier
        .clip(shape = CircleShape)
        .background(brush = if (isSelected) selectedBrush else unSelectedBrush)
        .size(size = size),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.animation.AnimatedVisibility (visible = isSelected,
            enter = slideInVertically { -it },
            exit = fadeOut()
        ) {
            Box(modifier = Modifier
                .clip(shape = CircleShape)
                .background(color = Color.White)
                .size(size = size / 2))
        }
    }
}