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


//TODO REFACTOR into smaller classes!

class MainViewModel(
    private val repository: RickpediaRepository
) : ViewModel() {

    private val TAG = "Rickpedia.MainViewModel"
    private val _multiLocationsState = mutableStateOf(MultiLocationsState())
    private val _multiEpisodesState = mutableStateOf(MultiEpisodesState())
    private val _multiCharactersState = mutableStateOf(MultiCharacterState())
    private val _locationState = mutableStateOf(SingleLocationState())
    val multiCharacterState: State<MultiCharacterState> = _multiCharactersState
    val multiLocationsState: State<MultiLocationsState> = _multiLocationsState
    val multiEpisodesState: State<MultiEpisodesState> = _multiEpisodesState
    val locationState: State<SingleLocationState> = _locationState

    init {
        loadAllCharactersData()
        loadAllLocationData()
        loadAllEpisodesData()
    }

    fun loadAllCharactersData() {
        Log.i(TAG, "Loading characters data")
        viewModelScope.launch(Dispatchers.IO) {
            var response = repository.getAllCharacters()
            _multiCharactersState.value = _multiCharactersState.value.copy(
                list = response,
                loadingFirstBatch = false,
                error = null,
            )
            Log.i(TAG, "STH")
            //TODO add some form of pagination here
            if (response.count() < 21) {
                repository.downloadRemainingCharacters()
                response = repository.getAllCharacters()
                _multiCharactersState.value = _multiCharactersState.value.copy(
                    loadingAll = false,
                    list = response
                )
            }
        }
    }

    fun loadAllLocationData() {
        Log.i(TAG, "Loading characters data")
        viewModelScope.launch(Dispatchers.IO) {
            var response = repository.getAllLocations()
            _multiLocationsState.value = _multiLocationsState.value.copy(
                list = response,
                loadingFirstBatch = false,
                error = null,
            )
            Log.i(TAG, "STH")
            if (response.count() < 21) {
                repository.downloadRemainingLocations()
                response = repository.getAllLocations()
                _multiLocationsState.value = _multiLocationsState.value.copy(
                    loadingAll = false,
                    list = response
                )
            }
        }
    }

    fun loadAllEpisodesData() {
        Log.i(TAG, "Loading characters data")
        viewModelScope.launch(Dispatchers.IO) {
            var response = repository.getAllEpisodes()
            _multiEpisodesState.value = _multiEpisodesState.value.copy(
                list = response,
                loadingFirstBatch = false,
                error = null,
            )
            Log.i(TAG, "STH")
            if (response.count() < 21) {
                repository.downloadRemainingEpisodes()
                response = repository.getAllEpisodes()
                _multiEpisodesState.value = _multiEpisodesState.value.copy(
                    loadingAll = false,
                    list = response
                )
            }
        }
    }

    data class MultiCharacterState(
        val list: List<CharacterEntity> = emptyList(),
        val loadingFirstBatch: Boolean = true,
        val loadingAll: Boolean = true,
        val error: String? = null,
    )

    data class MultiLocationsState(
        val list: List<LocationEntity> = emptyList(),
        val loadingFirstBatch: Boolean = true,
        val loadingAll: Boolean = true,
        val error: String? = null,
    )

    data class MultiEpisodesState(
        val list: List<EpisodesEntity> = emptyList(),
        val loadingFirstBatch: Boolean = true,
        val loadingAll: Boolean = true,
        val error: String? = null,
    )

    data class SingleLocationState(
        val element: LocationEntity? = null,
        val loading: Boolean = true,
        val error: String? = null,
    )
}

