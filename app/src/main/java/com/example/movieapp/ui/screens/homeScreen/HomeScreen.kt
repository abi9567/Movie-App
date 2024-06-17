package com.example.movieapp.ui.screens.homeScreen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieapp.R
import com.example.movieapp.ui.common.CustomScaffold
import com.example.movieapp.ui.theme.ToolBarColor

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(navController : NavController,
               sharedTransitionScope: SharedTransitionScope,
               animatedVisibilityScope : AnimatedVisibilityScope,
               paddingValues : PaddingValues) {

    CustomScaffold(topBar = {
        with(sharedTransitionScope) {
            Row(modifier = Modifier
                .background(color = ToolBarColor)
                .statusBarsPadding()
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.ic_logo),
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(size = 68.dp)
                        .sharedElement(
                            state = rememberSharedContentState(key = "icon"),
                            animatedVisibilityScope = animatedVisibilityScope),
                    contentDescription = null)

            }
        }
    }) {
        Column(modifier = Modifier
            .padding(paddingValues = paddingValues)
            .fillMaxSize()) {


        }
    }


}