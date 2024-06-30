package com.example.movieapp.utils.other

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
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
}