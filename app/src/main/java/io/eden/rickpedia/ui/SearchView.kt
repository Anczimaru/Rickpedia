package io.eden.rickpedia.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.eden.rickpedia.data.SearchResult
import io.eden.rickpedia.data.entities.CharacterEntity
import io.eden.rickpedia.data.entities.EpisodesEntity
import io.eden.rickpedia.data.entities.LocationEntity
import io.eden.rickpedia.model.SearchViewModel
import io.eden.rickpedia.navigation.Screen
import io.eden.rickpedia.ui.theme.GreenBorder

@Composable
fun SearchView(
    navController: NavController,
    viewModel: SearchViewModel,
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
        title = "Search"
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            SearchBarAndButton(
                value = viewModel.currentQuery,
                onButtonClicked = { viewModel.searchForEntity() },
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally)
            ) {
                when {
                    viewModel.searchState.value.loadingResults -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    }

                    else -> {
                        ResultsComposable(viewModel = viewModel, navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBarAndButton(
    value: MutableState<String>,
    onButtonClicked: () -> Unit,
) {
    Column {
        Box() {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                value = value.value,
                onValueChange = {
                    value.value = it
                    onButtonClicked()
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(GreenBorder, shape = CircleShape)
                .border(BorderStroke(1.dp, GreenBorder), shape = CircleShape),
        ) {
            Text(text = "Search",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        onButtonClicked()
                    })
        }
    }
}

@Composable
fun ResultsComposable(viewModel: SearchViewModel, navController: NavController) {
    LazyColumn {
        if (viewModel.searchState.value.results.isNullOrEmpty()) {
            // Do nothing
        } else {
            items(viewModel.searchState.value.results!!) { entity ->
                ResultsEntry(result = entity.convertToSearchResult(), onClicked = { id ->
                    when (entity) {
                        is CharacterEntity -> {
                            navController.navigate(Screen.CharacterDetails.route + "/$id")
                        }

                        is LocationEntity -> {
                            navController.navigate(Screen.LocationDetails.route + "/$id")
                        }

                        is EpisodesEntity -> {
                            navController.navigate(Screen.EpisodeDetails.route + "/$id")
                        }
                    }
                })
            }
        }
    }
}

@Composable
fun ResultsEntry(result: SearchResult, onClicked: (Int) -> Unit) {
    Row(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .border(BorderStroke(1.dp, GreenBorder), shape = CircleShape)
        .clickable {
            onClicked(result.id)
        }) {
        Row(
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = result.image),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterVertically)
            )
            Text(
                text = result.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(start = 20.dp, top = 8.dp, bottom = 8.dp, end = 20.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    SearchBarAndButton(mutableStateOf(""), {})
}