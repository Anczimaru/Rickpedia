package io.eden.rickpedia.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Entity(tableName = "location-table")
@Serializable
@Parcelize
data class LocationEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String,
) : Parcelable, DatabaseEntity()

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