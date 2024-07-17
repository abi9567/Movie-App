package com.abi.movieapp.ui.screens.detailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.abi.movieapp.R
import com.abi.movieapp.ui.common.CustomHeightSpacer
import com.abi.movieapp.ui.common.CustomSwitch
import com.abi.movieapp.ui.theme.SecondaryLightColor
import com.abi.movieapp.ui.theme.SwitchBackgroundColor
import com.abi.movieapp.ui.theme.TextFieldBorderColor

@Composable
fun SessionsView(
    viewModel : DetailViewModel,
    onClick : () -> Unit
) {

    val isChecked by viewModel.isChecked.collectAsState(initial = false)

    Column(modifier = Modifier.fillMaxWidth()) {

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.margin24)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 1F),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_calendar),
                    modifier = Modifier.height(height = 24.dp),
                    tint = Color.Unspecified,
                    contentDescription = null)
                CustomHeightSpacer(dimenResId = R.dimen.margin8)
                Text(text = "Apr 18", style = MaterialTheme.typography.titleSmall)
            }

            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 1F),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_sort),
                    modifier = Modifier.height(height = 24.dp),
                    tint = Color.Unspecified,
                    contentDescription = null)
                CustomHeightSpacer(dimenResId = R.dimen.margin8)
                Text(text = stringResource(id = R.string.time), style = MaterialTheme.typography.titleSmall)
            }

            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 1F),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomSwitch(isChecked = isChecked, onCheckedChange = viewModel::setSwitchBoxValue)
                CustomHeightSpacer(dimenResId = R.dimen.margin8)
                Text(text = stringResource(id = R.string.by_cinema), style = MaterialTheme.typography.titleSmall)
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
            Text(text = stringResource(id = R.string.time),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(weight = 0.3F),
                color = SecondaryLightColor,
                style = MaterialTheme.typography.bodyMedium)

            Text(text = stringResource(id = R.string.theatre_name),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = dimensionResource(id = R.dimen.margin16))
                    .weight(weight = 0.7F),
                color = SecondaryLightColor,
                style = MaterialTheme.typography.bodyMedium)

        }

        LazyColumn(modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = dimensionResource(id = R.dimen.margin16))
        ) {
            items(count = 10) { position ->
                MovieBookingSingleRowWithTheatreName(
                    onClick = onClick
                )
                if (position != 9) {
                    HorizontalDivider(modifier = Modifier.fillMaxWidth(),
                        color = TextFieldBorderColor)
                }
            }
        }
    }
}