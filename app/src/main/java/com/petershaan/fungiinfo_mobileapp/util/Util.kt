package com.dicoding.asclepius.util

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private const val DATE_FORMAT = "dd-MMM-yyyy"
private const val MAXIMAL_SIZE = 1000000

fun parseTimestamp(timestamp: Long): String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val date = Date(timestamp)
    return simpleDateFormat.format(date)
}

val timeStamp: String = SimpleDateFormat(
    DATE_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

fun createCustomTempFile(context: Context): File {
    val filesDir = context.externalCacheDir
    return File.createTempFile(timeStamp, ".jpg", filesDir)
}

fun convertTimestampToString(
    timestampString: String,
    inputFormat: String = "yyyy-MM-dd'T'HH:mm:ss'Z'",
    outputFormat: String = "yyyy-MM-dd HH:mm:ss",
): String {
    val inputDateFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
    val outputDateFormat = SimpleDateFormat(outputFormat, Locale.getDefault())
    inputDateFormat.timeZone = TimeZone.getTimeZone("UTC") // Set input timezone if known

    return try {
        val parsedDate = inputDateFormat.parse(timestampString)
        outputDateFormat.format(parsedDate)
    } catch (e: Exception) {
        // Handle parse errors
        e.printStackTrace()
        ""
    }
}