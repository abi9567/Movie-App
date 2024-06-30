package com.example.movieapp.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.movieapp.ui.screens.actorDetailScreen.ActorDetailScreen
import com.example.movieapp.ui.screens.actorDetailScreen.ActorViewModel
import com.example.movieapp.ui.screens.detailScreen.DetailScreen
import com.example.movieapp.ui.screens.detailScreen.DetailViewModel
import com.example.movieapp.ui.screens.homeScreen.HomeScreen
import com.example.movieapp.ui.screens.homeScreen.HomeViewModel
import com.example.movieapp.ui.screens.splashScreen.SplashScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavigationGraph(navController : NavHostController) {
    Scaffold(containerColor = MaterialTheme.colorScheme.primary) { paddingValues ->
        SharedTransitionLayout {
                NavHost(navController = navController,
                    modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
                    startDestination = Screen.SplashScreen.route) {

                    composable(route = Screen.SplashScreen.route) {
                        SplashScreen(navController = navController,
                            sharedTransitionScope = this@SharedTransitionLayout,
                            animatedVisibilityScope = this
                        )
                    }

                    composable(route = Screen.HomeScreen.route) {
                        val viewModel = hiltViewModel<HomeViewModel>()
                        HomeScreen(navController = navController,
                            sharedTransitionScope = this@SharedTransitionLayout,
                            animatedVisibilityScope = this,
                            viewModel = viewModel,
                            paddingValues = paddingValues)
                    }

                    composable(route = Screen.DetailScreen.route) {
                        val viewModel = hiltViewModel<DetailViewModel>()
                        DetailScreen(navController = navController,
                            viewModel = viewModel)
                    }

                    composable(route = Screen.ActorDetailScreen.route) {
                        val viewModel = hiltViewModel<ActorViewModel>()
                        ActorDetailScreen(navController = navController, viewModel = viewModel)
                    }
                }
        }
    }
}