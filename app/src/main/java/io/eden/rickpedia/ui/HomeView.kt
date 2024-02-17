package io.eden.rickpedia.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.eden.rickpedia.model.MainViewModel
import io.eden.rickpedia.navigation.Screen

@Composable
fun HomeScreenView(
    navController: NavController,
    viewModel: MainViewModel,
) {
    DrawerView(navController = navController, title = "Home") {
        // TODO make some grid here to be able to select what you are looking for
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        ) {
            Box(modifier = Modifier
                .padding(50.dp)
                .clickable {
                    navController.navigate(Screen.CharacterListScreen.route)
                }) {
                Text("Characters List", style = MaterialTheme.typography.headlineMedium)
            }

            Box(modifier = Modifier
                .padding(50.dp)
                .clickable {
                    navController.navigate(Screen.LocationListScreen.route)
                }) {
                Text("Locations List", style = MaterialTheme.typography.headlineMedium)
            }

            Box(modifier = Modifier
                .padding(50.dp)
                .clickable {
                    navController.navigate(Screen.EpisodesListScreen.route)
                }) {
                Text("Episodes List", style = MaterialTheme.typography.headlineMedium)
            }

            Box(modifier = Modifier
                .padding(50.dp)
                .clickable {
                    navController.navigate(Screen.EpisodesListScreen.route)
                }) {
                Text("Search", style = MaterialTheme.typography.headlineMedium)
            }


        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreenView(rememberNavController(), viewModel())
}