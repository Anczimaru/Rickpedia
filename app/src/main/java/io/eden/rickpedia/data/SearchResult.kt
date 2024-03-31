package io.eden.rickpedia.data

import android.graphics.drawable.Drawable
import io.eden.rickpedia.R

data class SearchResult(
    val image: Int = R.drawable.placeholder,
    val id: Int,
    val name: String,
)