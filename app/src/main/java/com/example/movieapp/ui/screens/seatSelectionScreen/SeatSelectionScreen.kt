package com.example.movieapp.ui.screens.seatSelectionScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieapp.R
import com.example.movieapp.ui.common.CustomHeightSpacer
import com.example.movieapp.ui.common.CustomModalBottomSheetLayout
import com.example.movieapp.ui.common.CustomScaffold
import com.example.movieapp.ui.common.CustomTopBar
import com.example.movieapp.ui.common.CustomWidthSpacer
import com.example.movieapp.ui.theme.AppBackgroundColor
import com.example.movieapp.ui.theme.SecondaryLightColor
import com.example.movieapp.ui.theme.YellowColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SeatSelectionScreen(navController : NavController,
                        viewModel : SeatSelectionViewModel) {

    val theatreLayoutMap = viewModel.theatreHallSeatList.layout
    val selectedSeats by viewModel.selectedSeats.collectAsState(initial = null)
    val bottomSheetScaffoldState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    var screenWidth by remember { mutableIntStateOf(value = 0) }
    val verticalScrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()
    val isVisible by remember { derivedStateOf { horizontalScrollState.value > screenWidth / 4 } }
    val totalTicketsTobeBooked by viewModel.totalSeatsToBeBooked.collectAsState(initial = 0)
    val animatedSeatCount by animateIntAsState(targetValue = totalTicketsTobeBooked, label = "Animated Ticket Count")

    LaunchedEffect(key1 = screenWidth) {
        horizontalScrollState.scrollTo(value = screenWidth / 4)
    }

    CustomModalBottomSheetLayout(sheetContent = {
        SeatNumberSelectionBottomSheetView(selectedSeatsCount = 5,
            onClick = { scope.launch { bottomSheetScaffoldState.hide() } }) },
        sheetState = bottomSheetScaffoldState) {
        CustomScaffold(topBar = {
            CustomTopBar(text = "Eurasia Cinemas",
                description = "The Batman",
                bottomComposableView = {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(id = R.dimen.margin16)),
                        verticalAlignment = Alignment.CenterVertically) {
                        IconTextView(icon = R.drawable.ic_calendar,
                            modifier = Modifier.weight(weight = 1F),
                            onClick = {  },
                            text = "Apr 14")

                        CustomWidthSpacer(dimenResId = R.dimen.margin16)

                        IconTextView(icon = R.drawable.ic_clock,
                            modifier = Modifier.weight(weight = 1F),
                            onClick = {  },
                            text = "15 : 10")

                        CustomWidthSpacer(dimenResId = R.dimen.margin16)
                        
                        Column(modifier = Modifier
                            .clip(shape = CircleShape)
                            .clickable { scope.launch { bottomSheetScaffoldState.show() } }
                            .background(color = SecondaryLightColor)
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
                },
                onNavigationButtonClick = { navController.navigateUp() })
        }) { paddingValues ->
            Column(modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
        }
    }
}