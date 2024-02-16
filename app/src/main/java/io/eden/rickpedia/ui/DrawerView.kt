@file:OptIn(ExperimentalMaterial3Api::class)

package io.eden.rickpedia.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.eden.rickpedia.navigation.Screen
import io.eden.rickpedia.navigation.drawerScreens
import kotlinx.coroutines.launch

@Composable
fun DrawerView(
    navController: NavController,
    title: String = "Placeholder",
    content: @Composable (PaddingValues) -> Unit,
) {
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
                AppBarView(title = title, onDrawerMenuIconClicked = {
                    scope.launch {
                        drawerState.open()
                    }
                })
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
) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = {
                onDrawerMenuIconClicked()
            }) {
                Icon(imageVector = Icons.Default.List, contentDescription = "Open Menu")
            }
        },
    )
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
        .padding(8.dp)
        .background(background)
        .clickable {
            onDrawerItemClicked(item)
        }) {
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = item.dTittle,
            modifier = Modifier.padding(end = 8.dp, top = 4.dp)
        )
        Text(text = item.dTittle, style = MaterialTheme.typography.headlineMedium)
    }
}


@Preview
@Composable
fun DrawerMenuPreview() {
    DrawerMenu("", {})
}
