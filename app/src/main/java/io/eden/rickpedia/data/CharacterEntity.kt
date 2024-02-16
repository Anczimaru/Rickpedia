package io.eden.rickpedia.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.*
import kotlinx.serialization.json.Json

@Entity(tableName = "character-table")
@Serializable
@Parcelize
data class CharacterEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: OriginEntity,
    val location: CharacterLocationEntity,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
): Parcelable

@Serializable
@Parcelize
data class OriginEntity(
    val name: String,
    val url: String,
): Parcelable

@Serializable
@Parcelize
data class CharacterLocationEntity(
    val name: String,
    val url: String,
):Parcelable

val DummyCharacter: CharacterEntity
    get() = Json.decodeFromString<CharacterEntity>(dummyCharacter)

const val dummyCharacter = "{\n" +
        "  \"id\": 2,\n" +
        "  \"name\": \"Morty Smith\",\n" +
        "  \"status\": \"Alive\",\n" +
        "  \"species\": \"Human\",\n" +
        "  \"type\": \"\",\n" +
        "  \"gender\": \"Male\",\n" +
        "  \"origin\": {\n" +
        "    \"name\": \"Earth\",\n" +
        "    \"url\": \"https://rickandmortyapi.com/api/location/1\"\n" +
        "  },\n" +
        "  \"location\": {\n" +
        "    \"name\": \"Earth\",\n" +
        "    \"url\": \"https://rickandmortyapi.com/api/location/20\"\n" +
        "  },\n" +
        "  \"image\": \"https://rickandmortyapi.com/api/character/avatar/2.jpeg\",\n" +
        "  \"episode\": [\n" +
        "    \"https://rickandmortyapi.com/api/episode/1\",\n" +
        "    \"https://rickandmortyapi.com/api/episode/2\"" +
        "  ],\n" +
        "  \"url\": \"https://rickandmortyapi.com/api/character/2\",\n" +
        "  \"created\": \"2017-11-04T18:50:21.651Z\"\n" +
        "}"