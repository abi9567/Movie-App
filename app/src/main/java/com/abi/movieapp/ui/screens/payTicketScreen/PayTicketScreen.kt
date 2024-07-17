package com.abi.movieapp.ui.screens.payTicketScreen

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.abi.movieapp.R
import com.abi.movieapp.internal.enums.PaymentScreen
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
                        navController.navigateUp()
                    }
                }

            })
    }) { paddingValues ->

        val isLoading by viewModel.isLoading.collectAsState(initial = false)
        val paymentMethodSelected by viewModel.paymentMethodSelected.collectAsState(initial = 0)
        val upiList by viewModel.upiAppsList.collectAsState(initial = null)

        val animateRotation by animateFloatAsState(
            animationSpec = tween(durationMillis = 900, easing = LinearEasing),
            targetValue = if (paymentScreenEnum == PaymentScreen.PaymentSuccess) -180F else 0F,
            label = "Animate")

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
                    navController.navigateUp()
                }
            }
        }

        Column(modifier = Modifier
            .padding(paddingValues = paddingValues)
            .fillMaxWidth()) {

            Column(modifier = Modifier
                .graphicsLayer {
                    rotationY = animateRotation
                }
                .padding(all = dimensionResource(id = R.dimen.margin8))
                .background(color = MidnightBlue, shape = MaterialTheme.shapes.medium)
                .padding(vertical = dimensionResource(id = R.dimen.margin16))
                .fillMaxWidth()) {

                TicketDetailView(isPaymentSuccess = paymentScreenEnum == PaymentScreen.PaymentSuccess)
                CustomHeightSpacer(dimenResId = R.dimen.margin16)
                TicketDotView()
                CustomHeightSpacer(dimenResId = R.dimen.margin16)

                when(paymentScreenEnum) {
                    PaymentScreen.MobileNumberView -> {
                        AnimatedVisibility(visibleState = remember {
                            MutableTransitionState(initialState = false).apply { targetState = true }
                        }) {
                            CustomTextBoxView(value = "",
                                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.margin16)),
                                onValueChane = {},
                                placeHolderText = stringResource(id = R.string.phone_number))
                        }
                    }
                    PaymentScreen.SelectPaymentMethodView -> {
                        AnimatedVisibility(visibleState = remember {
                            MutableTransitionState(initialState = false).apply { targetState = true }
                        }) {
                            SelectPaymentMethodView(
                                paymentMethodSelected = paymentMethodSelected,
                                onClick = viewModel::setPaymentMethodSelected
                            )
                        }
                    }
                    PaymentScreen.ShowUPIListView -> {
                        AnimatedVisibility(visibleState = remember {
                            MutableTransitionState(initialState = false).apply { targetState = true }
                        }) {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                repeat(times = upiList?.size ?: 0) {
                                    val item = upiList?.get(index = it)
                                    SingleUPIListView(item = item,
                                        onClick = {
                                            val uri = Uri.Builder()
                                                .scheme("upi")
                                                .authority("pay")
                                                .appendQueryParameter("pa", "9567467497@kotak")
                                                .appendQueryParameter("pn", "Test")
                                                .appendQueryParameter("mc", "5411")
                                                .appendQueryParameter("tr", "EZV2021111610485300031599")
                                                .appendQueryParameter("tn", "Booking")
                                                .appendQueryParameter("am", "1.01")
                                                .appendQueryParameter("cu", "INR")
                                                .appendQueryParameter("mode", "04")
                                                .appendQueryParameter("orgid", "1234567")
                                                .appendQueryParameter("sign", "123456745654654654AAABV")

//                                                .appendQueryParameter("mid", "98644454584A")
//                                                .appendQueryParameter("tid", "Q12345")
//                                                .appendQueryParameter("mtid", "84651153416514")
//                                                .appendQueryParameter("msid", "A123524A")
                                                .build()
                                                val intent = Intent(Intent.ACTION_VIEW)
                                                    .setPackage(item?.packageName)
                                                    .setData(uri)
                                                startActivityLauncher.launch(intent)
                                    })
                                }
                            }
                        }
                    }
                    PaymentScreen.PaymentSuccess -> {
                        AnimatedVisibility(visibleState = remember {
                            MutableTransitionState(initialState = false).apply { targetState = true }
                        }) {
                            PaymentSuccessView()
                        }
                    }
                }

                CustomHeightSpacer(dimenResId = R.dimen.margin16)
                
                CustomGradientButton(text =
                when(paymentScreenEnum) {
                    PaymentScreen.MobileNumberView -> stringResource(id = R.string.continue_with_phone)
                    PaymentScreen.SelectPaymentMethodView -> stringResource(id = R.string.continue_with_amount, 170)
                    PaymentScreen.ShowUPIListView -> stringResource(id = R.string.continue_with_amount, 170)
                    PaymentScreen.PaymentSuccess -> stringResource(id = R.string.done)
                },
                    modifier = Modifier
                        .graphicsLayer {
                            rotationY =
                                if (paymentScreenEnum == PaymentScreen.PaymentSuccess) -180F else 0F
                        }
                        .padding(horizontal = dimensionResource(id = R.dimen.margin16))
                        .fillMaxWidth(),
                    verticalPadding = 14.dp,
                    onClick = {
                        when(paymentScreenEnum) {
                            PaymentScreen.MobileNumberView -> {
                                viewModel.setPhoneNumberEntered()
                            }
                            PaymentScreen.SelectPaymentMethodView -> {
                                viewModel.setPaymentScreenEnum(enum = PaymentScreen.ShowUPIListView)
                            }
                            PaymentScreen.ShowUPIListView -> {

                            }
                            PaymentScreen.PaymentSuccess -> {

                            }
                        }
                    }
                )
            }
        }
    }
}