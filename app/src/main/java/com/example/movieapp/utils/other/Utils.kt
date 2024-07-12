package com.example.movieapp.utils.other

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import com.example.movieapp.data.response.InstalledUPIApps
import com.example.movieapp.internal.extensions.showToast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object Utils {
    fun getDate(date : Date?) : String? {
        if (date == null) return null
        val calendar = Calendar.getInstance()
        calendar.time = date
        val dateFormat = SimpleDateFormat("EEE MMMM yyyy", Locale.ENGLISH)
        return dateFormat.format(date)
    }

    fun formatTodayDate(date: Date) : String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        return dateFormat.format(date)
    }

    fun openYoutubeLink(youtubeID: String?, context : Context) {
        if (youtubeID.isNullOrEmpty()) {
            context.showToast(message = "Video Not Found")
            return
        }
        val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$youtubeID"))
        val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$youtubeID"))
        try {
            context.startActivity(intentApp)
        } catch (ex: ActivityNotFoundException) {
            context.startActivity(intentBrowser)
        }
    }

    fun getInstalledUPIApps(context: Context): List<InstalledUPIApps> {
        val packageManager: PackageManager = context.packageManager
        val upiIntent = Intent(Intent.ACTION_VIEW)
        upiIntent.data = Uri.parse("upi://pay")
        val resolveInfoList = packageManager.queryIntentActivities(upiIntent, PackageManager.MATCH_DEFAULT_ONLY)
        Log.d("ResolveInfo", resolveInfoList.toString())
        val upiApps = mutableListOf<InstalledUPIApps>()
        for (item in resolveInfoList) {
            upiApps.add(
                InstalledUPIApps(icon = item.loadIcon(packageManager),
                    name = item.loadLabel(packageManager).toString(),
                    packageName = item.activityInfo.packageName)
            )
        }
        return upiApps
    }

    fun makePayment(packageName : String?,
                    startActivityLauncher : ManagedActivityResultLauncher<Intent, ActivityResult>
    ) {
        val upiId = "+919567467497@federal"
        val amount = 10
        val uri = "upi://pay?pa=$upiId&pn=RecipientName&am=$amount&cu=INR"
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(uri)
            setPackage(packageName)
        }
        startActivityLauncher.launch(intent)
    }

}