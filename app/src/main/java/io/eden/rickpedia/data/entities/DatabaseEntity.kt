package io.eden.rickpedia.data.entities

import android.util.Log
import io.eden.rickpedia.data.SearchResult

abstract class DatabaseEntity {

    abstract fun generateUpdate(): DatabaseEntity

    abstract fun generateTableContent(): List<Pair<String, String>>

    abstract fun convertToSearchResult(): SearchResult
}

fun List<DatabaseEntity>.generateUpdates(): List<DatabaseEntity>{
    return this.map { it.generateUpdate() }
}

fun String.trimToGetId(): Int {
    Log.i("Rickpedia.DatabaseEntity", "Parsing: $this")
    val separatedString = this.split("/")
    return separatedString.get(separatedString.lastIndex).toIntOrNull() ?: 0
}

fun List<String>.trimToGetIds(): List<Int> {
    return this.filter { it.isNotEmpty() }.map { it.trimToGetId() }
}