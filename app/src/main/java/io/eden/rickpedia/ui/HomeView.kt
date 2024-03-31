package io.eden.rickpedia.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.eden.rickpedia.model.MainViewModel
import io.eden.rickpedia.navigation.drawerScreens
import io.eden.rickpedia.ui.theme.GreenBorder

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
            drawerScreens.drop(1).forEach { item ->
                Row(modifier = Modifier
                    .padding(30.dp)
                    .clickable {
                        navController.navigate(item.dRoute)
                    }
                    .fillMaxWidth()
                    .border(BorderStroke(1.dp, GreenBorder), shape = CircleShape),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.dTittle,
                            modifier = Modifier.padding(end = 8.dp, top = 4.dp)
                        )
                        Text(
                            text = item.dTittle,
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
//            Box(modifier = Modifier
//                .padding(50.dp)
//                .clickable {
//                    navController.navigate(Screen.LocationListScreen.route)
//                }) {
//                Text("Locations List", style = MaterialTheme.typography.headlineMedium)
//            }
//
//            Box(modifier = Modifier
//                .padding(50.dp)
//                .clickable {
//                    navController.navigate(Screen.EpisodesListScreen.route)
//                }) {
//                Text("Episodes List", style = MaterialTheme.typography.headlineMedium)
//            }
//
//            Box(modifier = Modifier
//                .padding(50.dp)
//                .clickable {
//                    navController.navigate(Screen.SearchScreen.route)
//                }) {
//                Text("Search", style = MaterialTheme.typography.headlineMedium)
//            }
//
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreenView(rememberNavController(), viewModel())
}