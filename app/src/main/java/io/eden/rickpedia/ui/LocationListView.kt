package io.eden.rickpedia.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import io.eden.rickpedia.model.MainViewModel
import io.eden.rickpedia.navigation.Screen
import io.eden.rickpedia.ui.theme.GreenBorder

@Composable
fun LocationListView(
    navController: NavController,
    viewModel: MainViewModel,
) {
    val onLocationClicked: (Int) -> Unit = { id ->
        navController.navigate(Screen.LocationDetails.route + "/${id}")
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
        title = Screen.LocationListScreen.title,
        triggerSearch = { query -> viewModel.triggerLocationSearch(query) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when {
                viewModel.multiLocationsState.value.loadingFirstBatch -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        items(viewModel.multiLocationsState.value.list) { location ->
                            ListItemFragment {
                                MainCategoryItemFragment(
                                    value = location.name,
                                    id = location.id,
                                    onClicked = {
                                        onLocationClicked(location.id)
                                    })
                            }
                        }
                    }
                }
            }
        }
    }
}