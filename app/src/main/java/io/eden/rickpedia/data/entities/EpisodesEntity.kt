package io.eden.rickpedia.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.eden.rickpedia.data.SearchResult
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Entity(tableName = "episodes-table")
@Serializable
@Parcelize
data class EpisodesEntity(
    //Fields received from API
    @PrimaryKey
    val id: Int,
    val name: String,
    val air_date: String?,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String,
    //Fields that are generated
    val charactersIds: List<Int>?
) : Parcelable, DatabaseEntity() {

    override fun generateUpdate(): DatabaseEntity {
        return this.copy(
            charactersIds = characters.trimToGetIds().filter { it != 0 },
        )
    }

    override fun generateTableContent(): List<Pair<String, String>> {
        return listOf(
            Pair("Name: ", name),
            Pair("Air Date: ", air_date ?: "Unknown"),
            Pair("Episode:", episode),
            // TODO Consider adding characters
        )
    }

    override fun convertToSearchResult(): SearchResult {
        TODO("Not yet implemented")
    }
}

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