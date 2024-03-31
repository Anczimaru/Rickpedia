@file:OptIn(ExperimentalMaterial3Api::class)

package io.eden.rickpedia.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.eden.rickpedia.navigation.Screen
import io.eden.rickpedia.navigation.drawerScreens
import kotlinx.coroutines.launch

@Composable
fun DrawerView(
    triggerSearch: (String) -> Unit = {},
    navController: NavController,
    title: String = "Placeholder",
    content: @Composable (PaddingValues) -> Unit,
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colorScheme.background,
        darkIcons = !isSystemInDarkTheme()
    )
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                content = {
                    DrawerMenu(
                        currentTitle = title,
                        onDrawerItemClicked = { screen ->
                            scope.launch {
                                navController.navigate(screen.dRoute)
                                drawerState.close()
                            }
                        }
                    )
                }
            )
        },

        content = {
            Scaffold(topBar = {
                AppBarView(
                    title = title,
                    onDrawerMenuIconClicked = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    triggerSearch = triggerSearch
                )
            }

            ) {
                content(it)
            }
        }
    )
}


@Composable
fun AppBarView(
    title: String,
    onDrawerMenuIconClicked: () -> Unit,
    triggerSearch: (String) -> Unit,
) {
    var isSearchBarVisiable by remember { mutableStateOf(false) }
    var searchQueryValue by remember { mutableStateOf(TextFieldValue()) }
    Column {
        TopAppBar(
            title = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 30.dp)
                ) {
                    Text(
                        text = title,
                        modifier = Modifier.align(Alignment.Center),
                        maxLines = 1,
                        overflow = TextOverflow.Clip
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = {
                    onDrawerMenuIconClicked()
                }) {
                    Icon(imageVector = Icons.Default.List, contentDescription = "Open Menu")
                }
            },
            actions = {
                when {
                    //Add other conditions
                    title.equals("Search") -> {
                        // NO Search icon here
                    }

                    title in listOf(Screen.CharacterListScreen.title, Screen.EpisodesListScreen.title, Screen.LocationListScreen.title) -> {
                        // Add button for switching to grid
                        IconButton(onClick = { isSearchBarVisiable = !isSearchBarVisiable }) {
                            Icon(
                                Icons.Default.Search,
                                "Search in Characters Button",
                                Modifier.size(300.dp)
                            )
                        }
                    }
                }
            }
        )
        if (isSearchBarVisiable) {
            TextField(
                value = searchQueryValue,
                onValueChange = {
                    searchQueryValue = it
                    triggerSearch(searchQueryValue.text)
                },
                label = { Text("Enter text") },
                keyboardActions = KeyboardActions(
                    onDone = {
                        // You can perform actions when the Done button on the keyboard is pressed
                        isSearchBarVisiable = false
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun DrawerMenu(
    currentTitle: String,
    onDrawerItemClicked: (Screen.DrawerScreen) -> Unit,
) {
    LazyColumn() {
        items(drawerScreens) { item ->
            DrawerItem(
                selected = item.dTittle == currentTitle,
                onDrawerItemClicked = onDrawerItemClicked,
                item = item
            )
        }
    }
}

@Composable
fun DrawerItem(
    selected: Boolean,
    onDrawerItemClicked: (Screen.DrawerScreen) -> Unit,
    item: Screen.DrawerScreen
) {
    val background = if (selected) Color.DarkGray else Color.White
    Row(modifier = Modifier
        .padding(top = 8.dp, bottom = 8.dp)
        .fillMaxWidth()
        .background(background)
        .clickable {
            onDrawerItemClicked(item)
        }) {
        Row(Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)) {
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = item.dTittle,
                modifier = Modifier.padding(top = 2.dp, end = 8.dp).align(Alignment.CenterVertically)
            )
            Text(text = item.dTittle, style = MaterialTheme.typography.headlineMedium)
        }
    }
}


@Preview
@Composable
fun DrawerMenuPreview() {
    DrawerMenu("", {})
}
