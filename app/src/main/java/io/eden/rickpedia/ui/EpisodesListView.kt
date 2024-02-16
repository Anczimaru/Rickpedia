package io.eden.rickpedia.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.eden.rickpedia.data.DummyEpisode
import io.eden.rickpedia.data.EpisodesEntity
import io.eden.rickpedia.navigation.Screen

@Composable
fun EpisodesListView(
    navController: NavController
){
    val episodeList = listOf<EpisodesEntity>(DummyEpisode, DummyEpisode, DummyEpisode, DummyEpisode)

    DrawerView(navController = navController, title = Screen.EpisodesListScreen.title) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(episodeList) { episode ->
                Row (modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(text = episode.name)
                }
            }
        }
    }
}