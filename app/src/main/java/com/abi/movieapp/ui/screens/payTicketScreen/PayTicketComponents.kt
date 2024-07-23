package com.abi.movieapp.ui.screens.payTicketScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.abi.movieapp.R
import com.abi.movieapp.data.response.InstalledUPIApps
import com.abi.movieapp.ui.common.CustomHeightSpacer
import com.abi.movieapp.ui.common.CustomWidthSpacer
import com.abi.movieapp.ui.screens.detailScreen.DetailScreenTitleDescriptionView
import com.abi.movieapp.ui.theme.AppBackgroundColor
import com.abi.movieapp.ui.theme.AppBackgroundColorGradient
import com.abi.movieapp.ui.theme.ButtonGradientBrush
import com.abi.movieapp.ui.theme.SecondaryLightColor
import com.abi.movieapp.ui.theme.TextFieldBorderColor

@Composable
fun TicketDetailView(isPaymentSuccess : Boolean) {

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = dimensionResource(id = R.dimen.margin16)))
    {
        Text(text = "The Batman",
            style = MaterialTheme.typography.headlineMedium)

        CustomHeightSpacer(dimenResId = R.dimen.margin16)

        DetailScreenTitleDescriptionView(title = stringResource(id = R.string.theatre),
            description = "Eurasia Cinema7")

        CustomHeightSpacer(dimenResId = R.dimen.margin8)
        DetailScreenTitleDescriptionView(title = stringResource(id = R.string.date),
            description = "6 April 2022, 14:40")

        CustomHeightSpacer(dimenResId = R.dimen.margin8)
        DetailScreenTitleDescriptionView(title = stringResource(id = R.string.hall),
            description = "6th")

        CustomHeightSpacer(dimenResId = R.dimen.margin8)
        DetailScreenTitleDescriptionView(title = stringResource(id = R.string.seats),
            description = "7 row (7,8)")

        HorizontalDivider(color = TextFieldBorderColor,
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.margin16)))

        AnimatedVisibility(visible = !isPaymentSuccess) {
            Column(modifier = Modifier.fillMaxWidth()) {
                DetailScreenTitleDescriptionView(title = stringResource(id = R.string.ticket_price),
                    description = stringResource(id = R.string.amount, 120)
                )

                CustomHeightSpacer(dimenResId = R.dimen.margin8)
                DetailScreenTitleDescriptionView(title = stringResource(id = R.string.convenience_fee),
                    description = stringResource(id = R.string.amount, 50)
                )

                CustomHeightSpacer(dimenResId = R.dimen.margin8)
                DetailScreenTitleDescriptionView(title = stringResource(id = R.string.contribution),
                    description = stringResource(id = R.string.amount, 1)
                )
            }
        }

        CustomHeightSpacer(dimenResId = R.dimen.margin8)
        DetailScreenTitleDescriptionView(title = stringResource(id = R.string.order_total),
            description = stringResource(id = R.string.amount, 171)
        )
    }
}

@Composable
fun TicketDotView() {
    Row(modifier = Modifier
        .width(width = 1000.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(painter = painterResource(id = R.drawable.ic_semi_circle_left),
            tint = Color.Unspecified,
            contentDescription = null)

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .weight(weight = 1F),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(times = 13) {
                SingleDotView()
            }
        }

        Icon(painter = painterResource(id = R.drawable.ic_semi_circle_right),
            tint = Color.Unspecified,
            contentDescription = null)
    }
}

@Composable
fun SelectPaymentMethodView(
    paymentMethodSelected : Int?,
    onClick : (Int) -> Unit
) {

    Column(modifier = Modifier
        .padding(horizontal = dimensionResource(id = R.dimen.margin16))
        .fillMaxWidth()) {

        Text(text = stringResource(id = R.string.choose_payment_method),
            color = SecondaryLightColor,
            style = MaterialTheme.typography.titleLarge)

        HorizontalDivider(color = TextFieldBorderColor,
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.margin8))
        )

        repeat(times = 3) {
            PaymentOptionSingleView(isSelected = paymentMethodSelected == it,
                isUpiView = it == 1,
                isAddCardView = it == 2,
                onClick = { onClick(it) })
        }
    }
}

@Composable
private fun PaymentOptionSingleView(
    isSelected : Boolean,
    isUpiView : Boolean,
    isAddCardView : Boolean,
    onClick : () -> Unit
) {
    Row(modifier = Modifier
        .padding(vertical = dimensionResource(id = R.dimen.margin8))
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null, onClick = onClick
        )
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier
            .clip(shape = CircleShape)
            .background(brush = if (isSelected) ButtonGradientBrush else AppBackgroundColorGradient)
            .size(size = dimensionResource(id = R.dimen.margin24)),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.animation.AnimatedVisibility (visible = isSelected,
                enter = slideInVertically { -it },
                exit = fadeOut()
            ) {
                Box(modifier = Modifier
                    .clip(shape = CircleShape)
                    .background(color = Color.White)
                    .size(size = 12.dp))
            }
        }

        CustomWidthSpacer(dimenResId = R.dimen.margin16)

        Icon(painter = painterResource(id = if (isUpiView) R.drawable.ic_upi
        else if (isAddCardView) R.drawable.ic_add
        else R.drawable.ic_visa),
            modifier = Modifier
                .height(height = 14.dp)
                .width(width = 18.dp)
                .fillMaxSize(),
            tint = Color.Unspecified,
            contentDescription = null)

        CustomWidthSpacer(dimenResId = R.dimen.margin16)

        Text(text = if (isUpiView) "UPI" else if (isAddCardView) "Add Card" else "4716 •••• •••• 5615",
            modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 1F),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = Medium))

        if (!isUpiView && !isAddCardView) {
            CustomWidthSpacer(dimenResId = R.dimen.margin8)
            Text(text = "06/24",
                color = SecondaryLightColor,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = Medium))
        }
    }
}

@Composable
fun SingleUPIListView(item : InstalledUPIApps?,
                      onClick : () -> Unit
                      ) {

    Row(modifier = Modifier
        .padding(
            horizontal = dimensionResource(id = R.dimen.margin16),
            vertical = dimensionResource(id = R.dimen.margin8)
        )
        .clickable { onClick() }
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(model = ImageRequest
            .Builder(context = LocalContext.current)
            .data(item?.icon)
            .build(),
            modifier = Modifier.size(size = 22.dp),
            contentDescription = null)

        CustomWidthSpacer(dimenResId = R.dimen.margin16)

        Text(text = item?.name ?: "",
            style = MaterialTheme.typography.titleSmall)
    }
}

@Composable
fun PaymentSuccessView() {
    Column(modifier = Modifier
//        .graphicsLayer { rotationY = -180F }
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .shadow(
                elevation = 12.dp,
                clip = true,
                shape = MaterialTheme.shapes.medium,
                spotColor = Color.White
            )
            .clip(shape = MaterialTheme.shapes.medium)
            .background(color = Color.White)
            .width(width = 200.dp)
            .height(height = 200.dp)
            .padding(all = dimensionResource(id = R.dimen.margin16))
        ) {
            Image(painter = painterResource(id = R.drawable.qr_code),
                contentDescription = null)
        }

        CustomHeightSpacer(dimenResId = R.dimen.margin8)

        Text(text = stringResource(id = R.string.show_this_to_gate_keeper),
            style = MaterialTheme.typography.bodyLarge)

        CustomHeightSpacer(dimenResId = R.dimen.margin16)
    }
}

@Composable
private fun SingleDotView() {
    Box(modifier = Modifier
        .padding(horizontal = dimensionResource(id = R.dimen.margin4))
        .clip(shape = CircleShape)
        .background(color = AppBackgroundColor)
        .size(size = 12.dp))
}