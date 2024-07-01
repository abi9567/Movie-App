package com.example.movieapp.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import com.example.movieapp.ui.screens.searchScreen.SearchScreen
import com.example.movieapp.ui.screens.searchScreen.SearchViewModel
import com.example.movieapp.ui.screens.splashScreen.SplashScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavigationGraph(navController : NavHostController) {
    Scaffold(containerColor = MaterialTheme.colorScheme.primary) {
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
                            viewModel = viewModel)
                    }

                    composable(route = Screen.DetailScreen.route) {
                        val viewModel = hiltViewModel<DetailViewModel>()
                        DetailScreen(navController = navController,
                            viewModel = viewModel)
                    }

                    composable(route = Screen.ActorDetailScreen.route) {
                        val viewModel = hiltViewModel<ActorViewModel>()
                        ActorDetailScreen(navController = navController,
                            viewModel = viewModel)
                    }

                    composable(route = Screen.SearchScreen.route,
                        enterTransition = { slideInVertically { it } },
                        exitTransition = { slideOutVertically { it } }
                        ) {
                        val viewModel = hiltViewModel<SearchViewModel>()
                        SearchScreen(navController = navController, viewModel = viewModel)
                    }
                }
        }
    }
}