package io.eden.rickpedia.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.eden.rickpedia.data.SearchResult
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Entity(tableName = "location-table")
@Serializable
@Parcelize
data class LocationEntity(
    //Fields received from API
    @PrimaryKey
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String,
    //Fields that are generated
    val residentsIds: List<Int>
) : Parcelable, DatabaseEntity() {

    override fun generateUpdate(): DatabaseEntity {
        return this.copy(
            residentsIds = residents.trimToGetIds().filter{ it != 0},
        )
    }

    override fun generateTableContent(): List<Pair<String, String>> {
        TODO("Not yet implemented")
    }

    override fun convertToSearchResult(): SearchResult {
        TODO("Not yet implemented")
    }
}

val DummyLocation: LocationEntity
    get() = Json.decodeFromString<LocationEntity>(dummyLocationString)

val dummyLocationString = "{\n" +
        "  \"id\": 3,\n" +
        "  \"name\": \"Citadel of Ricks\",\n" +
        "  \"type\": \"Space station\",\n" +
        "  \"dimension\": \"unknown\",\n" +
        "  \"residents\": [\n" +
        "    \"https://rickandmortyapi.com/api/character/8\",\n" +
        "    \"https://rickandmortyapi.com/api/character/14\"\n" +
        "  ],\n" +
        "  \"url\": \"https://rickandmortyapi.com/api/location/3\",\n" +
        "  \"created\": \"2017-11-10T13:08:13.191Z\"\n" +
        "}"