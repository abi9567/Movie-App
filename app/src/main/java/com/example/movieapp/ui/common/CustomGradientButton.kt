package com.example.movieapp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.movieapp.R
import com.example.movieapp.ui.theme.ButtonGradientBrush
import com.example.movieapp.ui.theme.GradientColorSecond
import com.example.movieapp.ui.theme.PrimaryGradientColor

@Composable
fun CustomGradientButton(
    modifier: Modifier = Modifier,
    icon : @Composable (() -> Unit)? = null,
    text : String,
    textStyle : TextStyle = MaterialTheme.typography.headlineMedium,
    onClick : () -> Unit
) {
    Row(modifier = modifier
        .shadow(elevation = 12.dp,
            clip = true,
            shape = MaterialTheme.shapes.small,
            spotColor = PrimaryGradientColor)
        .clip(shape = MaterialTheme.shapes.small)
        .background(brush = ButtonGradientBrush)
        .clickable { onClick() }
        .padding(
            horizontal = dimensionResource(id = R.dimen.margin8),
            vertical = dimensionResource(id = R.dimen.margin4)
        ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            it()
            CustomWidthSpacer(dimenResId = R.dimen.margin8)
        }

        Text(text = text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = textStyle)
    }
}