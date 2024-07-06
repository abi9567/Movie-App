package com.example.movieapp.ui.screens.seatSelectionScreen

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.movieapp.R
import com.example.movieapp.data.response.Seat
import com.example.movieapp.internal.extensions.toPx
import com.example.movieapp.ui.common.CustomHeightSpacer
import com.example.movieapp.ui.common.CustomWidthSpacer
import com.example.movieapp.ui.theme.AppBackgroundColor
import com.example.movieapp.ui.theme.ButtonGradientBrush
import com.example.movieapp.ui.theme.MidnightBlue
import com.example.movieapp.ui.theme.PrimaryGradientColor
import com.example.movieapp.ui.theme.SwitchBackgroundColor
import com.example.movieapp.ui.theme.TextFieldBorderColor
import com.example.movieapp.ui.theme.YellowColor

@Composable
fun IconTextView(
    modifier : Modifier = Modifier,
    @DrawableRes icon : Int,
    text : String,
    onClick : () -> Unit
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .clip(shape = MaterialTheme.shapes.small)
        .clickable { onClick() }
        .border(
            width = 1.dp, color = TextFieldBorderColor,
            shape = MaterialTheme.shapes.small
        )
        .padding(vertical = 11.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(painter = painterResource(id = icon),
            tint = Color.Unspecified,
            modifier = Modifier.size(size = 18.dp),
            contentDescription = null)
        CustomWidthSpacer(dimenResId = R.dimen.margin8)
        Text(text = text, style = MaterialTheme.typography.titleSmall)
    }
}

@Composable
fun AvailableSeatView() {
    Row(modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier
            .clip(shape = CircleShape)
            .background(color = TextFieldBorderColor)
            .size(size = 16.dp))

        CustomWidthSpacer(dimenResId = R.dimen.margin8)

        Text(text = stringResource(id = R.string.available),
            style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun OccupiedSeatView() {
    Row(modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier
            .clip(shape = CircleShape)
            .background(color = MidnightBlue)
            .size(size = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_close),
                modifier = Modifier.size(size = 5.dp),
                tint = Color.Unspecified,
                contentDescription = null)
        }

        CustomWidthSpacer(dimenResId = R.dimen.margin8)

        Text(text = stringResource(id = R.string.occupied),
            style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun ChosenSeatView() {
    Row(modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier
            .shadow(
                elevation = 16.dp,
                shape = CircleShape,
                clip = true,
                spotColor = PrimaryGradientColor
            )
            .clip(shape = CircleShape)
            .background(brush = ButtonGradientBrush)
            .size(size = 16.dp))

        CustomWidthSpacer(dimenResId = R.dimen.margin8)

        Text(text = stringResource(id = R.string.chosen),
            style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun SeatView(alphabetList :  Map<String, List<Seat?>?>,
             onSeatItemClick : (Seat?) -> Unit,
             selectedSeats : MutableList<Seat?>?) {

    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(painter = painterResource(id = R.drawable.ic_screen),
            modifier = Modifier.fillMaxWidth(),
            tint = Color.Unspecified,
            contentDescription = null)

        CustomHeightSpacer(dimenResId = R.dimen.margin16)

        Column(modifier = Modifier.padding(all = 16.dp)) {
            repeat(times = alphabetList.size) { rowPosition ->
                val rowItemKey = alphabetList.keys.elementAt(index = rowPosition)
                val seatList = alphabetList.get(key = rowItemKey)
                Row {
                    repeat(times = seatList?.size ?: 0) { columnPosition ->
                        val seat = seatList?.get(index = columnPosition)
                        SingleSeatView(seat = seat,
                            isSeatSelected = selectedSeats?.contains(element = seat) ?: false,
                            onClick = { onSeatItemClick(seat) })
                    }
                }
            }
        }
    }
}

@Composable
fun SingleSeatView(color : Color = SwitchBackgroundColor,
                   fontColor : Color = Color.White,
                   isSeatSelected : Boolean,
                   onClick : () -> Unit,
                   seat : Seat?) {

    Box(modifier = Modifier
        .padding(all = dimensionResource(id = R.dimen.margin8))
        .size(size = 30.dp)
        .clip(shape = RoundedCornerShape(size = 10.dp))
        .clickable(enabled = (seat?.seatNumber != null && seat.available == true), onClick = onClick)
        .background(color = if (seat?.seatNumber == null) AppBackgroundColor
        else if (isSeatSelected) PrimaryGradientColor else color),
        contentAlignment = Alignment.Center
    ) {
        if (seat?.seatNumber != null && seat.available == true) {
            Text(text = seat.seatNumber,
                color = fontColor,
                style = MaterialTheme.typography.labelMedium)
            return
        }
        if (seat?.available == false) {
            Icon(painter = painterResource(id = R.drawable.ic_close),
                modifier = Modifier.size(size = 9.dp),
                tint = Color.Unspecified,
                contentDescription = null)
        }
    }
}

@Composable
fun SingleAlphabetView(color : Color = YellowColor,
                   fontColor : Color = Color.White,
                   seatNumber : String?) {

    Box(modifier = Modifier
        .padding(all = dimensionResource(id = R.dimen.margin8))
        .size(size = 30.dp)
        .clip(shape = RoundedCornerShape(size = 10.dp))
        .background(color = color),
        contentAlignment = Alignment.Center
    ) {
        Text(text = seatNumber ?: "",
            color = fontColor,
            style = MaterialTheme.typography.labelMedium)
    }
}