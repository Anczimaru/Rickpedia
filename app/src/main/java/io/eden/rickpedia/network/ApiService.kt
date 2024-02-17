package io.eden.rickpedia.network

import io.eden.rickpedia.data.entities.CharacterEntity
import io.eden.rickpedia.data.entities.EpisodesEntity
import io.eden.rickpedia.data.entities.LocationEntity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private val retrofit = Retrofit.Builder().baseUrl("https://rickandmortyapi.com/api/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService = retrofit.create(ApiService::class.java)

interface ApiService {

//Characters
    @GET("character")
    suspend fun getAllCharacters(): Response<List<CharacterEntity>>

    @GET("/character/{id}")
    suspend fun getSingleCharacter(@Path("id") id: String): CharacterEntity

    @GET("character/")
    suspend fun getCharacterPage(@Query("page") page: String): Response<List<CharacterEntity>>

//Locations
    @GET("location")
    suspend fun getAllLocations(): Response<List<LocationEntity>>

    @GET("location/")
    suspend fun getLocationPage(@Query("page") page: String): Response<List<LocationEntity>>

//Episodes
    @GET("episode")
    suspend fun getAllEpisodes(): Response<List<EpisodesEntity>>

    @GET("episode/")
    suspend fun getEpisodePage(@Query("page") page: String): Response<List<EpisodesEntity>>

}
