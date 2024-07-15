package com.example.movieapp.ui.screens.seatSelectionScreen

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.rememberDismissState
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.movieapp.R
import com.example.movieapp.data.response.Seat
import com.example.movieapp.internal.extensions.fixedTextSize
import com.example.movieapp.ui.common.CustomGradientButton
import com.example.movieapp.ui.common.CustomHeightSpacer
import com.example.movieapp.ui.common.CustomWidthSpacer
import com.example.movieapp.ui.theme.AppBackgroundColor
import com.example.movieapp.ui.theme.BottomSheetBackgroundColor
import com.example.movieapp.ui.theme.ButtonGradientBrush
import com.example.movieapp.ui.theme.MidnightBlue
import com.example.movieapp.ui.theme.PrimaryColor
import com.example.movieapp.ui.theme.PrimaryGradientColor
import com.example.movieapp.ui.theme.SecondaryLightColor
import com.example.movieapp.ui.theme.SwitchBackgroundColor
import com.example.movieapp.ui.theme.TextFieldBorderColor
import com.example.movieapp.ui.theme.WhiteGradientBrush
import com.example.movieapp.ui.theme.YellowColor
import kotlinx.coroutines.withTimeout

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
             selectedSeats : MutableSet<Seat?>?) {

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
        .padding(
            vertical = dimensionResource(id = R.dimen.margin4),
            horizontal = 2.dp
        )
        .size(size = 30.dp)
        .clip(shape = RoundedCornerShape(size = 10.dp))
        .clickable(
            enabled = (seat?.seatNumber != null && seat.available == true),
            onClick = onClick
        )
        .background(
            color = if (seat?.seatNumber == null) AppBackgroundColor
            else if (isSeatSelected) PrimaryGradientColor else if (seat.available == false) MidnightBlue else color
        ),
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
        .padding(
            vertical = dimensionResource(id = R.dimen.margin4),
            horizontal = dimensionResource(id = R.dimen.margin8)
        )
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

@Composable
fun SeatNumberSelectionBottomSheetView(
    selectedSeatsCount : Int,
    onClick: (Int) -> Unit
) {
    var tempSelectedSeat by remember { mutableIntStateOf(value = selectedSeatsCount) }

    Column(modifier = Modifier
        .navigationBarsPadding()
        .padding(
            horizontal = dimensionResource(id = R.dimen.margin16),
            vertical = dimensionResource(id = R.dimen.margin24)
        )
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.how_many_seats),
            style = MaterialTheme.typography.bodyMedium)
        CustomHeightSpacer(dimenResId = R.dimen.margin24)

        Row(modifier = Modifier
            .padding(horizontal = 10.dp)
            .height(height = 30.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(times = 10) { position ->
                SeatNumberView(seatNumber = position + 1,
                    isVisible = position + 1 == tempSelectedSeat,
                    onClick = { tempSelectedSeat = position + 1 })
            }
        }
        CustomHeightSpacer(dimenResId = R.dimen.margin24)
        CustomGradientButton(text = stringResource(id = R.string.select_seats),
            modifier = Modifier.fillMaxWidth(),
            verticalPadding = dimensionResource(id = R.dimen.margin8),
            onClick = { onClick(tempSelectedSeat) })
    }
}

@Composable
fun SeatNumberView(
    seatNumber : Int,
    onClick : () -> Unit,
    isVisible : Boolean
) {
    Box(modifier = Modifier
        .clickable(interactionSource = remember { MutableInteractionSource() },
            indication = null, enabled = true, onClick = { onClick() })
        .size(size = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(visible = isVisible,
            enter = slideInVertically { -it },
            exit = fadeOut()
        ) {
            Box(modifier = Modifier
                .align(alignment = Alignment.CenterStart)
                .clip(shape = CircleShape)
                .background(color = SecondaryLightColor)
                .size(size = 24.dp))
        }

        Text(text = "$seatNumber",
            textAlign = TextAlign.Center,
            fontSize = 14.fixedTextSize(),
            style = MaterialTheme.typography.titleSmall)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BuyTicketView(
    amount : Int?,
    onSwipeEnd : () -> Unit
) {

    val swipeToDismiss = rememberDismissState(
        initialValue = DismissValue.Default,
        confirmStateChange = { dismissValue ->
            if (dismissValue == DismissValue.DismissedToEnd) {
                onSwipeEnd()
            }
            false
        }
    )

    val rotation by swipeToDismiss.offset

    Box(modifier = Modifier
        .clip(shape = CircleShape)
        .background(brush = ButtonGradientBrush)
        .padding(
            horizontal = dimensionResource(id = R.dimen.margin16),
            vertical = 13.dp
        )
        .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LaunchedEffect(key1 = Unit) {
            try {
                withTimeout(timeMillis = 1000) {
                    swipeToDismiss.animateTo(
                        targetValue = DismissValue.DismissedToEnd,
                        anim = tween(durationMillis = 7000, easing = LinearEasing)
                    )
                }
            } catch (error : Exception) {
                swipeToDismiss.reset()
            }
        }

        SwipeToDismiss(state = swipeToDismiss,
            directions = setOf(DismissDirection.StartToEnd),
            modifier = Modifier.fillMaxWidth(),
            background = {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically)
                {
                    Text(text = stringResource(id = R.string.slide_right),
                        modifier = Modifier.graphicsLayer { translationX = rotation / 2F },
                        style = MaterialTheme.typography.bodyLarge)

                    CustomWidthSpacer(dimenResId = R.dimen.margin8)

                    Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        modifier = Modifier.graphicsLayer { translationX = rotation / 2F },
                        tint = Color.White,
                        contentDescription = null)
                }
            }
        ) {
            Row(modifier = Modifier
                .height(intrinsicSize = IntrinsicSize.Max)
                .background(brush = ButtonGradientBrush, shape = CircleShape)
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(modifier = Modifier
                    .graphicsLayer { rotationZ = rotation }
                    .clip(shape = CircleShape)
                    .size(size = dimensionResource(id = R.dimen.margin40))
                    .background(color = Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_black_gradient),
                        modifier = Modifier.size(size = dimensionResource(id = R.dimen.margin24)),
                        tint = Color.Unspecified,
                        contentDescription = null)
                }

                Column(modifier = Modifier
                    .padding(start = dimensionResource(id = R.dimen.margin16))
                    .fillMaxWidth()
                    .weight(weight = 1F),
                    verticalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(id = R.string.book_seats),
                    style = MaterialTheme.typography.titleSmall)

                Text(
                    text = stringResource(id = R.string.slide_right_to_book),
                    style = MaterialTheme.typography.bodyLarge)
            }

            VerticalDivider(
                modifier = Modifier
                    .padding(start = dimensionResource(id = R.dimen.margin16))
                    .fillMaxHeight(),
                color = Color.White.copy(alpha = 0.5F)
            )

            Text(text = stringResource(id = R.string.amount, "${ amount ?: 0 }"),
                textAlign = TextAlign.End,
                fontSize = 18.fixedTextSize(),
                modifier = Modifier.width(width = dimensionResource(id = R.dimen.margin64)),
                style = MaterialTheme.typography.headlineMedium)
            }
        }
    }
}

@Composable
fun SingleDateView(
    item : Pair<String, String>,
    isSelected : Boolean,
    onClick : () -> Unit
) {
    Column(modifier = Modifier
        .clip(shape = CircleShape)
        .clickable { onClick() }
        .background(color = if (isSelected) PrimaryGradientColor else SwitchBackgroundColor)
        .padding(all = dimensionResource(id = R.dimen.margin4)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        Text(text = item.first,
            color = Color.White,
            style = MaterialTheme.typography.labelMedium)

        CustomHeightSpacer(dimenResId = R.dimen.margin4)

        Box(modifier = Modifier
            .clip(shape = CircleShape)
            .size(size = 22.dp)
            .background(color = if (isSelected) Color.White else SecondaryLightColor),
            contentAlignment = Alignment.Center
        ) {
            Text(text = item.second,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isSelected) PrimaryGradientColor else Color.Black)
        }
    }
}

@Composable
fun SingleTimeView(isSelected : Boolean,
                   onClick : () -> Unit,
                   time : String) {

    Box(modifier = Modifier
        .clip(shape = MaterialTheme.shapes.medium)
        .clickable { onClick() }
        .background(color = if (isSelected) PrimaryGradientColor else SwitchBackgroundColor)
        .padding(horizontal = dimensionResource(id = R.dimen.margin8),
            vertical = dimensionResource(id = R.dimen.margin4)),
        contentAlignment = Alignment.Center) {

        Text(text = time,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White)
    }
}