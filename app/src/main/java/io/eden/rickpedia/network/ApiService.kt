package io.eden.rickpedia.network

import io.eden.rickpedia.data.entities.CharacterEntity
import io.eden.rickpedia.data.entities.EpisodesEntity
import io.eden.rickpedia.data.entities.LocationEntity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private val retrofit = Retrofit.Builder().baseUrl("https://rickandmortyapi.com/api/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService = retrofit.create(ApiService::class.java)

interface ApiService {

    @GET("character")
    suspend fun getAllCharacters(): Response<List<CharacterEntity>>

    @GET("location")
    suspend fun getAllLocations(): Response<List<LocationEntity>>

    @GET("episode")
    suspend fun getAllEpisodes(): Response<List<EpisodesEntity>>

    @GET("/character/{id}")
    suspend fun getSingleCharacter(@Path("id") id:String): CharacterEntity
}
