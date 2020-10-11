package ru.skillbranch.skillarticles.extensions

fun List<Pair<Int, Int>>.groupByBounds(bounds: List<Pair<Int, Int>>): List<MutableList<Pair<Int, Int>>> {
    val result: MutableList<MutableList<Pair<Int, Int>>> = mutableListOf()
    bounds.forEach { bound ->
        val current: MutableList<Pair<Int, Int>> = mutableListOf()
        loop@ for (searchResult in this) {
            when {
                searchResult.second <= bound.first || searchResult.first >= bound.second -> continue@loop
                searchResult.first <= bound.first && searchResult.second > bound.first && searchResult.second <= bound.second
                -> current.add(bound.first to searchResult.second)
                bound.first <= searchResult.first && searchResult.second <= bound.second -> current.add(
                    searchResult.first to searchResult.second
                )
                bound.first <= searchResult.first && searchResult.first < bound.second && bound.second <= searchResult.second ->
                    current.add(searchResult.first to bound.second)
                searchResult.first <= bound.first && bound.second <= searchResult.second ->
                    current.add(bound.first to bound.second)
                else -> continue@loop
            }
        }
        result.add(current)
    }
    return result
}