package io.eden.rickpedia.model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.eden.rickpedia.data.RickpediaRepository
import io.eden.rickpedia.data.entities.CharacterEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterDetailsViewModel(
    private val repository: RickpediaRepository
) : BaseViewModel() {

    private val _charactersState = mutableStateOf(SingleCharacterState())
    val characterState: State<SingleCharacterState> = _charactersState

    override fun resetState() {
        _charactersState.value = _charactersState.value.copy(
            element = null,
            starredEpisodes = null,
            loadingMain = true,
            loadingEpisodes = true,
            error = null,
        )
    }

    fun loadCertainCharacterData(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getCharacterById(id)
            _charactersState.value = _charactersState.value.copy(
                element = response,
                loadingMain = false,
                error = null,
            )
        }
    }

    fun loadStarredEpisodes(listOfIds: List<Int>){
        viewModelScope.launch(Dispatchers.IO) {
            _charactersState.value = _charactersState.value.copy(
                loadingEpisodes = false,
                starredEpisodes = listOfIds.map { Pair(it, repository.getEpisodeNameById(it)) }
            )
        }
    }

    data class SingleCharacterState(
        val element: CharacterEntity? = null,
        val starredEpisodes: List<Pair<Int, Pair<String, String>>>? = null,
        val loadingMain: Boolean = true,
        val loadingEpisodes: Boolean = true,
        val error: String? = null,
    )
}