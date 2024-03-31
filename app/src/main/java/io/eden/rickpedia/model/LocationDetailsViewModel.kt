package io.eden.rickpedia.model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.eden.rickpedia.data.RickpediaRepository
import io.eden.rickpedia.data.entities.LocationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationDetailsViewModel(
    private val repository: RickpediaRepository,
): BaseViewModel() {
    private val TAG = "Rickpedia.LocationDetailsViewModel"
    private val _locationState = mutableStateOf(SingleLocationState())
    val locationState: State<SingleLocationState> = _locationState

    override fun resetState() {
        _locationState.value = _locationState.value.copy(
            element = null,
            residents = null,
            loadingMain = true,
            loadingResidents = true,
            error = null,
        )
    }

    fun loadCertainLocation(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getLocationById(id)
            _locationState.value = _locationState.value.copy(
                element = response,
                loadingMain = false,
                error = null,
            )
        }
    }

    fun loadResidents(listOfIds: List<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            _locationState.value = _locationState.value.copy(
                loadingResidents = false,
                residents = listOfIds.map { Pair(it, repository.getCharacterNameById(it)) }
            )
        }
    }

    data class SingleLocationState(
        val element: LocationEntity? = null,
        val residents: List<Pair<Int, String>>? = null,
        val loadingMain: Boolean = true,
        val loadingResidents: Boolean = true,
        val error: String? = null,
    )
}