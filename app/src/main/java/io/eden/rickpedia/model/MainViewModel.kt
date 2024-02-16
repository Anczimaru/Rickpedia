package io.eden.rickpedia.model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.eden.rickpedia.data.RickpediaRepository
import io.eden.rickpedia.data.entities.CharacterEntity
import io.eden.rickpedia.data.entities.EpisodesEntity
import io.eden.rickpedia.data.entities.LocationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel(
    private val repository: RickpediaRepository
) : ViewModel() {

    private val TAG = "Rickpedia.MainViewModel"
    private val _multiLocationsState = mutableStateOf(MultiLocationsState())
    private val _multiEpisodesState = mutableStateOf(MultiEpisodesState())
    private val _multiCharactersState = mutableStateOf(MultiCharacterState())
    private val _charactersState = mutableStateOf(SingleCharacterState())
    val multiCharacterState: State<MultiCharacterState> = _multiCharactersState
    val multiLocationsState: State<MultiLocationsState> = _multiLocationsState
    val multiEpisodesState: State<MultiEpisodesState> = _multiEpisodesState
    val characterState: State<SingleCharacterState> = _charactersState

    init {
        loadAllCharactersData()
        loadAllLocationData()
        loadAllEpisodesData()
    }

    fun loadCertainCharacterData(id: Int) {
        Log.i(TAG, "Loading some data")
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getCharacter(id)
            _charactersState.value = _charactersState.value.copy(
                element = response,
                loading = false,
                error = null,
            )
            Log.i(TAG, "STH")
        }
    }

    fun loadAllCharactersData() {
        Log.i(TAG, "Loading characters data")
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getAllCharacters()
            _multiCharactersState.value = _multiCharactersState.value.copy(
                list = response,
                loading = false,
                error = null,
            )
            Log.i(TAG, "STH")
        }
    }

    fun loadAllLocationData() {
        Log.i(TAG, "Loading characters data")
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getAllLocations()
            _multiLocationsState.value = _multiLocationsState.value.copy(
                list = response,
                loading = false,
                error = null,
            )
            Log.i(TAG, "STH")
        }
    }

    fun loadAllEpisodesData() {
        Log.i(TAG, "Loading characters data")
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getAllEpisodes()
            _multiEpisodesState.value = _multiEpisodesState.value.copy(
                list = response,
                loading = false,
                error = null,
            )
            Log.i(TAG, "STH")
        }
    }

    data class MultiCharacterState(
        val loading: Boolean = true,
        val list: List<CharacterEntity> = emptyList(),
        val error: String? = null,
    )

    data class MultiLocationsState(
        val loading: Boolean = true,
        val list: List<LocationEntity> = emptyList(),
        val error: String? = null,
    )

    data class MultiEpisodesState(
        val loading: Boolean = true,
        val list: List<EpisodesEntity> = emptyList(),
        val error: String? = null,
    )

    data class SingleCharacterState(
        val loading: Boolean = true,
        val element: CharacterEntity? = null,
        val error: String? = null,
    )
}

