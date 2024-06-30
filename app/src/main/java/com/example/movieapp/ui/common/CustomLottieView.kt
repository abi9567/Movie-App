package com.example.movieapp.ui.common

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun CustomLottieView(modifier: Modifier = Modifier,
                     @RawRes lottieFile : Int) {

    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(resId = lottieFile)
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        isPlaying = true
    )

    LottieAnimation(
        composition = preloaderLottieComposition,
        progress = { preloaderProgress },
        modifier = modifier
    )
}