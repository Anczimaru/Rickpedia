package io.eden.rickpedia.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import io.eden.rickpedia.R
import io.eden.rickpedia.data.RickpediaDatabase
import io.eden.rickpedia.data.RickpediaRepository
import io.eden.rickpedia.data.entities.CharacterEntity
import io.eden.rickpedia.data.entities.DummyCharacter
import io.eden.rickpedia.model.MainViewModel
import io.eden.rickpedia.navigation.Screen
import io.eden.rickpedia.network.apiService
import io.eden.rickpedia.ui.theme.GreenBorder

@Composable
fun CharacterListView(
    navController: NavController,
    viewModel: MainViewModel,
) {
    val onCharacterClicked: (Int) -> Unit = { id ->
        navController.navigate(Screen.CharacterDetails.route + "/${id}")
    }
    val getEpisodeNameById: (Int) -> String = { id ->
        viewModel.getEpisodeNameById(id)
    }
    /* Clean-up */
    DisposableEffect(viewModel) {
        onDispose {
            viewModel.resetState()
        }
    }

    /* UI */
    DrawerView(
        navController = navController,
        title = Screen.CharacterListScreen.title,
        triggerSearch = { query -> viewModel.triggerCharacterSearch(query) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                var isGridEnabled by remember { mutableStateOf(false) }
                CharacterViewToggle(
                    isChecked = isGridEnabled,
                    onCheckedChange = { isGridEnabled = it })
                when {
                    viewModel.multiCharacterState.value.loadingFirstBatch -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    }

                    else -> {
                        when (isGridEnabled) {
                            true -> {
                                LazyVerticalGrid(columns = GridCells.Fixed(2))
                                {
                                    items(viewModel.multiCharacterState.value.list) { character ->
                                        CharacterThumbnailsGrid(
                                            characterEntity = character,
                                            onCharacterClicked = onCharacterClicked
                                        )
                                    }
                                }
                            }

                            false -> {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxSize()
                                ) {
                                    items(viewModel.multiCharacterState.value.list) { character ->
                                        CharacterDetailedCard(
                                            characterEntity = character,
                                            onCharacterClicked = onCharacterClicked,
                                            getEpisodeNameById = getEpisodeNameById,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterViewToggle(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth().padding(8.dp).border(BorderStroke(1.dp, GreenBorder))
    ) {

        IconButton(
            onClick = { onCheckedChange(true) },
            modifier = Modifier
                .fillMaxWidth(0.48f)
                .background(if (isChecked) Color.DarkGray else MaterialTheme.colorScheme.background),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.character_icon),
                contentDescription = null // You can provide appropriate content description
            )
        }
        Spacer(modifier = Modifier.width(16.dp).border(BorderStroke(1.dp, GreenBorder)))
        IconButton(
            onClick = { onCheckedChange(false) },
            modifier = Modifier
                .fillMaxWidth()
                .background(if (!isChecked) Color.DarkGray else MaterialTheme.colorScheme.background)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.list_icon),
                contentDescription = null // You can provide appropriate content description
            )
        }
    }
}

//TODO convert to grid
@Composable
fun CharacterThumbnailsGrid(
    characterEntity: CharacterEntity,
    onCharacterClicked: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .clickable {
                onCharacterClicked(characterEntity.id)
            },
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.border(BorderStroke(1.dp, GreenBorder))
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = characterEntity.image),
                contentDescription = "image",
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = characterEntity.name,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.padding(8.dp))
        }
        Text(text = "STH")
    }
}

@Composable
fun CharacterDetailedCard(
    characterEntity: CharacterEntity,
    onCharacterClicked: (Int) -> Unit,
    getEpisodeNameById: (Int) -> String,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .clickable {
                onCharacterClicked(characterEntity.id)
            }
            .border(BorderStroke(1.dp, GreenBorder))
    ) {
        Column(
            modifier = Modifier.border(BorderStroke(1.dp, GreenBorder))
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = characterEntity.image),
                contentDescription = "image",
                modifier = Modifier.size(200.dp)
            )
        }
        Column(Modifier.fillMaxWidth()) {
            Text(
                text = characterEntity.name,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.headlineSmall
            )
            Row(
                modifier = Modifier
            ) {
                /* Character details */
                Spacer(modifier = Modifier.padding(8.dp))
                ColoredDot(
                    color = (if (characterEntity.status.equals("Alive")) Color.Green else if (characterEntity.status.equals(
                            "Dead"
                        )
                    ) Color.Red else Color.DarkGray),
                    dotSize = 8.dp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "${characterEntity.status} - ${characterEntity.species}"
                )
            }
            Text(
                text = "Last known location:",
                modifier = Modifier.padding(8.dp),
                color = Color.LightGray
            )
            Text(text = characterEntity.location.name, modifier = Modifier.padding(start = 8.dp))
            Text(text = "First seen in:", modifier = Modifier.padding(8.dp), color = Color.LightGray)
            Text(text = getEpisodeNameById(characterEntity.episodeIds.first() ?: 0), modifier = Modifier.padding(start = 8.dp) )
        }
    }
}

@Preview
@Composable
fun CharacterViewTogglePreview() {
    CharacterViewToggle(true, {})
}

@Preview
@Composable
fun CharacterDetailedCardPreview() {
    CharacterDetailedCard(
        characterEntity = DummyCharacter,
        onCharacterClicked = {},
        getEpisodeNameById = { "string" })
}

@Preview
@Composable
fun CharacterGridPreview() {
    CharacterThumbnailsGrid(characterEntity = DummyCharacter, onCharacterClicked = {})
}

@Preview
@Composable
fun CharacterListViewPreview() {
    val database = RickpediaDatabase.getInstance(context = LocalContext.current)
    val repository = RickpediaRepository(apiService, database)
    CharacterListView(rememberNavController(), MainViewModel(repository = repository))
}