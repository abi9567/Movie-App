package com.example.movieapp.ui.screens.detailScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieapp.R
import com.example.movieapp.data.response.Resource
import com.example.movieapp.internal.enums.DetailScreenTabs
import com.example.movieapp.navigation.Screen
import com.example.movieapp.ui.common.CustomLoading
import com.example.movieapp.ui.common.CustomScaffold
import com.example.movieapp.ui.common.CustomTopBar
import com.example.movieapp.ui.theme.PrimaryGradientColor
import com.example.movieapp.ui.theme.SecondaryLightColor
import com.example.movieapp.utils.other.Utils

@Composable
fun DetailScreen(
    navController : NavController,
    viewModel : DetailViewModel
) {
    val context = LocalContext.current
    val selectedTab by viewModel.selectedTab.collectAsState(initial = DetailScreenTabs.Booking)
    val movieDetail by viewModel.movieDetail.collectAsState(initial = null)
    val recommendations by viewModel.recommendations.collectAsState(initial = null)
    val comments by viewModel.reviews.collectAsState(initial = null)
    val cast by viewModel.credits.collectAsState(initial = null)
    val youtubeUrls by viewModel.youtubeUrls.collectAsState(initial = null)

    BackHandler(enabled = selectedTab == DetailScreenTabs.Booking) {
        viewModel.setTab(tab = DetailScreenTabs.About)
    }

    CustomScaffold(
        topBar = {
            CustomTopBar(text = movieDetail?.data?.title ?: "Movie detail",
                onNavigationButtonClick = { navController.navigateUp() }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues = paddingValues)
            .fillMaxWidth())
        {
            when(movieDetail) {
                is Resource.Loading -> { CustomLoading() }
//                is Resource.Success -> {
                else -> {
                    TabRow(selectedTabIndex = selectedTab.ordinal,
                        indicator = { tabPositions ->
                            TabRowDefaults.SecondaryIndicator(
                                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab.ordinal]),
                                color = PrimaryGradientColor,
                                height = 2.dp)
                        },
                        divider = { HorizontalDivider(thickness = 2.dp, color = Color(0xFF26334E)) },
                        containerColor = Color.Transparent
                    ) {
                        viewModel.tabList.forEach { tab ->
                            Tab(selected = tab == selectedTab,
                                text = {
                                    Text(text = stringResource(id = if(tab == DetailScreenTabs.About)
                                        R.string.about else R.string.booking),
                                        style = MaterialTheme.typography.titleLarge,
                                        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.margin8)),
                                        color = if(tab == selectedTab) PrimaryGradientColor else SecondaryLightColor
                                    )
                                },
                                onClick = { viewModel.setTab(tab = tab) })
                        }
                    }
                    when(selectedTab) {
                        DetailScreenTabs.About -> {
                            AboutScreenView(movieDetail = movieDetail?.data,
                                onWatchVideoClick = {
                                    Utils.openYoutubeLink(youtubeID = youtubeUrls?.data?.results?.getOrNull(index = 0)?.youtubeKey,
                                        context = context
                                    )
                                },
                                cast = cast?.data?.cast,
                                crew = cast?.data?.crew,
                                comments = comments?.data?.results,
                                recommendation = recommendations?.data?.results,
                                onRecommendedVideoClick = { movieId ->
                                    navController.navigate(route = Screen.DetailScreen.detailScreenArgs(movieId = movieId))
                                },
                                onBookTicketClick = { viewModel.setTab(tab = DetailScreenTabs.Booking) },
                                onCastDetailClick = { id ->
                                    navController.navigate(route = Screen.ActorDetailScreen.actorDetailScreen(actorId = id))
                                }
                            )
                        }

                        else -> { SessionsView(viewModel = viewModel) }
                    }
                }
//                else -> { }
            }
        }
    }
}