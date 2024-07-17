package com.abi.movieapp.ui.screens.splashScreen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.abi.movieapp.R
import com.abi.movieapp.navigation.Screen
import kotlinx.coroutines.delay

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SplashScreen(navController : NavController,
                 sharedTransitionScope: SharedTransitionScope,
                 animatedVisibilityScope : AnimatedVisibilityScope
) {

    LaunchedEffect(key1 = Unit) {
        delay(timeMillis = 2000)
        navController.navigate(route = Screen.HomeScreen.route)
    }

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
            Image(painter = painterResource(id = R.drawable.splash_background),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                contentDescription = null)

        with(sharedTransitionScope) {
            Image(painter = painterResource(id = R.drawable.ic_logo),
                modifier = Modifier
                    .sharedElement(state = rememberSharedContentState(key = "icon"),
                        animatedVisibilityScope = animatedVisibilityScope)
                    .aspectRatio(ratio = 2.1F),
                contentDescription = null)
        }
    }
}