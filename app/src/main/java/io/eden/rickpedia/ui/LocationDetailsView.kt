package io.eden.rickpedia.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.eden.rickpedia.model.LocationDetailsViewModel
import io.eden.rickpedia.navigation.Screen
import io.eden.rickpedia.ui.theme.GreenBorder

@Composable
fun LocationDetailsView(
    navController: NavController,
    viewModel: LocationDetailsViewModel,
    locationId: Int,
) {
    val onCharacterClicked: (Int) -> Unit = { id ->
        navController.navigate(Screen.CharacterDetails.route + "/$id")
    }

    DisposableEffect(viewModel) {
        onDispose {
            viewModel.resetState()
        }
    }

    viewModel.loadCertainLocation(locationId)
    when {
        viewModel.locationState.value.loadingMain -> {
            Box {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        else -> {
            val element = viewModel.locationState.value.element!!
            DrawerView(navController = navController, title = element.name) { pd ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(pd)
                ) {
                    element.residentsIds.let { ids -> viewModel.loadResidents(ids) }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                    ) {
                        Box(modifier = Modifier.padding(8.dp)) {
                            Table(items = element.generateTableContent())
                        }
                        when {
                            viewModel.locationState.value.loadingResidents -> {
                                CircularProgressIndicator()
                            }

                            else -> {
                                ResidentsComposable(
                                    listOfResidents = viewModel.locationState.value.residents!!,
                                    onCharacterClicked = { id ->
                                        onCharacterClicked(id)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ResidentsComposable(
    listOfResidents: List<Pair<Int, String>>,
    onCharacterClicked: (Int) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = "Residents",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.padding(8.dp))
    }
    LazyColumn(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        items(listOfResidents) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(BorderStroke(1.dp, GreenBorder), shape = CircleShape)
            ) {
                Text(text = it.second,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(start= 16.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .clickable {
                            onCharacterClicked(it.first)
                        })
            }
        }
    }
}