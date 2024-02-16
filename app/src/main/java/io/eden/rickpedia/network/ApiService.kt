package io.eden.rickpedia.network

import io.eden.rickpedia.data.CharacterEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
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
    suspend fun getAllCharacters(): CharacterListResponse

    @GET("/character/{id}")
    suspend fun getSingleCharacter(@Path("id") id:String): CharacterEntity
}


suspend fun <T : Any> safeApiCall(apiCall: suspend () -> Response<T>): Flow<Result<T>> {
    return flow {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                emit(Result.Success(response.body()!!))
            } else {
                emit(Result.Error(Exception("API call failed with code ${response.code()}")))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}