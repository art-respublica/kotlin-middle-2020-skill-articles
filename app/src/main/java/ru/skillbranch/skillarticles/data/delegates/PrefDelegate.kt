package ru.skillbranch.skillarticles.data.delegates

import ru.skillbranch.skillarticles.data.local.PrefManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PrefDelegate<T>(private val defaultValue: T) : ReadWriteProperty<PrefManager, T?> {
    override fun getValue(thisRef: PrefManager, property: KProperty<*>): T? {
        val key = property.name
        with(thisRef.preferences) {
            return when (defaultValue) {
                is Boolean -> (getBoolean(key, defaultValue) as? T) ?: defaultValue
                is String -> (getString(key, defaultValue) as? T) ?: defaultValue
                is Int -> (getInt(key, defaultValue) as? T) ?: defaultValue
                is Long -> (getLong(key, defaultValue) as? T) ?: defaultValue
                is Float -> (getFloat(key, defaultValue) as? T) ?: defaultValue
                else -> throw NotFoundRealizationException(defaultValue)
            }
        }
    }

    override fun setValue(thisRef: PrefManager, property: KProperty<*>, value: T?) {
        val key = property.name
        with(thisRef.preferences.edit()) {
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

    class NotFoundRealizationException(defValue: Any?) :
        Exception("not found realization for $defValue")

}





