package io.eden.rickpedia.navigation

import androidx.annotation.DrawableRes
import io.eden.rickpedia.R

sealed class Screen(val title:String, val route: String) {
    object HomeScreen : Screen("Rickpedia","home_screen")
    object CharacterListScreen : Screen("Character List","character_list")
    object EpisodesListScreen : Screen("Episodes List","episodes_list")
    object LocationListScreen : Screen("Location list","location_list")
    object CharacterDetails : Screen("Character Details", "character_details")
    object EpisodeDetails: Screen("Episode Details", "episodes_details")
    object LocationDetails: Screen("Location Details", "location_details")
    object SearchScreen: Screen("Search", "search")


    sealed class DrawerScreen(val dTittle: String, val dRoute: String, @DrawableRes val icon:Int) : Screen(dTittle, dRoute){
        object Home: DrawerScreen("Home", "home_screen", R.drawable.home_icon)
        object Characters: DrawerScreen( "Character List", "character_list", R.drawable.characters)
        object Locations: DrawerScreen("Locations List", "location_list", R.drawable.location_icon)
        object Episodes: DrawerScreen("Episodes List", "episodes_list", R.drawable.episodes_icon)
        object Search: DrawerScreen("Search", "search", R.drawable.search_icon)
    }
}

val drawerScreens = listOf<Screen.DrawerScreen>(
    Screen.DrawerScreen.Home,
    Screen.DrawerScreen.Characters,
    Screen.DrawerScreen.Locations,
    Screen.DrawerScreen.Episodes,
    Screen.DrawerScreen.Search,
)