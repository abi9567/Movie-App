package com.example.movieapp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieapp.R
import com.example.movieapp.ui.theme.AppBackgroundColor
import com.example.movieapp.ui.theme.SecondaryLightColor

@Composable
fun LargeImageView(
    modifier : Modifier,
    imageUrl : String?,
    onImageClick : () -> Unit
) {
    Box(modifier = Modifier
        .background(color = AppBackgroundColor)
        .clickable { onImageClick() }
        .fillMaxSize(),
        contentAlignment = Alignment.Center) {

        Column(modifier = Modifier.fillMaxWidth()) {

            AsyncImage(model = ImageRequest
                .Builder(context = LocalContext.current)
                .data(data = imageUrl)
                .error(drawableResId = R.drawable.ic_place_holder)
                .build(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter,
                modifier = modifier
                    .aspectRatio(ratio = 0.85F)
                    .fillMaxWidth(),
                contentDescription = null)
        }
    }
}