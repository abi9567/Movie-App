package com.abi.movieapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HiltApplication() : Application() {
//    companion object {
//        private lateinit var instance : HiltApplication
//        fun get() = instance
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        instance = this
//    }
}