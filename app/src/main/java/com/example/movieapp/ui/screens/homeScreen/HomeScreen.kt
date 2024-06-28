package com.example.movieapp.ui.screens.homeScreen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movieapp.R
import com.example.movieapp.navigation.Screen
import com.example.movieapp.ui.common.CircularLoadingView
import com.example.movieapp.ui.common.CustomGradientButton
import com.example.movieapp.ui.common.CustomPagerComposeView
import com.example.movieapp.ui.common.CustomScaffold
import com.example.movieapp.ui.common.CustomWidthSpacer
import com.example.movieapp.ui.common.PaginationErrorView

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(navController : NavController,
               viewModel: HomeViewModel,
               sharedTransitionScope: SharedTransitionScope,
               animatedVisibilityScope : AnimatedVisibilityScope,
               paddingValues : PaddingValues) {

    val movieList = viewModel.filmList.collectAsLazyPagingItems()

    CustomScaffold(topBar = {

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.margin16))
                .padding(bottom = dimensionResource(id = R.dimen.margin4))
                .statusBarsPadding()
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {

                with(sharedTransitionScope) {
                    Icon(painter = painterResource(id = R.drawable.ic_logo),
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .offset(y = 3.dp, x = (-13).dp)
                            .size(size = 54.dp)
                            .sharedElement(
                                state = rememberSharedContentState(key = "icon"),
                                animatedVisibilityScope = animatedVisibilityScope
                            ),
                        contentDescription = null)
                }

                CustomWidthSpacer(dimenResId = R.dimen.margin16)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = R.drawable.ic_location),
                        tint = Color.Unspecified,
                        contentDescription = null)

                    CustomWidthSpacer(dimenResId = R.dimen.margin8)

                    Text(text = "Calicut",
                        style = MaterialTheme.typography.titleSmall)
                }

                CustomWidthSpacer(dimenResId = R.dimen.margin16)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = R.drawable.ic_language),
                        tint = Color.Unspecified,
                        contentDescription = null)

                    CustomWidthSpacer(dimenResId = R.dimen.margin8)

                    Text(text = "Eng",
                        style = MaterialTheme.typography.titleSmall)
                }

                CustomWidthSpacer(dimenResId = R.dimen.margin16)

                CustomGradientButton(text = "Log in",
                    textStyle = MaterialTheme.typography.titleSmall,
                    onClick = { })

            }
    }) { topBarPadding ->
        CustomPagerComposeView(pagingItem = movieList,
            emptyItemView = { /*TODO*/ },
            layoutView = {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(count = 2),
                    horizontalArrangement = Arrangement.spacedBy(space = dimensionResource(id = R.dimen.margin16)),
                    verticalArrangement = Arrangement.spacedBy(space = dimensionResource(id = R.dimen.margin16)),
                    contentPadding = PaddingValues(all = dimensionResource(id = R.dimen.margin16)),
                    modifier = Modifier
                        .padding(paddingValues = topBarPadding)
                        .fillMaxSize()
                ) {
                    item(span = { GridItemSpan(currentLineSpan = 2) }) {
                        MovieListTitle(text = "Now in cinemas")
                    }

                    items(count = movieList.itemCount) { position ->
                        val item = movieList.get(index = position)
                        MovieListSingleItem(
                            item = item,
                            onClick = {
                                navController.navigate(route = Screen
                                    .DetailScreen.detailScreenArgs(movieId = item?.id))
                            }
                        )
                    }

                    if(movieList.loadState.append is LoadState.Loading) {
                        item(span = { GridItemSpan(currentLineSpan = 2) }) { CircularLoadingView(isPaginationLoading = true) }
                    }

                    if(movieList.loadState.append is LoadState.Error) {
                        item(span = { GridItemSpan(currentLineSpan = 2) }) { PaginationErrorView(onRetry = { movieList.retry() }) }
                    }
                }
            }
        )
    }
}