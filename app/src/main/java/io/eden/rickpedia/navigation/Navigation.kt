package io.eden.rickpedia.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.eden.rickpedia.model.CharacterDetailsViewModel
import io.eden.rickpedia.model.EpisodeDetailsViewModel
import io.eden.rickpedia.model.MainViewModel
import io.eden.rickpedia.model.SearchViewModel
import io.eden.rickpedia.ui.CharacterDetailsView
import io.eden.rickpedia.ui.CharacterListView
import io.eden.rickpedia.ui.EpisodesDetailsView
import io.eden.rickpedia.ui.EpisodesListView
import io.eden.rickpedia.ui.HomeScreenView
import io.eden.rickpedia.ui.LocationListView
import io.eden.rickpedia.ui.SearchView

@Composable
fun Navigation(
    navController: NavHostController,
    viewModel: MainViewModel,
    episodeDetailsViewModel: EpisodeDetailsViewModel,
    characterDetailsViewModel: CharacterDetailsViewModel,
    searchViewModel: SearchViewModel,
) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(Screen.HomeScreen.route) {
            HomeScreenView(navController = navController, viewModel = viewModel)
        }
        composable(Screen.SearchScreen.route) {
            SearchView(navController = navController, viewModel = searchViewModel)
        }
        composable(Screen.CharacterListScreen.route) {
            CharacterListView(navController = navController, viewModel = viewModel)
        }
        composable(Screen.EpisodesListScreen.route) {
            EpisodesListView(navController = navController, viewModel = viewModel)
        }
        composable(Screen.LocationListScreen.route) {
            LocationListView(navController = navController, viewModel = viewModel)
        }
        composable(
            Screen.CharacterDetails.route + "/{id}", arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                })
        ) { entry ->
            val id = if (entry.arguments != null) entry.arguments!!.getInt("id") else 0
            CharacterDetailsView(
                navController = navController,
                viewModel = characterDetailsViewModel,
                characterId = id
            )
        }
        composable(
            Screen.EpisodeDetails.route + "/{id}", arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                })
        ) { entry ->
            val id = if (entry.arguments != null) entry.arguments!!.getInt("id") else 0
            EpisodesDetailsView(
                navController = navController,
                viewModel = episodeDetailsViewModel,
                episodesId = id
            )
        }
    }
}