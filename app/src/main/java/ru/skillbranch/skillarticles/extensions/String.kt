package ru.skillbranch.skillarticles.extensions

import java.util.*

fun String?.indexesOf(substr: String, ignoreCase: Boolean = true): List<Int> {
    if (this.isNullOrEmpty() || substr.isEmpty()) return emptyList()
    val matchResults =
        if (ignoreCase) Regex(substr.toLowerCase(Locale.ROOT)).findAll(this.toLowerCase(Locale.ROOT))
        else Regex(substr).findAll(this)
    return matchResults.map { it.range.first }.toList()
}