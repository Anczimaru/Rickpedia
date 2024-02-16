package io.eden.rickpedia.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.eden.rickpedia.model.MainViewModel
import io.eden.rickpedia.ui.CharacterDetailsView
import io.eden.rickpedia.ui.CharacterListView
import io.eden.rickpedia.ui.EpisodesListView
import io.eden.rickpedia.ui.HomeScreenView
import io.eden.rickpedia.ui.LocationListView

@Composable
fun Navigation(
    navController: NavHostController,
    viewModel: MainViewModel,
) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(Screen.HomeScreen.route) {
            HomeScreenView(navController = navController)
        }
        composable(Screen.CharacterListScreen.route){
            CharacterListView(navController = navController, viewModel)
        }
        composable(Screen.EpisodesListScreen.route){
            EpisodesListView(navController = navController)
        }
        composable(Screen.LocationListScreen.route){
            LocationListView(navController = navController)
        }
        composable(Screen.CharacterDetails.route + "/{id}", arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
                defaultValue = 0
                nullable = false
            })){entry ->
            val id = if (entry.arguments != null) entry.arguments!!.getInt("id") else 0
            CharacterDetailsView(navController, characterId = id)
        }
    }
}