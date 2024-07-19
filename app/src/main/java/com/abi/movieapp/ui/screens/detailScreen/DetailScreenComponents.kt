package com.abi.movieapp.ui.screens.detailScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
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
import com.abi.movieapp.BuildConfig
import com.abi.movieapp.R
import com.abi.movieapp.data.response.Comment
import com.abi.movieapp.data.response.Movie
import com.abi.movieapp.data.response.Person
import com.abi.movieapp.internal.extensions.formatMovieRating
import com.abi.movieapp.ui.common.CustomHeightSpacer
import com.abi.movieapp.ui.common.CustomWidthSpacer
import com.abi.movieapp.ui.theme.PrimaryColor
import com.abi.movieapp.ui.theme.SecondaryLightColor
import com.abi.movieapp.ui.theme.TextFieldBorderColor
import com.abi.movieapp.ui.theme.YellowColor
import com.abi.movieapp.utils.other.Utils

@Composable
fun DetailScreenTitleDescriptionView(
    title : String,
    description : String
) {
    Row(modifier = Modifier.fillMaxWidth()) {

        Text(text = title, style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 0.5F),
            color = SecondaryLightColor)

        CustomWidthSpacer(dimenResId = R.dimen.margin24)

        Text(text = description, style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 1F),
            color = Color.White)
    }
}

@Composable
fun RecommendationSingleView(item : Movie?,
                             onClick : () -> Unit) {
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(BuildConfig.IMAGE_URL+item?.imageUrl)
            .crossfade(enable = true)
            .build(),
        placeholder = painterResource(R.drawable.ic_place_holder),
        error = painterResource(R.drawable.ic_place_holder),
        contentDescription = null,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(size = 14.dp))
            .clickable { onClick() }
            .width(width = 120.dp)
            .aspectRatio(ratio = 0.71F),
        contentScale = ContentScale.Crop)
}

@Composable
fun CastSingleView(item : Person?,
                   onClick : () -> Unit
) {

    Column(modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(BuildConfig.IMAGE_URL + item?.profilePicture)
                .crossfade(enable = true)
                .build(),
            placeholder = painterResource(R.drawable.ic_place_holder),
            error = painterResource(R.drawable.ic_place_holder),
            contentDescription = null,
            modifier = Modifier
                .clip(shape = CircleShape)
                .clickable { onClick() }
                .border(
                    width = 2.dp,
                    color = SecondaryLightColor.copy(alpha = 0.5F),
                    shape = CircleShape
                )
                .size(size = 80.dp),
            contentScale = ContentScale.Crop)

        CustomHeightSpacer(dimenResId = R.dimen.margin4)

        Text(text = item?.name ?: "",
            maxLines = 1,
            modifier = Modifier.width(width = 80.dp),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleSmall)

        CustomHeightSpacer(dimenResId = R.dimen.margin4)

        Text(text = item?.characterName ?: item?.department ?: "",
            maxLines = 1,
            modifier = Modifier.width(width = 80.dp),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            color = SecondaryLightColor,
            style = MaterialTheme.typography.bodySmall)

    }
}

@Composable
fun ReviewSingleView(comment : Comment?, modifier : Modifier) {

    Column(modifier = modifier
        .border(width = 1.dp, color = SecondaryLightColor, shape = MaterialTheme.shapes.medium)
        .padding(all = dimensionResource(id = R.dimen.margin16)))
    {
        Row(modifier = Modifier
            .height(intrinsicSize = IntrinsicSize.Max)
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(BuildConfig.IMAGE_URL + comment?.authorDetails?.profileImage)
                    .crossfade(enable = true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_place_holder),
                error = painterResource(R.drawable.ic_place_holder),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(ratio = 1F)
                    .clip(shape = CircleShape),
                contentScale = ContentScale.Crop)
            
            CustomWidthSpacer(dimenResId = R.dimen.margin16)
            
            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 1F),
                verticalArrangement = Arrangement.Center) {

                Text(text = comment?.author ?: (comment?.authorDetails?.userName ?: ""),
                    style = MaterialTheme.typography.titleSmall)

                CustomHeightSpacer(dimenResId = R.dimen.margin4)

                Text(text = Utils.getDate(date = comment?.date) ?: "",
                    color = SecondaryLightColor,
                    style = MaterialTheme.typography.titleSmall)
            }

            Icon(imageVector = Icons.Filled.Star,
                tint = YellowColor,
                modifier = Modifier.size(size = dimensionResource(id = R.dimen.margin16)),
                contentDescription = null)

            CustomWidthSpacer(dimenResId = R.dimen.margin8)

            Text(text = comment?.authorDetails?.rating.formatMovieRating(),
                color = PrimaryColor,
                style = MaterialTheme.typography.titleSmall)
        }

        CustomHeightSpacer(dimenResId = R.dimen.margin16)

        Text(text = comment?.comment ?: "",
            modifier = Modifier.fillMaxWidth(),
            maxLines = 9,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun MovieBookingSingleRowWithTheatreName(
    onClick: () -> Unit,
    number : Int
) {

    Column(modifier = Modifier
        .height(intrinsicSize = IntrinsicSize.Max)
        .clickable { onClick() }
        .padding(vertical = dimensionResource(id = R.dimen.margin16))
        .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {

        Row(modifier = Modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "14 : 50",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = dimensionResource(id = R.dimen.margin16))
                    .weight(weight = 0.3F),
                style = MaterialTheme.typography.headlineMedium)

            VerticalDivider(modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.margin16))
                .fillMaxHeight(),
                color = TextFieldBorderColor
            )

            Text(text = "Theatre $number",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(weight = 0.7F),
                style = MaterialTheme.typography.titleSmall)
        }

        CustomHeightSpacer(dimenResId = R.dimen.margin16)
    }
}