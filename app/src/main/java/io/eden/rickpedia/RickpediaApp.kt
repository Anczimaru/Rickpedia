package io.eden.rickpedia

import android.app.Application
import io.eden.rickpedia.data.RickpediaDatabase
import io.eden.rickpedia.data.RickpediaRepository
import io.eden.rickpedia.model.MainViewModel
import io.eden.rickpedia.network.apiService

class RickpediaApp:Application() {

    lateinit var database: RickpediaDatabase
    lateinit var repository: RickpediaRepository
    lateinit var mainViewModel: MainViewModel

    override fun onCreate() {
        super.onCreate()

        // Initialize the database
        database = RickpediaDatabase.getInstance(this)

        // Initialize the repository
        repository = RickpediaRepository(apiService, database)

        // Initialize the MainViewModel
        mainViewModel = MainViewModel(repository)
    }
}