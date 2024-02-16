package io.eden.rickpedia.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import io.eden.rickpedia.data.DummyCharacter
import io.eden.rickpedia.navigation.Screen

@Composable
fun CharacterDetailsView(
    navController: NavController,
    characterId: Int
) {
    DrawerView(navController = navController, title = Screen.CharacterDetails.title) {
        // Load data
        val characterEntity = DummyCharacter

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Add Image
            Image(
                painter = rememberAsyncImagePainter(model = characterEntity.image),
                contentDescription = "Image",
                modifier = Modifier
                    .wrapContentSize()
                    .aspectRatio(1f),
            )
            Row {
                Text(text = "Name", modifier = Modifier.padding(8.dp))
                Text(text = characterEntity.name, modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterDetailsPreview() {
    CharacterDetailsView(rememberNavController(), characterId = 0)
}