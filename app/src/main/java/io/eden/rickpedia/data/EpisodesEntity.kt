package io.eden.rickpedia.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Entity(tableName = "episodes-table")
@Serializable
@Parcelize
data class EpisodesEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    @SerialName("air_date")
    val airDate: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String,
) : Parcelable

val DummyEpisode: EpisodesEntity
    get() = Json.decodeFromString<EpisodesEntity>(dummyEpisodeString)

val dummyEpisodeString = "{\n" +
        "  \"id\": 28,\n" +
        "  \"name\": \"The Ricklantis Mixup\",\n" +
        "  \"air_date\": \"September 10, 2017\",\n" +
        "  \"episode\": \"S03E07\",\n" +
        "  \"characters\": [\n" +
        "    \"https://rickandmortyapi.com/api/character/1\",\n" +
        "    \"https://rickandmortyapi.com/api/character/2\"\n" +
        "  ],\n" +
        "  \"url\": \"https://rickandmortyapi.com/api/episode/28\",\n" +
        "  \"created\": \"2017-11-10T12:56:36.618Z\"\n" +
        "}"