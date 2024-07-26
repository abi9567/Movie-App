package com.abi.movieapp.ui.screens.detailScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.abi.movieapp.R
import com.abi.movieapp.data.response.BookingDetail
import com.abi.movieapp.ui.common.CustomHeightSpacer
import com.abi.movieapp.ui.screens.seatSelectionScreen.SingleDateView
import com.abi.movieapp.ui.theme.AppBackgroundColor
import com.abi.movieapp.ui.theme.PrimaryGradientColor
import com.abi.movieapp.ui.theme.SecondaryLightColor
import com.abi.movieapp.ui.theme.SwitchBackgroundColor
import com.abi.movieapp.ui.theme.YellowColor

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun BookingView(
    viewModel : DetailViewModel,
    onClick : (BookingDetail?) -> Unit,
    selectedDate : Pair<String, String>?
) {
    val movieShowTime by viewModel.movieShowTime.collectAsState(initial = null)
    val nextDaysList = viewModel.nextDaysList
    val isDateVisible by viewModel.isDateVisible.collectAsState(initial = false)
    val isSortedByDistance by viewModel.isSortedByDistance.collectAsState(initial = false)

    Column(modifier = Modifier.fillMaxWidth()) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.margin24)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 1F)
                .clickable(indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = { viewModel.setDateVisibility() }),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_calendar),
                    modifier = Modifier.height(height = 24.dp),
                    tint = Color.Unspecified,
                    contentDescription = null)
                CustomHeightSpacer(dimenResId = R.dimen.margin8)
                Text(text = "${selectedDate?.first}  ${selectedDate?.second}",
                    style = MaterialTheme.typography.titleSmall)
            }

            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 1F)
                .clickable(indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = { viewModel.setSortedList() }),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_sort),
                    modifier = Modifier.height(height = 24.dp),
                    tint = if (isSortedByDistance) PrimaryGradientColor else Color.Unspecified,
                    contentDescription = null)
                CustomHeightSpacer(dimenResId = R.dimen.margin8)
                Text(text = stringResource(id = R.string.distance), style = MaterialTheme.typography.titleSmall)
            }
        }

        AnimatedVisibility (visible = isDateVisible,
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyRow(modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.margin16))
                .animateContentSize(),
                contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.margin16)),
                horizontalArrangement = Arrangement
                    .spacedBy(space = dimensionResource(id = R.dimen.margin16))
            ) {
                items(nextDaysList) { date ->
                    SingleDateView(item = date,
                        onClick = {
                            viewModel.apply {
                                setDate(date = date)
                                setDateVisibility()
                        } },
                        isSelected = selectedDate == date)
                }
            }
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.margin16))
            .background(color = SwitchBackgroundColor)
            .padding(
                vertical = dimensionResource(id = R.dimen.margin8),
                horizontal = dimensionResource(id = R.dimen.margin16)
            ))
        {
            Text(text = stringResource(id = R.string.theatre_and_show_time),
                modifier = Modifier.fillMaxWidth(),
                color = SecondaryLightColor,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium)

        }

        LazyColumn(modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(bottom = dimensionResource(id = R.dimen.margin16),
                start = dimensionResource(id = R.dimen.margin16), end = dimensionResource(id = R.dimen.margin16))
        ) {
            movieShowTime?.forEach { item ->
                stickyHeader {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .background(color = AppBackgroundColor)
                        .padding(vertical = dimensionResource(id = R.dimen.margin16)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = item.name ?: "",
                            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.margin8)),
                            style = MaterialTheme.typography.titleLarge.copy(color = YellowColor))

                        Text(text = "(${ item.distance } km)",
                            textAlign = TextAlign.End,
                            style = MaterialTheme.typography.bodySmall.copy(color = SecondaryLightColor))

                    }
                }

                item {
                    FlowRow(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(space = dimensionResource(id = R.dimen.margin16)),
                        verticalArrangement = Arrangement.spacedBy(space = dimensionResource(id = R.dimen.margin16))
                    ) {
                        repeat(times = item.showTime?.size ?: 0) { position ->
                            val showTime = item.showTime?.get(index = position)
                            MovieBookingSingleRowWithTheatreName(
                                showTime = showTime,
                                onClick = {
                                val bookingDetail = viewModel.getBookedDetails(
                                    time = showTime, theatre = item)
                                onClick(bookingDetail)
                            })
                        }
                    }
                }
            }
        }
    }
}