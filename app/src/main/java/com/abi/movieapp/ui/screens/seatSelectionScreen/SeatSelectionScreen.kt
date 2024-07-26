package com.abi.movieapp.ui.screens.seatSelectionScreen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.abi.movieapp.R
import com.abi.movieapp.internal.enums.DateSelectionEnum
import com.abi.movieapp.internal.extensions.navigateAndSendData
import com.abi.movieapp.internal.extensions.showToast
import com.abi.movieapp.navigation.Screen
import com.abi.movieapp.ui.common.CustomHeightSpacer
import com.abi.movieapp.ui.common.CustomModalBottomSheetLayout
import com.abi.movieapp.ui.common.CustomScaffold
import com.abi.movieapp.ui.common.CustomTopBar
import com.abi.movieapp.ui.common.CustomWidthSpacer
import com.abi.movieapp.ui.theme.AppBackgroundColor
import com.abi.movieapp.ui.theme.ButtonGradientBrush
import com.abi.movieapp.ui.theme.MidnightBlue
import com.abi.movieapp.ui.theme.YellowColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SeatSelectionScreen(navController : NavController,
                        viewModel : SeatSelectionViewModel) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val theatreLayoutMap = viewModel.theatreHallSeatList.layout
    val selectedSeats by viewModel.selectedSeats.collectAsState(initial = null)
    val bottomSheetScaffoldState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { false })
    var screenWidth by remember { mutableIntStateOf(value = 0) }
    val verticalScrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()
    val isVisible by remember { derivedStateOf { horizontalScrollState.value > screenWidth / 4 } }
    val totalTicketsTobeBooked by viewModel.totalSeatsToBeBooked.collectAsState(initial = 2)
    val animatedSeatCount by animateIntAsState(targetValue = totalTicketsTobeBooked,
        animationSpec = tween(easing = LinearEasing, durationMillis = 300),
        label = "Animated Ticket Count")
    val bookingAmount by viewModel.totalAmount.collectAsState(initial = null)

    val animatedBookingAmount by animateIntAsState(targetValue = bookingAmount ?: 0,
        animationSpec = tween(easing = LinearEasing),
        label = "Amount Animated Amount")

    val currentPicker by viewModel.currentPickerSelection.collectAsState(initial = DateSelectionEnum.NotVisible)
    val nextDaysList = viewModel.nextDaysList
    val selectedDate by viewModel.selectedDate.collectAsState(initial = nextDaysList.getOrNull(index = 0))

    val timeList by viewModel.timeList.collectAsState(initial = null)
    val selectedTime by viewModel.selectedTime.collectAsState(initial = null)
    val bookingDetail by viewModel.bookingDetails.collectAsState(initial = null)

    LaunchedEffect(key1 = screenWidth) {
        horizontalScrollState.scrollTo(value = screenWidth / 4)
    }

    BackHandler(enabled = true) {
        if (bottomSheetScaffoldState.isVisible) return@BackHandler
        navController.navigateUp()
    }

    CustomModalBottomSheetLayout(sheetContent = {
        SeatNumberSelectionBottomSheetView(selectedSeatsCount = totalTicketsTobeBooked,
            onClick = { selectedSeatsCount ->
                scope.launch { bottomSheetScaffoldState.hide() }
                if (selectedSeatsCount == totalTicketsTobeBooked) return@SeatNumberSelectionBottomSheetView
                viewModel.setTotalSeatsToBeBooked(count = selectedSeatsCount, resetSeatCount = true)
            }) },

        sheetState = bottomSheetScaffoldState) {
        CustomScaffold(topBar = {
            CustomTopBar(text = bookingDetail?.filmTheatreName ?: "Theatre Name",
                description = bookingDetail?.filmName,
                onNavigationButtonClick = { navController.navigateUp() })
        }) { paddingValues ->

            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)) {

                Column(modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(modifier = Modifier
                        .background(color = MidnightBlue.copy(alpha = 0.7F))
                        .animateContentSize()
                        .fillMaxWidth())
                    {
                        Row(modifier = Modifier
                            .animateContentSize()
                            .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically)
                        {
                            IconTextView(icon = R.drawable.ic_calendar,
                                modifier = Modifier
                                    .padding(start = dimensionResource(id = R.dimen.margin16))
                                    .weight(weight = 1F),
                                onClick = { viewModel.setPicker(item = DateSelectionEnum.Date) },
                                text = selectedDate?.first + " " + selectedDate?.second)

                            CustomWidthSpacer(dimenResId = R.dimen.margin16)

                            IconTextView(icon = R.drawable.ic_clock,
                                modifier = Modifier.weight(weight = 1F),
                                onClick = { viewModel.setPicker(item = DateSelectionEnum.Time) },
                                text = selectedTime ?: "")

                            CustomWidthSpacer(dimenResId = R.dimen.margin16)

                            Column(modifier = Modifier
                                .padding(end = dimensionResource(id = R.dimen.margin16))
                                .clip(shape = CircleShape)
                                .clickable { scope.launch { bottomSheetScaffoldState.show() } }
                                .background(brush = ButtonGradientBrush)
                                .size(size = 32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = "$animatedSeatCount",
                                    style = MaterialTheme.typography.titleSmall)
                                Icon(painter = painterResource(id = R.drawable.ic_edit),
                                    tint = Color.White,
                                    contentDescription = null)
                            }
                        }

                        AnimatedVisibility (visible = currentPicker != DateSelectionEnum.NotVisible,
                            enter = slideInVertically(animationSpec = tween()) { -it },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            LazyRow(modifier = Modifier
                                .padding(top = dimensionResource(id = R.dimen.margin16))
                                .animateContentSize(),
                                contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.margin16)),
                                horizontalArrangement = Arrangement
                                    .spacedBy(space = dimensionResource(id = R.dimen.margin16))
                            ) {
                                if (currentPicker == DateSelectionEnum.Date) {
                                    items(nextDaysList) { date ->
                                        SingleDateView(item = date,
                                            onClick = { viewModel.setDate(date = date) },
                                            isSelected = selectedDate == date)
                                    }
                                } else if (currentPicker == DateSelectionEnum.Time) {
                                    items(timeList ?: emptyList()) { time ->
                                        SingleTimeView(isSelected = time == selectedTime,
                                            time = time,
                                            onClick = { viewModel.setTime(time = time) })
                                    }
                                }
                            }
                        }
                        CustomHeightSpacer(dimenResId = R.dimen.margin16)
                    }

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = dimensionResource(id = R.dimen.margin16)),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        AvailableSeatView()
                        CustomWidthSpacer(dimenResId = R.dimen.margin24)
                        OccupiedSeatView()
                        CustomWidthSpacer(dimenResId = R.dimen.margin24)
                        ChosenSeatView()
                    }

                    CustomHeightSpacer(dimenResId = R.dimen.margin16)

                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        AnimatedVisibility(visible = !isVisible) {
                            Column(modifier = Modifier
                                .background(color = AppBackgroundColor.copy(alpha = 0.35F))
                                .verticalScroll(state = verticalScrollState))
                            {
                                Spacer(modifier = Modifier.height(height = 50.dp))

                                repeat(times = theatreLayoutMap.size) { position ->
                                    val seatNumber = theatreLayoutMap.keys.elementAt(index = position)
                                    SingleAlphabetView(seatNumber = seatNumber,
                                        fontColor = AppBackgroundColor,
                                        color = YellowColor
                                    )
                                }
                                Spacer(modifier = Modifier.height(height = 16.dp))
                            }
                        }

                        Row(modifier = Modifier
                            .weight(weight = 1F)
                            .verticalScroll(state = verticalScrollState)
                            .horizontalScroll(state = horizontalScrollState)
                            .onGloballyPositioned { screenWidth = it.size.width },
                            horizontalArrangement = Arrangement.Center
                        ) {
                            SeatView(alphabetList = theatreLayoutMap,
                                onSeatItemClick = viewModel::addOrRemoveSeat,
                                selectedSeats = selectedSeats)
                        }

                        AnimatedVisibility(visible = isVisible) {
                            Column(modifier = Modifier
                                .background(color = AppBackgroundColor.copy(alpha = 0.35F))
                                .verticalScroll(state = verticalScrollState)) {

                                Spacer(modifier = Modifier.height(height = 50.dp))

                                repeat(times = theatreLayoutMap.size) { position ->
                                    val seatNumber = theatreLayoutMap.keys.elementAt(index = position)
                                    SingleAlphabetView(seatNumber = seatNumber,
                                        fontColor = AppBackgroundColor,
                                        color = YellowColor
                                    )
                                }

                                Spacer(modifier = Modifier.height(height = 16.dp))
                            }
                        }
                    }
                }

                AnimatedVisibility(visible = bookingAmount != null,
                    enter = slideInVertically { it },
                    exit = slideOutVertically { 2 * it },
                    modifier = Modifier
                        .padding(all = dimensionResource(id = R.dimen.margin16))
                        .align(alignment = Alignment.BottomCenter)
                ) {
                    BuyTicketView(amount = animatedBookingAmount,
                        onSwipeEnd = {
                            if (selectedSeats?.size != totalTicketsTobeBooked) {
                                context.showToast(message = context.getString(R.string.please_select_remaining_seats))
                                return@BuyTicketView
                            }
                            navController.navigateAndSendData(
                                route = Screen.PayTicketScreen.route,
                                key = Screen.BOOKING_DETAIL,
                                data = bookingDetail?.copy(
                                    selectedSeatList = selectedSeats?.toList()
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}