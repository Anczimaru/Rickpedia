package io.eden.rickpedia.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import io.eden.rickpedia.data.entities.CharacterLocationEntity
import io.eden.rickpedia.data.entities.OriginEntity
import kotlinx.serialization.json.Json

class DatabaseConverters {

    //Character Location Entity <-> JSON String
    @TypeConverter
    fun convertCharacterLocationEntityToJSONString(characterLocationEntity: CharacterLocationEntity): String =
        Gson().toJson(characterLocationEntity)

    @TypeConverter
    fun convertJSONStringToCharacterLocationEntity(jsonString: String): CharacterLocationEntity =
        Gson().fromJson(jsonString, CharacterLocationEntity::class.java)

    //Origin Entity <-> JSON String
    @TypeConverter
    fun convertOriginEntityToJSONString(originEntity: OriginEntity): String =
        Gson().toJson(originEntity)

    @TypeConverter
    fun convertJSONStringToOriginEntity(jsonString: String): OriginEntity =
        Gson().fromJson(jsonString, OriginEntity::class.java)

    //List string convert
    @TypeConverter
    fun convertStringListToJSONString(listOfString: List<String>): String =
        Gson().toJson(listOfString)

    @TypeConverter
    fun convertJSONStringToStringList(jsonString: String): List<String> =
        Json.decodeFromString<List<String>>(jsonString)
}