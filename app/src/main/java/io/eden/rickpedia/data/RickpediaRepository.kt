package io.eden.rickpedia.data

import android.util.Log
import io.eden.rickpedia.data.dao.BaseDao
import io.eden.rickpedia.data.entities.CharacterEntity
import io.eden.rickpedia.data.entities.DatabaseEntity
import io.eden.rickpedia.data.entities.EpisodesEntity
import io.eden.rickpedia.data.entities.LocationEntity
import io.eden.rickpedia.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class RickpediaRepository(
    private val apiService: ApiService,
    private val database: RickpediaDatabase,
) {
    private val TAG = "Rickpedia.Repository"

    fun getCharacter(id: Int): CharacterEntity {
        return database.characterDao().getCharacterById(id)
    }

    suspend fun getAllCharacters(): List<CharacterEntity> {
        Log.i(TAG, "Fetching characters")
        val localData = database.characterDao().getAllCharacters()
        return if (localData.isEmpty()) {
            Log.i(TAG, "DATABASE IS EMPTY")
            val results = apiService.getAllCharacters().results
            makeAsyncWrite(results)
            results
        } else {
            Log.i(TAG, "DATABASE IS NOT EMPTY")
            localData
        }
    }

     suspend fun getAllLocations(): List<LocationEntity> {
        Log.i(TAG, "Fetching characters")
        val localData = database.locationDao().getAllLocations()
        return if (localData.isEmpty()) {
            Log.i(TAG, "DATABASE IS EMPTY")
            val results = apiService.getAllLocations().results
            makeAsyncWrite(results)
            results
        } else {
            Log.i(TAG, "DATABASE IS NOT EMPTY")
            localData
        }
    }

     suspend fun getAllEpisodes(): List<EpisodesEntity> {
        Log.i(TAG, "Fetching characters")
        val localData = database.episodeDao().getAllEpisodes()
        return if (localData.isEmpty()) {
            Log.i(TAG, "DATABASE IS EMPTY")
            val results = apiService.getAllEpisodes().results
            makeAsyncWrite(results)
            results
        } else {
            Log.i(TAG, "DATABASE IS NOT EMPTY")
            localData
        }
    }




    private fun <T: DatabaseEntity> getDao(element: T): BaseDao<T> {
         return when (element) {
            is CharacterEntity -> {
                database.characterDao() as BaseDao<T>
            }

            is LocationEntity -> {
                database.locationDao() as BaseDao<T>
            }

            is EpisodesEntity -> {
                database.episodeDao() as BaseDao<T>
            }

            else -> {
                throw Exception("Something went wrong with casting")
            }
        }
    }

    private suspend fun makeAsyncWrite(elements: List<DatabaseEntity>) {
        withContext(Dispatchers.IO) {
            elements.forEach { element ->
                getDao(element).insert(element)
            }
        }
    }
}