package io.eden.rickpedia.model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.eden.rickpedia.data.RickpediaRepository
import io.eden.rickpedia.data.entities.EpisodesEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EpisodeDetailsViewModel(
    private val repository: RickpediaRepository,
) : ViewModel() {

    private val TAG = "Rickpedia.EpisodeDetailsViewModel"
    private val _episodeState = mutableStateOf(SingleEpisodeState())
    val episodeState: State<SingleEpisodeState> = _episodeState

    fun resetState() {
        _episodeState.value = _episodeState.value.copy(
            element = null,
            starring = null,
            loadingMain = true,
            loadingStarring = true,
            error = null,
        )
    }

    fun loadCertainEpisodeData(id: Int) {
        Log.i(TAG, "Loading some data")
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getEpisodeById(id)
            _episodeState.value = _episodeState.value.copy(
                element = response,
                loadingMain = false,
                error = null,
            )
            Log.i(TAG, "STH")
        }
    }

    // Characters loading
    fun loadStarringCharacters(listOfIds: List<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            _episodeState.value = _episodeState.value.copy(
                loadingStarring = false,
                starring = listOfIds.map { Pair(it, repository.getCharacterNameById(it)) }
            )
        }
    }

    data class SingleEpisodeState(
        val element: EpisodesEntity? = null,
        val starring: List<Pair<Int, String>>? = null,
        val loadingMain: Boolean = true,
        val loadingStarring: Boolean = true,
        val error: String? = null,
    )
}