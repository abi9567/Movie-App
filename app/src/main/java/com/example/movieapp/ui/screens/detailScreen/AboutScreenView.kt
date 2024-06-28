package com.example.movieapp.ui.screens.detailScreen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieapp.BuildConfig
import com.example.movieapp.R
import com.example.movieapp.data.response.Comment
import com.example.movieapp.data.response.MovieDetail
import com.example.movieapp.data.response.NowPlaying
import com.example.movieapp.data.response.Person
import com.example.movieapp.internal.extensions.calculateDurationTime
import com.example.movieapp.internal.extensions.formatMovieRating
import com.example.movieapp.ui.common.CustomHeightSpacer
import com.example.movieapp.ui.common.CustomWidthSpacer
import com.example.movieapp.ui.theme.RatingGradientBrush
import com.example.movieapp.ui.theme.SecondaryLightColor
import kotlinx.coroutines.delay

@Composable
fun AboutScreenView(movieDetail : MovieDetail?,
                    recommendation : List<NowPlaying>?,
                    comments : List<Comment>?,
                    cast : List<Person>?,
                    crew : List<Person>?,
                    onWatchVideoClick : () -> Unit,
                    onRecommendedVideoClick : (Int?) -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var totalVoteCount by remember { mutableIntStateOf(value = 0) }
    val animateTotalVoteCount by animateIntAsState(targetValue = totalVoteCount,
        animationSpec = tween(easing = LinearEasing),
        label = "Animate Count"
    )

    LaunchedEffect(key1 = Unit) {
        delay(timeMillis = 200)
        totalVoteCount = movieDetail?.totalVotes ?: 0
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(state = scrollState)
        .fillMaxWidth()) {

        Box(modifier = Modifier) {
            AsyncImage(
                model = ImageRequest.Builder(context = context)
                    .data(BuildConfig.IMAGE_URL+movieDetail?.backgroundImage)
                    .build(),
                placeholder = painterResource(R.drawable.ic_place_holder),
                error = painterResource(R.drawable.ic_place_holder),
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(ratio = 2F)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop)

            Icon(painter = painterResource(id = R.drawable.ic_watch),
                tint = Color.Unspecified,
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .clickable { onWatchVideoClick() }
                    .align(alignment = Alignment.Center),
                contentDescription = null)
        }

        Row(modifier = Modifier
            .background(brush = RatingGradientBrush)
            .height(intrinsicSize = IntrinsicSize.Max)
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier
                .weight(weight = 1F)
                .padding(all = dimensionResource(id = R.dimen.margin16))
                .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(space = dimensionResource(id = R.dimen.margin4)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = movieDetail?.rating.formatMovieRating(),
                        style = MaterialTheme.typography.displaySmall)

                    CustomWidthSpacer(dimenResId = R.dimen.margin4)

                    Icon(imageVector = Icons.Filled.Star,
                        tint = SecondaryLightColor,
                        contentDescription = null)
                }

                Text(text = "IMDB",
                    color = SecondaryLightColor,
                    style = MaterialTheme.typography.bodyLarge)
            }

            VerticalDivider(color = SecondaryLightColor.copy(alpha = 0.1F),
                modifier = Modifier
                    .fillMaxHeight()
                    .width(width = 1.dp),
                thickness = 1.dp)

            Column(modifier = Modifier
                .weight(weight = 1F)
                .padding(all = dimensionResource(id = R.dimen.margin16))
                .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(space = dimensionResource(id = R.dimen.margin4)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "$animateTotalVoteCount",
                    style = MaterialTheme.typography.displaySmall)

                Text(text = "Total votes",
                    color = SecondaryLightColor,
                    style = MaterialTheme.typography.bodyLarge)
            }
        }

        Column(modifier = Modifier
            .padding(top = dimensionResource(id = R.dimen.margin16))
            .padding(horizontal = dimensionResource(id = R.dimen.margin16))) {

            Text(
                text = movieDetail?.description ?: "",
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.bodyLarge
            )

            CustomHeightSpacer(dimenResId = R.dimen.margin16)

            DetailScreenTitleDescriptionView(
                title = "Certificate",
                description = if (movieDetail?.adult == true) "18+" else "10+"
            )
            CustomHeightSpacer(dimenResId = R.dimen.margin8)

            DetailScreenTitleDescriptionView(
                title = "Language",
                description = movieDetail?.availableLanguages?.get(index = 0)?.name ?: "English"
            )
            CustomHeightSpacer(dimenResId = R.dimen.margin8)

            DetailScreenTitleDescriptionView(
                title = "Runtime",
                description = movieDetail?.durationInMinutes.calculateDurationTime()
            )
            CustomHeightSpacer(dimenResId = R.dimen.margin8)

            DetailScreenTitleDescriptionView(
                title = "Release",
                description = movieDetail?.releaseDate ?: ""
            )

            movieDetail?.genreWithComma?.let {
                CustomHeightSpacer(dimenResId = R.dimen.margin8)
                DetailScreenTitleDescriptionView(
                    title = "Genre",
                    description = it
                )
            }

        }

        if (!(cast.isNullOrEmpty())) {
            CustomHeightSpacer(dimenResId = R.dimen.margin24)

            Text(text = "Cast", maxLines = 1,
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.margin16)),
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineMedium)

            CustomHeightSpacer(dimenResId = R.dimen.margin16)

            LazyRow(modifier = Modifier
                .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.margin16)),
                horizontalArrangement = Arrangement.spacedBy(space = dimensionResource(id = R.dimen.margin24))
            ) {
                items(items = cast) { item ->
                    CastSingleView(item = item)
                }
            }
        }

        if (!(crew.isNullOrEmpty())) {

            CustomHeightSpacer(dimenResId = R.dimen.margin24)

            Text(text = "Crew", maxLines = 1,
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.margin16)),
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineMedium)

            CustomHeightSpacer(dimenResId = R.dimen.margin16)

            LazyRow(modifier = Modifier
                .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.margin16)),
                horizontalArrangement = Arrangement
                    .spacedBy(space = dimensionResource(id = R.dimen.margin24))
            ) {
                items(items = crew) { item ->
                    CastSingleView(item = item)
                }
            }
        }

            if (!recommendation.isNullOrEmpty()) {

                CustomHeightSpacer(dimenResId = R.dimen.margin24)

                Text(text = "Recommended for you", maxLines = 1,
                    modifier = Modifier.padding(start = dimensionResource(id = R.dimen.margin16)),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.headlineMedium)

                CustomHeightSpacer(dimenResId = R.dimen.margin16)

                LazyRow(modifier = Modifier
                    .fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.margin16)),
                    horizontalArrangement = Arrangement
                        .spacedBy(space = dimensionResource(id = R.dimen.margin16))
                ) {
                    items(items = recommendation) { item ->
                        RecommendationSingleView(item = item, onClick = { onRecommendedVideoClick(item.id) })
                    }
                }
            }

        if (!comments.isNullOrEmpty()) {

            CustomHeightSpacer(dimenResId = R.dimen.margin24)

            Text(text = "Top Comments", maxLines = 1,
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.margin16)),
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineMedium)

            CustomHeightSpacer(dimenResId = R.dimen.margin16)

            LazyRow(modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.margin16)),
                horizontalArrangement = Arrangement
                    .spacedBy(space = dimensionResource(id = R.dimen.margin16))
            ) {
                items(items = comments) { comment ->
                    ReviewSingleView(comment = comment,
                        modifier = Modifier
                            .height(height = 250.dp)
                            .fillParentMaxWidth(fraction = if (comments.size == 1) 1F else 0.95F)
                    )
                }
            }
        }
        CustomHeightSpacer(dimenResId = R.dimen.margin24)
    }
}