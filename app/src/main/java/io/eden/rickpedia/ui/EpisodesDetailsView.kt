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
import io.eden.rickpedia.model.EpisodeDetailsViewModel
import io.eden.rickpedia.navigation.Screen
import io.eden.rickpedia.ui.theme.GreenBorder

@Composable
fun EpisodesDetailsView(
    navController: NavController,
    viewModel: EpisodeDetailsViewModel,
    episodesId: Int,
) {
    val onCharacterClicked: (Int) -> Unit = { id ->
        navController.navigate(Screen.CharacterDetails.route + "/$id")
    }
    /* Clean-up */
    DisposableEffect(viewModel) {
        onDispose {
            viewModel.resetState()
        }
    }

    /* UI */
    viewModel.loadCertainEpisodeData(episodesId)
    when {
        viewModel.episodeState.value.loadingMain -> {
            Box {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        else -> {
            val element = viewModel.episodeState.value.element!!
            DrawerView(navController = navController, title = element.episode) { pd ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(pd)
                ) {
                    element.charactersIds?.let { ids -> viewModel.loadStarringCharacters(ids) }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                    ) {
                        //Add Image
                        Box(modifier = Modifier.padding(8.dp)) {
                            Table(items = element.generateTableContent())
                        }
                        when {
                            viewModel.episodeState.value.loadingStarring -> {
                                CircularProgressIndicator()
                            }

                            else -> {
                                StarringComposable(
                                    listOfCharacters = viewModel.episodeState.value.starring!!,
                                    onCharacterClicked = { id ->
                                        onCharacterClicked(id)
                                    })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StarringComposable(
    listOfCharacters: List<Pair<Int, String>>, onCharacterClicked: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = "Starring",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(8.dp)
                .align(
                    Alignment.CenterHorizontally
                )
        )
        Spacer(modifier = Modifier.padding(8.dp))
    }
    LazyColumn(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        items(listOfCharacters) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
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