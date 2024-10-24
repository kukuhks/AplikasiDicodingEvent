package com.ks.aplikasidicodingevent.ui.setting

import android.content.Context
import android.content.SharedPreferences

class NotificationPreference(context: Context) {
    private val sharedPreferences : SharedPreferences =
        context.getSharedPreferences("notification", Context.MODE_PRIVATE)

    fun setNotificationEnabled(isEnabled: Boolean) {
        sharedPreferences.edit().putBoolean("is_notification_enabled", isEnabled).apply()
    }

    fun isNotificationEnabled() : Boolean {
        return sharedPreferences.getBoolean("is_notification_enabled", false)
    }
}