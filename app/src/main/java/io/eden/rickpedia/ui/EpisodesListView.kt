package io.eden.rickpedia.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.eden.rickpedia.data.entities.EpisodesEntity
import io.eden.rickpedia.model.MainViewModel
import io.eden.rickpedia.navigation.Screen

@Composable
fun EpisodesListView(
    navController: NavController,
    viewModel: MainViewModel,
) {
    /* Clean-up */
    DisposableEffect(viewModel) {
        onDispose {
            viewModel.resetState()
        }
    }

    /* UI */
    DrawerView(
        navController = navController,
        title = Screen.EpisodesListScreen.title,
        triggerSearch = { query -> viewModel.triggerEpisodeSearch(query) },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when {
                viewModel.multiEpisodesState.value.loadingFirstBatch -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        items(viewModel.multiEpisodesState.value.list) { episode ->
                            ListItemFragment {
                                EpisodeEntry(
                                    episode,
                                    onEpisodeClicked = {
                                        navController.navigate(Screen.EpisodeDetails.route + "/${it}")
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
fun EpisodeEntry(episode: EpisodesEntity, onEpisodeClicked: (Int) -> Unit) {
    MainCategoryItemFragment(
        value = "${episode.episode}  ${episode.name}",
        id = episode.id,
        onClicked = onEpisodeClicked
    )
}