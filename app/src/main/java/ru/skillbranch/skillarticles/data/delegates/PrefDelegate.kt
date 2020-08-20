package ru.skillbranch.skillarticles.data.delegates

import ru.skillbranch.skillarticles.data.local.PrefManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PrefDelegate<T>(private val defaultValue: T) : ReadWriteProperty<PrefManager, T?> {

    override fun getValue(thisRef: PrefManager, property: KProperty<*>): T? {
        val key = property.name
        return thisRef.get(key, defaultValue)
    }

    override fun setValue(thisRef: PrefManager, property: KProperty<*>, value: T?) {
        val key = property.name
        thisRef.put(key, value)
    }
}





