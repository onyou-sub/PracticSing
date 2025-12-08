package com.example.practicsing.data

import android.content.Context
import android.content.SharedPreferences

object PracticePrefs {

    private const val PREF_NAME = "practice_prefs"
    private const val KEY_LAST_PRACTICE_EPOCH_DAY = "last_practice_epoch_day"
    private const val KEY_CURRENT_DAY = "current_day"   // 연속 일수 = Day

    private fun prefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private fun todayEpochDay(): Long {
        val millisPerDay = 24L * 60L * 60L * 1000L
        val nowMillis = System.currentTimeMillis()
        return nowMillis / millisPerDay
    }

    /** 오늘 이미 연습을 했는지 여부 */
    fun hasPracticedToday(context: Context): Boolean {
        val lastDay = prefs(context).getLong(KEY_LAST_PRACTICE_EPOCH_DAY, -1L)
        return lastDay == todayEpochDay()
    }

    /** 현재 Day (연속 일수). 끊기면 1부터 다시 시작 */
    fun getCurrentDay(context: Context): Int {
        // 기본 1일부터 시작
        return prefs(context).getInt(KEY_CURRENT_DAY, 1)
    }

    fun registerPracticeDone(context: Context): Int {
        val p = prefs(context)
        val today = todayEpochDay()
        val lastDay = p.getLong(KEY_LAST_PRACTICE_EPOCH_DAY, -1L)
        val currentDay = p.getInt(KEY_CURRENT_DAY, 1)

        val newDay = when {
            lastDay == today -> currentDay           // 오늘 이미 기록되어 있음
            lastDay == today - 1 -> currentDay + 1   // 어제에 이어 오늘
            else -> 1                                // 끊겼으니 1일부터
        }

        p.edit()
            .putLong(KEY_LAST_PRACTICE_EPOCH_DAY, today)
            .putInt(KEY_CURRENT_DAY, newDay)
            .apply()

        return newDay
    }
}
