package com.abi.movieapp.ui.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.abi.movieapp.BuildConfig
import com.abi.movieapp.R
import com.abi.movieapp.data.response.Movie
import com.abi.movieapp.internal.enums.Language
import com.abi.movieapp.internal.extensions.formatMovieRating
import com.abi.movieapp.navigation.Screen
import com.abi.movieapp.ui.common.CircularLoadingView
import com.abi.movieapp.ui.common.CustomHeightSpacer
import com.abi.movieapp.ui.common.CustomLottieView
import com.abi.movieapp.ui.common.CustomPagerComposeView
import com.abi.movieapp.ui.common.CustomRadioButton
import com.abi.movieapp.ui.common.CustomWidthSpacer
import com.abi.movieapp.ui.common.PaginationErrorView
import com.abi.movieapp.ui.theme.BackgroundBrush
import com.abi.movieapp.ui.theme.MidnightBlue
import com.abi.movieapp.ui.theme.PrimaryGradientColor
import com.abi.movieapp.ui.theme.SecondaryLightColor

@Composable
fun MovieListSingleItem(
    item : Movie?,
    onClick : () -> Unit
) {
    val context = LocalContext.current
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null, onClick = onClick
        )

    ) {
        Box(modifier = Modifier
            .clip(shape = MaterialTheme.shapes.medium)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = context)
                    .data(BuildConfig.IMAGE_URL+item?.imageUrl)
                    .crossfade(enable = true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_place_holder),
                error = painterResource(R.drawable.ic_place_holder),
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(ratio = 0.71F)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop)

            Text(text = item?.rating.formatMovieRating(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(all = dimensionResource(id = R.dimen.margin8))
                    .align(alignment = Alignment.TopEnd)
                    .background(
                        color = if (item?.isLessRating == true)
                            MidnightBlue else PrimaryGradientColor,
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(
                        vertical = dimensionResource(id = R.dimen.margin4),
                        horizontal = dimensionResource(id = R.dimen.margin8)
                    )
            )

        }

        CustomHeightSpacer(dimenResId = R.dimen.margin8)
        
        Text(text = item?.title ?: "",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge)

        CustomHeightSpacer(dimenResId = R.dimen.margin4)

        Text(text = item?.genreName ?: "",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge.copy(color = SecondaryLightColor))
    }
}

@Composable
fun MovieListTitle(
    text : String,
    onSearchIconClick : () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {

        Text(text = text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.displayLarge
        )

        CustomWidthSpacer(dimenResId = R.dimen.margin0,
            modifier = Modifier
                .weight(weight = 1F)
                .fillMaxWidth())

        IconButton(onClick = onSearchIconClick) {
            Icon(painter = painterResource(id = R.drawable.ic_search),
                tint = Color.Unspecified,
                contentDescription = null)
        }
    }
}

@Composable
fun HomeScreenFilmPaginationView(
    state : LazyGridState,
    modifier : Modifier = Modifier,
    isSearchView : Boolean,
    movieList : LazyPagingItems<Movie>,
    navController : NavController
) {
    CustomPagerComposeView(pagingItem = movieList,
        emptyItemView = {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CustomLottieView(lottieFile = R.raw.popcorn_animation,
                        modifier = Modifier.aspectRatio(ratio = 2F))

                    CustomHeightSpacer(dimenResId = R.dimen.margin16)

                    Text(text = stringResource(id = R.string.no_movies),
                        style = MaterialTheme.typography.titleLarge)

                    if (isSearchView) {

                        CustomHeightSpacer(dimenResId = R.dimen.margin4)

                        Text(text = stringResource(id = R.string.search_with_another_movie),
                            color = SecondaryLightColor,
                            style = MaterialTheme.typography.titleSmall)
                    }
                }
            }
        },
        layoutView = {
            LazyVerticalGrid(
                state = state,
                columns = GridCells.Fixed(count = 2),
                horizontalArrangement = Arrangement.spacedBy(space = dimensionResource(id = R.dimen.margin16)),
                verticalArrangement = Arrangement.spacedBy(space = dimensionResource(id = R.dimen.margin16)),
                contentPadding = PaddingValues(all = dimensionResource(id = R.dimen.margin16)),
                modifier = modifier.fillMaxSize()
            ) {
                item(span = { GridItemSpan(currentLineSpan = 2) }) {
                    if (isSearchView) return@item
                    MovieListTitle(text = stringResource(id = R.string.now_in_cinemas),
                        onSearchIconClick = {
                            navController.navigate(route = Screen.SearchScreen.route) }
                    )
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
                    item(span = { GridItemSpan(currentLineSpan = 2) }) {
                        CircularLoadingView(isPaginationLoading = true) }
                }

                if(movieList.loadState.append is LoadState.Error) {
                    item(span = { GridItemSpan(currentLineSpan = 2) }) {
                        PaginationErrorView(onRetry = { movieList.retry() }) }
                }
            }
        }
    )
}

@Composable
fun LanguageSelectionBottomSheet(languages : List<Language>,
                                 selectedItem : String,
                                 onClick: (Language) -> Unit,
                                 onClose: () -> Unit
) {
    Column(modifier = Modifier
        .padding(top = dimensionResource(id = R.dimen.margin16))
        .height(height = 300.dp)
        .navigationBarsPadding()
        .fillMaxWidth()) {

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = R.string.select_language),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(weight = 1F),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleSmall)

            IconButton(onClick = onClose) {
                Icon(painter = painterResource(id = R.drawable.ic_close),
                    tint = Color.Unspecified,
                    contentDescription = null)
            }
        }

        LazyColumn(modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(bottom = dimensionResource(id = R.dimen.margin16)),
            verticalArrangement = Arrangement.spacedBy(space = dimensionResource(id = R.dimen.margin16))
        ) {
            items(items = languages) { item ->
                Row(modifier = Modifier
                    .clickable { onClick(item) }
                    .padding(
                        vertical = dimensionResource(id = R.dimen.margin8),
                        horizontal = dimensionResource(id = R.dimen.margin16)
                    )
                    .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomRadioButton(isSelected = selectedItem == item.name,
                        unSelectedBrush = BackgroundBrush,
                        size = 20.dp)

                    CustomWidthSpacer(dimenResId = R.dimen.margin16)

                    Text(text = item.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(weight = 1F),
                        style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }
}