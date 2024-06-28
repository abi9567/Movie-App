package com.example.movieapp.ui.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieapp.BuildConfig
import com.example.movieapp.R
import com.example.movieapp.data.response.NowPlaying
import com.example.movieapp.internal.extensions.formatMovieRating
import com.example.movieapp.ui.common.CustomHeightSpacer
import com.example.movieapp.ui.common.CustomWidthSpacer
import com.example.movieapp.ui.theme.AppBackgroundColor
import com.example.movieapp.ui.theme.MidnightBlue
import com.example.movieapp.ui.theme.PrimaryColor
import com.example.movieapp.ui.theme.PrimaryGradientColor
import com.example.movieapp.ui.theme.SecondaryLightColor

@Composable
fun MovieListSingleItem(
    item : NowPlaying?,
    onClick : () -> Unit
) {
    val context = LocalContext.current
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable(interactionSource = remember { MutableInteractionSource() },
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
                    .background(color = if (item?.isLessRating == true)
                        MidnightBlue else PrimaryGradientColor,
                        shape = MaterialTheme.shapes.small)
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

        Text(text = "Action",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge.copy(color = SecondaryLightColor))
    }
}

@Composable
fun MovieListTitle(
    text : String
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

        IconButton(onClick = { /*TODO*/ }) {
            Icon(painter = painterResource(id = R.drawable.ic_search),
                tint = Color.Unspecified,
                contentDescription = null)
        }

    }

}