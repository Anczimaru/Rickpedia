package io.eden.rickpedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import io.eden.rickpedia.navigation.Navigation
import io.eden.rickpedia.ui.theme.RickpediaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rickpediaApp = (application as RickpediaApp)

        setContent {
            val navHostController = rememberNavController()
            RickpediaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(navHostController, rickpediaApp.mainViewModel, rickpediaApp.episodeDetailsViewModel, rickpediaApp.characterDetailsViewModel)
                }
            }
        }
    }
}
