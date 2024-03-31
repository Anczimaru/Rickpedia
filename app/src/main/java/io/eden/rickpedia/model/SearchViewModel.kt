package io.eden.rickpedia.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.eden.rickpedia.data.RickpediaRepository
import io.eden.rickpedia.data.entities.DatabaseEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: RickpediaRepository,
) : BaseViewModel() {

    private val _searchState = mutableStateOf(SearchState())
    val searchState: State<SearchState> = _searchState
    var currentQuery: MutableState<String> = mutableStateOf("")

    override fun resetState() {
        _searchState.clearSearchState()
    }

    fun searchForEntity() {
        if (currentQuery.value.isBlank()) {
            _searchState.clearSearchState()
        } else {
            _searchState.value = _searchState.value.copy(
                loadingResults = true
            )
            viewModelScope.launch(Dispatchers.IO) {
                _searchState.value = _searchState.value.copy(
                    loadingResults = false,
                    results = repository.getCharactersByName(currentQuery.value)
                            + repository.getEpisodesByString(currentQuery.value)
                            + repository.getLocationsByString(currentQuery.value),
                )
            }
        }
    }

    data class SearchState(
        val loadingResults: Boolean = false,
        val results: List<DatabaseEntity>? = null,
    )

    fun MutableState<SearchState>.clearSearchState() {
        this.value = this.value.copy(
            results = emptyList(),
            loadingResults = false,
        )
    }
}