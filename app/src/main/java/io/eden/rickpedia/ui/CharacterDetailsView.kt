package io.eden.rickpedia.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import io.eden.rickpedia.model.CharacterDetailsViewModel
import io.eden.rickpedia.navigation.Screen
import io.eden.rickpedia.ui.theme.GreenBorder

@Composable
fun CharacterDetailsView(
    navController: NavController,
    viewModel: CharacterDetailsViewModel,
    characterId: Int,
) {
    //Load Character Data
    viewModel.loadCertainCharacterData(characterId)

    when {
        //Provide placeholder for time when app is loading
        viewModel.characterState.value.loadingMain -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        // TODO Optional add error handling here

        //Character data loaded so generate content for the view
        else -> {
            //Assign character to variable for future use
            val element = viewModel.characterState.value.element!!
            DrawerView(navController = navController, title = element.name) {
                val scrollState = rememberScrollState()
                /* Load data */
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    viewModel.loadStarredEpisodes(element.episodeIds
                        .filter { it != null } //get rid of potential errors in parsing episodes
                        .map { it!! }
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = element.image),
                            contentDescription = "Image",
                            modifier = Modifier
                                .wrapContentSize()
                                .aspectRatio(1f)
                                .border(BorderStroke(1.dp, GreenBorder)),
                        )
                        /* Character details */
                        Table(items = element.generateTableContent())
                        when {
                            viewModel.characterState.value.loadingEpisodes -> {
                                CircularProgressIndicator()
                            }

                            else -> {
                                EpisodesStarredFragment(
                                    listOfEpisodes = viewModel.characterState.value.starredEpisodes!!,
                                    onEpisodeClicked = {
                                        navController.navigate(Screen.EpisodeDetails.route + "/$it")
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
fun EpisodesStarredFragment(
    listOfEpisodes: List<Pair<Int, Pair<String, String>>>,
    onEpisodeClicked: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = "Starred in:",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(8.dp)
                .align(
                    Alignment.CenterHorizontally
                )
        )
    }
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        listOfEpisodes.forEach {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(1.dp, GreenBorder), shape = CircleShape),
            ) {
                Text(
                    text = it.second.first + "  " + it.second.second,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable {
                            onEpisodeClicked(it.first)
                        })
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterDetailsPreview() {
    CharacterDetailsView(rememberNavController(), viewModel(), characterId = 0)
}