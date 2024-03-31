package io.eden.rickpedia.network

data class InfoClass(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String?,
)

data class Response<T>(
    val info: InfoClass,
    val results: T
)