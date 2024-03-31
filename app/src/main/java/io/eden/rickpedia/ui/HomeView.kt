package io.eden.rickpedia.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.eden.rickpedia.R
import io.eden.rickpedia.model.MainViewModel
import io.eden.rickpedia.navigation.drawerScreens
import io.eden.rickpedia.ui.theme.GreenBorder

@Composable
fun HomeScreenView(
    navController: NavController,
    viewModel: MainViewModel,
) {
    DrawerView(navController = navController, title = "Rickpedia") {
        // TODO make some grid here to be able to select what you are looking for
        Box(modifier = Modifier.fillMaxSize()) {
            /* Background Image */
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null, // Content description is null since it's a background image
                modifier = Modifier.fillMaxSize(), // Fill the entire size of the Box
//                colorFilter = ColorFilter.tint(color = Color.White) // Apply any desired color filter
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
            ) {
                /* Display list of views without Home view reference */
                drawerScreens.drop(1).forEach { item ->
                    Row(modifier = Modifier
                        .padding(30.dp)
                        .clickable {
                            navController.navigate(item.dRoute)
                        }
                        .fillMaxWidth()
                        .border(BorderStroke(1.dp, GreenBorder), shape = CircleShape),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.dTittle,
                                modifier = Modifier.padding(top = 2.dp, end = 8.dp).align(Alignment.CenterVertically)
                            )
                            Text(
                                text = item.dTittle,
                                style = MaterialTheme.typography.headlineMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreenView(rememberNavController(), viewModel())
}