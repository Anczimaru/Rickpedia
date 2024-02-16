package io.eden.rickpedia.network

import io.eden.rickpedia.data.CharacterEntity
import retrofit2.Response


data class InfoClass(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String?,
)

data class CharacterListResponse(
    val info: InfoClass,
    val results: List<CharacterEntity>
)