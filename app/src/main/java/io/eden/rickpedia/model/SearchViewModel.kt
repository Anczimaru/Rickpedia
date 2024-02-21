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
) : ViewModel() {

    private val _searchState = mutableStateOf(SearchState())
    val searchState: State<SearchState> = _searchState
    var currentQuery: MutableState<String> = mutableStateOf("")

    fun searchForCharacter() {
        _searchState.value = _searchState.value.copy(
            loadingResults = true
        )
        viewModelScope.launch(Dispatchers.IO) {
            _searchState.value = _searchState.value.copy(
                loadingResults = false,
                results = repository.getCharactersByName(currentQuery.value),
            )
        }
    }

    data class SearchState(
        val loadingResults: Boolean = true,
        val results: List<DatabaseEntity>? = null,
    )
}