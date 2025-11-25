package com.example.practicsing.data

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

object PracticePrefs {

    private const val PREF_NAME = "practice_prefs"
    private const val KEY_DAY = "current_day"
    private const val KEY_LAST_DATE = "last_practice_date"

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun getCurrentDay(context: Context): Int {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return pref.getInt(KEY_DAY, 1)
    }

    fun canIncreaseDay(context: Context): Boolean {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val lastDate = pref.getString(KEY_LAST_DATE, null)
        val today = dateFormat.format(Date())


        if (lastDate == null) return false

        return lastDate != today
    }

    fun increaseDay(context: Context) {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val current = getCurrentDay(context)
        val editor = pref.edit()

        if (current < 5) editor.putInt(KEY_DAY, current + 1)
        editor.apply()
    }


    fun updateLastPracticeDate(context: Context) {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val today = dateFormat.format(Date())

        pref.edit()
            .putString(KEY_LAST_DATE, today)
            .apply()
    }


    fun didPracticeToday(context: Context): Boolean {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val lastDate = pref.getString(KEY_LAST_DATE, null)
        val today = dateFormat.format(Date())
        return lastDate == today
    }
}
