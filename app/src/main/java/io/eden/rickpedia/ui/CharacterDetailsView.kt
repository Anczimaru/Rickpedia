package io.eden.rickpedia.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
fun CharacterDetailsView(
    navController: NavController,
    viewModel: MainViewModel,
    characterId: Int,
) {
    DrawerView(navController = navController, title = Screen.CharacterDetails.title) {
        // Load data
        val characterEntity = viewModel.loadCertainCharacterData(characterId)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when {
                viewModel.characterState.value.loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                else -> {
                    val element = viewModel.characterState.value.element!!
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        //Add Image
                        Image(
                            painter = rememberAsyncImagePainter(model = element.image),
                            contentDescription = "Image",
                            modifier = Modifier
                                .wrapContentSize()
                                .aspectRatio(1f),
                        )
                        CharacterRow(key = "Name: ", value = element.name)
                        CharacterRow(key = "Last known location:", value = element.location.name)
                        CharacterRow(key = "Status: ", value = element.status)
                        CharacterRow(key = "Species: ", value = element.species)
                        CharacterRow(key = "Gender: ", value = element.gender)
                        CharacterRow(key = "Origin: ", value = element.origin.name)
                        // TODO add episodes to character
                    }
                }
            }
        }
    }
}

// TODO improve UI
@Composable
fun CharacterRow(key: String, value: String) {
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = key,
            modifier = Modifier.padding(8.dp),
        )
        Text(
            text = value,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterDetailsPreview() {
    CharacterDetailsView(rememberNavController(), viewModel(), characterId = 0)
}