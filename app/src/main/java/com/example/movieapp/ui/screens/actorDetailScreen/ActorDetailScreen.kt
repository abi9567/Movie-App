package com.example.movieapp.ui.screens.actorDetailScreen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieapp.BuildConfig
import com.example.movieapp.R
import com.example.movieapp.data.response.Resource
import com.example.movieapp.internal.extensions.formatMovieRating
import com.example.movieapp.navigation.Screen
import com.example.movieapp.ui.common.CustomHeightSpacer
import com.example.movieapp.ui.common.CustomLoading
import com.example.movieapp.ui.common.CustomScaffold
import com.example.movieapp.ui.common.CustomTopBar
import com.example.movieapp.ui.common.LargeImageView
import com.example.movieapp.ui.screens.detailScreen.DetailScreenTitleDescriptionView
import com.example.movieapp.ui.screens.detailScreen.RecommendationSingleView
import com.example.movieapp.ui.theme.SecondaryLightColor

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ActorDetailScreen(
    navController: NavController,
    viewModel: ActorViewModel
) {
    val scrollState = rememberScrollState()
    val actorDetailState by viewModel.actorDetail.collectAsState(initial = null)
    val actorMovies by viewModel.actorMovies.collectAsState(initial = null)
    val isLargeImageVisible by viewModel.isLargeImageVisible.collectAsState(initial = false)

    BackHandler(enabled = isLargeImageVisible) {
        viewModel.setImageVisibility()
    }

    CustomScaffold(
        topBar = {
            CustomTopBar(text = actorDetailState?.data?.name
                ?: stringResource(id = R.string.actor_detail),
                onNavigationButtonClick = {
                    if (isLargeImageVisible) {
                        viewModel.setImageVisibility()
                        return@CustomTopBar
                    }
                    navController.navigateUp()
                })
        }
    ) { paddingValues ->

        when (actorDetailState) {
            is Resource.Loading -> CustomLoading()

            is Resource.Success -> {
                val actorDetail = actorDetailState?.data

                SharedTransitionLayout {
                    AnimatedContent(targetState = isLargeImageVisible, label = "") { targetState ->
                        if (!targetState) {
                            Column(
                                modifier = Modifier
                                    .padding(paddingValues = paddingValues)
                                    .verticalScroll(state = scrollState)
                                    .fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = dimensionResource(id = R.dimen.margin16))
                                ) {

                                    CustomHeightSpacer(dimenResId = R.dimen.margin16)

                                    with(this@SharedTransitionLayout) {
                                        AsyncImage(
                                            model = ImageRequest.Builder(context = LocalContext.current)
                                                .data(BuildConfig.IMAGE_URL + actorDetail?.profileImage)
                                                .crossfade(enable = true)
                                                .build(),
                                            placeholder = painterResource(R.drawable.ic_place_holder),
                                            error = painterResource(R.drawable.ic_place_holder),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .sharedElement(
                                                    state = rememberSharedContentState(key = "actor"),
                                                    animatedVisibilityScope = this@AnimatedContent
                                                )
                                                .align(alignment = Alignment.CenterHorizontally)
                                                .clip(shape = CircleShape)
                                                .clickable(onClick = viewModel::setImageVisibility)
                                                .border(
                                                    width = 2.dp,
                                                    color = SecondaryLightColor.copy(alpha = 0.5F),
                                                    shape = CircleShape
                                                )
                                                .size(size = 80.dp),
                                            contentScale = ContentScale.Crop
                                        )
                                    }

                                    CustomHeightSpacer(dimenResId = R.dimen.margin8)

                                    Text(
                                        text = actorDetail?.name ?: "",
                                        style = MaterialTheme.typography.titleLarge,
                                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                                    )

                                    CustomHeightSpacer(dimenResId = R.dimen.margin4)

                                    Text(
                                        text = stringResource(id = R.string.popularity)
                                                + " :  " + actorDetail?.popularity.formatMovieRating(),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = SecondaryLightColor,
                                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                                    )

                                    CustomHeightSpacer(dimenResId = R.dimen.margin16)

                                    Text(
                                        text = actorDetail?.biography ?: "",
                                        style = MaterialTheme.typography.bodyLarge,
                                        textAlign = TextAlign.Justify,
                                        color = SecondaryLightColor,
                                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                                    )

                                    CustomHeightSpacer(dimenResId = R.dimen.margin16)

                                    if (!actorDetail?.otherNames.isNullOrEmpty()) {
                                        DetailScreenTitleDescriptionView(
                                            title = stringResource(id = R.string.other_names),
                                            description = actorDetail?.otherNames?.joinToString { "$it" }
                                                ?: ""
                                        )
                                        CustomHeightSpacer(dimenResId = R.dimen.margin8)
                                    }

                                    actorDetail?.knownFor?.let {
                                        DetailScreenTitleDescriptionView(
                                            title = stringResource(id = R.string.known_for),
                                            description = it
                                        )
                                        CustomHeightSpacer(dimenResId = R.dimen.margin8)
                                    }

                                    actorDetail?.dateOfBirth?.let {
                                        DetailScreenTitleDescriptionView(
                                            title = stringResource(id = R.string.date_of_birth),
                                            description = it
                                        )
                                        CustomHeightSpacer(dimenResId = R.dimen.margin8)
                                    }

                                    actorDetail?.placeOfBirth?.let {
                                        DetailScreenTitleDescriptionView(
                                            title = stringResource(id = R.string.place_of_birth),
                                            description = it
                                        )
                                        CustomHeightSpacer(dimenResId = R.dimen.margin8)
                                    }

                                    actorDetail?.deathDay?.let {
                                        DetailScreenTitleDescriptionView(
                                            title = stringResource(id = R.string.death_on),
                                            description = it
                                        )
                                        CustomHeightSpacer(dimenResId = R.dimen.margin8)
                                    }
                                }


                                CustomHeightSpacer(dimenResId = R.dimen.margin16)

                                if (!(actorMovies?.data?.results.isNullOrEmpty())) {

                                    CustomHeightSpacer(dimenResId = R.dimen.margin24)

                                    Text(
                                        text = stringResource(id = R.string.popular_movies),
                                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.margin16)),
                                        style = MaterialTheme.typography.headlineMedium
                                    )

                                    CustomHeightSpacer(dimenResId = R.dimen.margin16)

                                    LazyRow(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        contentPadding = PaddingValues(
                                            horizontal = dimensionResource(
                                                id = R.dimen.margin16
                                            )
                                        ),
                                        horizontalArrangement = Arrangement
                                            .spacedBy(space = dimensionResource(id = R.dimen.margin16))
                                    ) {
                                        items(
                                            items = actorMovies?.data?.results ?: emptyList()
                                        ) { item ->
                                            RecommendationSingleView(item = item,
                                                onClick = {
                                                    navController.navigate(
                                                        route = Screen.DetailScreen.detailScreenArgs(
                                                            movieId = item.id
                                                        )
                                                    )

                                                })
                                        }
                                    }
                                    CustomHeightSpacer(dimenResId = R.dimen.margin24)
                                }
                            }
                        } else {
                            with(receiver = this@SharedTransitionLayout) {
                                LargeImageView(
                                    imageUrl = BuildConfig.IMAGE_URL + actorDetail?.profileImage,
                                    modifier = Modifier.sharedElement(
                                        state = rememberSharedContentState(key = "actor"),
                                        animatedVisibilityScope = this@AnimatedContent
                                    ),
                                    onImageClick = viewModel::setImageVisibility
                                )
                            }
                        }
                    }
                }
            }

            else -> {

            }
        }
    }
}