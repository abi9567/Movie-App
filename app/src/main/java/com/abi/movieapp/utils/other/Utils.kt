package com.abi.movieapp.utils.other

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import com.abi.movieapp.data.response.InstalledUPIApps
import com.abi.movieapp.internal.enums.Language
import com.abi.movieapp.internal.extensions.showToast
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

    fun convertToDate(time : Long) : Pair<String, String> {
        val calendarDate = Date(time)
        val monthFormat = SimpleDateFormat("MMM", Locale.US)
        val dateFormat = SimpleDateFormat("dd", Locale.US)
        val month = monthFormat.format(calendarDate)
        val date = dateFormat.format(calendarDate)
        return Pair(month, date)
    }

    fun selectedDateStringValue(language : String) : String {
        return when(language) {
            Language.English.name -> "en"
            Language.Malayalam.name -> "ml"
            Language.Hindi.name -> "hi"
            Language.Spanish.name -> "es"
            Language.French.name -> "fr"
            Language.German.name -> "de"
            Language.Japanese.name -> "ja"
            Language.Korean.name -> "ko"
            Language.Chinese.name -> "zh"
            Language.Russian.name -> "ru"
            Language.Italian.name -> "it"
            Language.Portuguese.name -> "pt"
            Language.Arabic.name -> "ar"
            Language.Tamil.name -> "ta"
            Language.Telugu.name -> "te"
            else -> "en"
        }
    }

    fun getNextDaysList() : List<Pair<String, String>> {
        val calendar = Calendar.getInstance()
        val time = calendar.time.time
        val calculationValue : Long = 86400000
        val nextDaysListInMilliSecond = listOf(
            (time + 0 * calculationValue),
            (time + 1 * calculationValue),
            (time + 2 * calculationValue),
            (time + 3 * calculationValue),
            (time + 4 * calculationValue),
            (time + 5 * calculationValue),
            (time + 6 * calculationValue),
            (time + 7 * calculationValue),
            (time + 8 * calculationValue),
            (time + 9 * calculationValue))
        val nextDaysList = nextDaysListInMilliSecond.map { convertToDate(time = it) }
        return nextDaysList
    }
}