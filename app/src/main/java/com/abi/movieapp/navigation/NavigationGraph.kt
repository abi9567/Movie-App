package com.abi.movieapp.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abi.movieapp.ui.screens.actorDetailScreen.ActorDetailScreen
import com.abi.movieapp.ui.screens.actorDetailScreen.ActorViewModel
import com.abi.movieapp.ui.screens.detailScreen.DetailScreen
import com.abi.movieapp.ui.screens.detailScreen.DetailViewModel
import com.abi.movieapp.ui.screens.homeScreen.HomeScreen
import com.abi.movieapp.ui.screens.homeScreen.HomeViewModel
import com.abi.movieapp.ui.screens.payTicketScreen.PayTicketScreen
import com.abi.movieapp.ui.screens.payTicketScreen.PayTicketViewModel
import com.abi.movieapp.ui.screens.searchScreen.SearchScreen
import com.abi.movieapp.ui.screens.searchScreen.SearchViewModel
import com.abi.movieapp.ui.screens.seatSelectionScreen.SeatSelectionScreen
import com.abi.movieapp.ui.screens.seatSelectionScreen.SeatSelectionViewModel
import com.abi.movieapp.ui.screens.splashScreen.SplashScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavigationGraph(navController : NavHostController) {
    Scaffold(containerColor = MaterialTheme.colorScheme.background) {
        SharedTransitionLayout {
                NavHost(navController = navController,
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

                    composable(route = Screen.SeatSelectionScreen.route) {
                        val viewModel : SeatSelectionViewModel = viewModel()
                        SeatSelectionScreen(navController = navController,
                            viewModel = viewModel)
                    }

                    composable(route = Screen.PayTicketScreen.route) {
                        val viewModel = hiltViewModel<PayTicketViewModel>()
                        PayTicketScreen(navController = navController, viewModel = viewModel)
                    }
                }
        }
    }
}