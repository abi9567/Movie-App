package com.abi.movieapp.ui.screens.payTicketScreen

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abi.movieapp.data.response.InstalledUPIApps
import com.abi.movieapp.internal.enums.PaymentScreen
import com.abi.movieapp.utils.other.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PayTicketViewModel @Inject constructor(@ApplicationContext private val context : Context) : ViewModel() {

    private val _isLoading = MutableStateFlow(value = false)
    val isLoading : Flow<Boolean> = _isLoading

    private val _isPhoneNumberEntered = MutableStateFlow(value = false)
    val isPhoneNumberEntered : Flow<Boolean> = _isPhoneNumberEntered

    private val _paymentMethodSelected = MutableStateFlow<Int?>(value = null)
    val paymentMethodSelected : Flow<Int?> = _paymentMethodSelected

    private val _upiAppsList = MutableStateFlow<List<InstalledUPIApps>?>(value = null)
    val upiAppsList : Flow<List<InstalledUPIApps>?> = _upiAppsList

    private val _paymentScreenEnum = MutableStateFlow(value = PaymentScreen.MobileNumberView)
    val paymentScreenEnum : Flow<PaymentScreen> = _paymentScreenEnum

    init {
//        getUPIAppsList()
    }

    fun setLoading() {
        viewModelScope.launch {
            _isLoading.value = true
            delay(timeMillis = 1000)
            _isLoading.value = false
        }
    }

    fun setPhoneNumberEntered() {
        _isPhoneNumberEntered.value = !(_isPhoneNumberEntered.value)
//        setPaymentScreenEnum(enum = PaymentScreen.SelectPaymentMethodView)
        setPaymentScreenEnum(enum = PaymentScreen.EmptyAnimationView)
        viewModelScope.launch {
            delay(timeMillis = 450)
            setPaymentScreenEnum(enum = PaymentScreen.PaymentSuccess)
        }
    }

    fun setPaymentMethodSelected(position : Int) {
        _paymentMethodSelected.value = position
    }

    private fun getUPIAppsList() {
        val list = Utils.getInstalledUPIApps(context = context)
        setUPIAppList(list = list)
    }

    private fun setUPIAppList(list : List<InstalledUPIApps>?) {
        _upiAppsList.value = list
    }

    fun setPaymentScreenEnum(enum : PaymentScreen) {
        _paymentScreenEnum.value = enum
    }

}