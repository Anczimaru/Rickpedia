package io.eden.rickpedia.data

import android.util.Log
import io.eden.rickpedia.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext


class RickpediaRepository(
    private val apiService: ApiService,
    private val database: RickpediaDatabase,
) {
    private val TAG = "Rickpedia.Repository"

    suspend fun getAllCharacters(): List<CharacterEntity>{
        Log.i(TAG, "Fetching characters")
        val localData = database.characterDao().getAllCharacters()
        return if (localData.isEmpty()) {
            Log.i(TAG, "DATABASE IS EMPTY")
            val results = apiService.getAllCharacters().results
            writeCharactersToDatabaseAsync(results)
            results
        } else {
            Log.i(TAG, "DATABASE IS NOT EMPTY")
            localData
        }

    }

    private suspend fun writeCharactersToDatabaseAsync(listOfCharacters: List<CharacterEntity>){
        withContext(Dispatchers.IO){
            listOfCharacters.forEach {
                database.characterDao().addCharacter(it)
            }
        }
    }

    fun isCharactersTableEmpty(): Boolean {
        return database.characterDao().getAllCharacters().isEmpty()
    }

//    suspend fun fetchAllCharacters() = coroutineScope {
//        try {
//            apiService.getAllCharacters().results.forEach{
//                addCharacterToDb(it)
//            }
//        } catch (exception: Exception) {
//            Log.i(TAG, "Something went wrong with API call")
//        }
//    }

    fun getAllEpisodes(): Flow<List<EpisodesEntity>> {
        return database.episodeDao().getAllEpisodes()
    }


    fun getAllLocations(): Flow<List<LocationEntity>> {
        return database.locationDao().getAllLocations()
    }

    suspend fun addCharacterToDb(characterEntity: CharacterEntity) {
        Log.i(TAG, "Adding new character with id: ${characterEntity.id}")
        database.characterDao().addCharacter(characterEntity)
    }
}