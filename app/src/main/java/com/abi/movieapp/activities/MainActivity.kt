package com.abi.movieapp.activities

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.abi.movieapp.navigation.NavigationGraph
import com.abi.movieapp.ui.theme.MovieAppTheme
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        registerUpdateResult()

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                window.isNavigationBarContrastEnforced = false
            }
            MovieAppTheme {
                NavigationGraph(navController = navController)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkUpdate()
    }

    private var updateFlowResultLauncher: ActivityResultLauncher<IntentSenderRequest>? = null

    private fun registerUpdateResult() {
        updateFlowResultLauncher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // Handle successful app update
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        updateFlowResultLauncher?.apply {
            unregister()
            updateFlowResultLauncher = null
        }
    }

    private fun checkUpdate() {
        val appUpdateManager = AppUpdateManagerFactory.create(this)
        updateFlowResultLauncher?.let { launcher ->
            appUpdateManager.appUpdateInfo
                .addOnSuccessListener { updateInfo ->
                    if(updateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                        if (updateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                            appUpdateManager.startUpdateFlowForResult(updateInfo, launcher,
                                AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build())
                        }
                    }
                }
        }
    }
}