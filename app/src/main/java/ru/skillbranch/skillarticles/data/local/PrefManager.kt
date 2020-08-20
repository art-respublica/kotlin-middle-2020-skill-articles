package ru.skillbranch.skillarticles.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.VisibleForTesting
import androidx.preference.PreferenceManager

class PrefManager(context: Context) {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun clearAll() {
        preferences.edit().clear().apply()
    }

    fun <T> put(key: String, value: T) {
        with(preferences.edit()) {
            when (value) {
                is Boolean -> putBoolean(key, value)
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                else -> throw NotFoundRealizationException(value)
            }.apply()
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String, defValue: T): T? {
        with(preferences) {
            return when (defValue) {
                is Boolean -> (getBoolean(key, defValue) as? T) ?: defValue
                is String -> (getString(key, defValue) as? T) ?: defValue
                is Int -> (getInt(key, defValue) as? T) ?: defValue
                is Long -> (getLong(key, defValue) as? T) ?: defValue
                is Float -> (getFloat(key, defValue) as? T) ?: defValue
                else -> throw NotFoundRealizationException(defValue)
            }
        }
    }

    class NotFoundRealizationException(defValue: Any?) :
        Exception("not found realization for $defValue")
}

