package io.eden.rickpedia.model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.eden.rickpedia.data.CharacterEntity
import io.eden.rickpedia.data.RickpediaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel(
    private val repository: RickpediaRepository
) : ViewModel() {

    private val TAG = "Rickpedia.MainViewModel"
    private val _charactersState = mutableStateOf(CharacterState())
    val characterState: State<CharacterState> = _charactersState

    init {
        loadAllCharactersData()
    }

    fun loadAllCharactersData() {
        Log.i(TAG, "Loading characters data")
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getAllCharacters()
            _charactersState.value = _charactersState.value.copy(
                list = response,
                loading = false,
                error = null,
            )
            Log.i(TAG, "STH")
        }
    }

    data class CharacterState(
        val loading: Boolean = true,
        val list: List<CharacterEntity> = emptyList(),
        val error: String? = null,
    )
}

