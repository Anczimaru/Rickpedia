package io.eden.rickpedia

import android.app.Application
import io.eden.rickpedia.data.RickpediaDatabase
import io.eden.rickpedia.data.RickpediaRepository
import io.eden.rickpedia.model.CharacterDetailsViewModel
import io.eden.rickpedia.model.EpisodeDetailsViewModel
import io.eden.rickpedia.model.MainViewModel
import io.eden.rickpedia.model.SearchViewModel
import io.eden.rickpedia.network.apiService

class RickpediaApp:Application() {

    lateinit var database: RickpediaDatabase
    lateinit var repository: RickpediaRepository
    lateinit var mainViewModel: MainViewModel
    lateinit var episodeDetailsViewModel: EpisodeDetailsViewModel
    lateinit var characterDetailsViewModel: CharacterDetailsViewModel
    lateinit var searchViewModel: SearchViewModel

    override fun onCreate() {
        super.onCreate()

        // Initialize the database
        database = RickpediaDatabase.getInstance(this)

        // Initialize the repository
        repository = RickpediaRepository(apiService, database)

        // Initialize the MainViewModel
        mainViewModel = MainViewModel(repository)
        episodeDetailsViewModel = EpisodeDetailsViewModel(repository)
        characterDetailsViewModel = CharacterDetailsViewModel(repository)
        searchViewModel = SearchViewModel(repository)
    }
}