package io.eden.rickpedia.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import io.eden.rickpedia.model.MainViewModel
import io.eden.rickpedia.navigation.Screen

@Composable
fun CharacterListView(
    navController: NavController,
    viewModel: MainViewModel,
) {
    DrawerView(navController = navController, title = Screen.CharacterListScreen.title) {
        Box(modifier = Modifier.fillMaxSize().padding(it)) {
            when {
                viewModel.multiCharacterState.value.loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(viewModel.multiCharacterState.value.list) { character ->
                            Row(modifier = Modifier.fillMaxWidth().clickable {
                                navController.navigate(Screen.CharacterDetails.route + "/${character.id}")
                            }) {
                                Image(
                                    painter = rememberAsyncImagePainter(model = character.image),
                                    contentDescription = "image",
                                    modifier = Modifier.size(200.dp)
                                )
                                Spacer(modifier = Modifier.padding(8.dp))
                                Text(text = character.name)
                            }
                        }
                    }
                }
            }

        }

    }
}

@Preview
@Composable
fun CharacterListPreview() {
    CharacterListView(rememberNavController(), viewModel())
}