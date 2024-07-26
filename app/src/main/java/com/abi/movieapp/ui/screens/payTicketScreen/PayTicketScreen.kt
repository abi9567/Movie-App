package com.abi.movieapp.ui.screens.payTicketScreen

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.abi.movieapp.R
import com.abi.movieapp.internal.enums.PaymentScreen
import com.abi.movieapp.internal.extensions.navigateWithPopUpToInclusive
import com.abi.movieapp.navigation.Screen
import com.abi.movieapp.ui.common.CustomGradientButton
import com.abi.movieapp.ui.common.CustomHeightSpacer
import com.abi.movieapp.ui.common.CustomLoading
import com.abi.movieapp.ui.common.CustomScaffold
import com.abi.movieapp.ui.common.CustomTextBoxView
import com.abi.movieapp.ui.common.CustomTopBar
import com.abi.movieapp.ui.theme.MidnightBlue

@Composable
fun PayTicketScreen(
    navController : NavController,
    viewModel : PayTicketViewModel
) {
    val paymentScreenEnum by viewModel.paymentScreenEnum.collectAsState(initial = PaymentScreen.MobileNumberView)
    var mobileNumber by remember { mutableStateOf<String?>(value = null) }
    val bookingDetails by viewModel.bookingDetails.collectAsState(initial = null)

    CustomScaffold(topBar = {
        CustomTopBar(text = stringResource(id = R.string.pay_for_tickets),
            onNavigationButtonClick = {
                when(paymentScreenEnum) {
                    PaymentScreen.MobileNumberView -> {
                        navController.navigateUp()
                    }
                    PaymentScreen.SelectPaymentMethodView -> {
                        viewModel.setPaymentScreenEnum(enum = PaymentScreen.MobileNumberView)
                    }
                    PaymentScreen.ShowUPIListView -> {
                        viewModel.setPaymentScreenEnum(enum = PaymentScreen.SelectPaymentMethodView)
                    }
                    PaymentScreen.PaymentSuccess -> {
                        navController.navigateWithPopUpToInclusive(
                            route = Screen.HomeScreen.route
                        )
                    }
                }

            })
    }) { paddingValues ->

        val isLoading by viewModel.isLoading.collectAsState(initial = false)
        val paymentMethodSelected by viewModel.paymentMethodSelected.collectAsState(initial = 0)
        val upiList by viewModel.upiAppsList.collectAsState(initial = null)

        val startActivityLauncher = rememberLauncherForActivityResult(contract =
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
//            val resultCode = result.resultCode
//            val resultData = result.data
//            val resultUri = resultData?.data
//            val extras = resultData?.extras
//            val keySet = resultData?.extras?.keySet()

//            Log.d("PayTicket", "Result : $result")
//            Log.d("PayTicket", "Result Code : $resultCode")
//            Log.d("PayTicket", "Result Data : $resultData")
//            Log.d("PayTicket", "Result Uri : $resultUri")
//            Log.d("PayTicket", "Extras : $extras")
//            Log.d("PayTicket", "KeySet : $keySet")
//            Log.d("PayTicket", "KeySetSize : ${keySet?.size}")
//
//            keySet?.forEach { key ->
//                Log.d("PayTicket", "value for $key : ${ extras?.getString(key) }\n")
//            }
//            val query = resultUri?.getQueryParameter("response")
//            Log.d("PayTicket", "Query Response ->  : $query")

//            if(resultCode == Activity.RESULT_OK) {
//                result.data?.let {
//                    val status = it.getStringExtra("Status")
//                    Log.d("PayTicket", "Status -> $status")
//                }
//            } else {
//                Log.d("PayTicket", "Failed")
//                val status = result.data?.getStringExtra("Status")
//                Log.d("PayTicket", "Status -> $status")
//            }

            viewModel.apply {
//                setLoading()
                setPaymentScreenEnum(enum = PaymentScreen.PaymentSuccess)
            }
        }

        if (isLoading) {
            CustomLoading()
            return@CustomScaffold
        }

        BackHandler(enabled = true) {
            when(paymentScreenEnum) {
                PaymentScreen.MobileNumberView -> {
                    navController.navigateUp()
                }
                PaymentScreen.SelectPaymentMethodView -> {
                    viewModel.setPaymentScreenEnum(enum = PaymentScreen.MobileNumberView)
                }
                PaymentScreen.ShowUPIListView -> {
                    viewModel.setPaymentScreenEnum(enum = PaymentScreen.SelectPaymentMethodView)
                }
                PaymentScreen.PaymentSuccess -> {
                    navController.navigateWithPopUpToInclusive(
                        route = Screen.HomeScreen.route
                    )
                }
            }
        }

        Column(modifier = Modifier
            .padding(paddingValues = paddingValues)
            .fillMaxWidth())
        {
            Column(modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.margin8),
                    end = dimensionResource(id = R.dimen.margin8),
                    top = dimensionResource(id = R.dimen.margin8)
                )
                .background(color = MidnightBlue, shape = MaterialTheme.shapes.medium)
                .padding(top = dimensionResource(id = R.dimen.margin16))
                .fillMaxWidth()) {

                TicketDetailView(isPaymentSuccess = paymentScreenEnum == PaymentScreen.PaymentSuccess,
                    bookingDetail = bookingDetails)
                CustomHeightSpacer(dimenResId = R.dimen.margin16)
                TicketDotView(modifier = Modifier)
            }

            AnimatedVisibility(visible = paymentScreenEnum == PaymentScreen.PaymentSuccess) {
                Column(modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.margin8))
                    .background(
                        color = MidnightBlue, shape = MaterialTheme.shapes.medium.copy(
                            topStart = CornerSize(size = 0.dp), topEnd = CornerSize(size = 0.dp)
                        )
                    )
                    .padding(bottom = dimensionResource(id = R.dimen.margin16))
                    .fillMaxWidth()) {
                    TicketDotView(modifier = Modifier.rotate(degrees = 180F))
                    CustomHeightSpacer(dimenResId = R.dimen.margin16)
                    PaymentSuccessView()

                    CustomGradientButton(text = stringResource(id = R.string.done),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = dimensionResource(id = R.dimen.margin16)),
                        verticalPadding = 14.dp,
                        onClick = {
                            navController.navigateWithPopUpToInclusive(
                                route = Screen.HomeScreen.route
                            )
                        })
                }
            }

            AnimatedVisibility(visible = paymentScreenEnum == PaymentScreen.MobileNumberView) {
                    Column(modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.margin8))
                        .background(
                            color = MidnightBlue, shape = MaterialTheme.shapes.medium.copy(
                                topStart = CornerSize(size = 0.dp), topEnd = CornerSize(size = 0.dp)
                            )
                        )
                        .padding(bottom = dimensionResource(id = R.dimen.margin16))
                        .fillMaxWidth()) {

                        TicketDotView(modifier = Modifier.rotate(degrees = 180F))
                        CustomHeightSpacer(dimenResId = R.dimen.margin16)

                        CustomTextBoxView(value = mobileNumber,
                            keyboardType = KeyboardType.NumberPassword,
                            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.margin16)),
                            onValueChane = { if ((it?.length ?: 0) > 10) return@CustomTextBoxView
                                mobileNumber = it },
                            placeHolderText = stringResource(id = R.string.phone_number))

                        CustomHeightSpacer(dimenResId = R.dimen.margin16)

                        CustomGradientButton(text = stringResource(id =
                        R.string.continue_with_amount, (bookingDetails?.bookingAmount ?: 0) + 51),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = dimensionResource(id = R.dimen.margin16)),
                            verticalPadding = 14.dp,
                            onClick = { viewModel.setPhoneNumberEntered() })
                    }
                }
        }
    }
}