package io.eden.rickpedia.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.eden.rickpedia.ui.theme.GreenBorder

@Composable
fun Table(items: List<Pair<String, String>>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, GreenBorder))
    ) {
        Column {
            //keys
            items.forEach {
                Text(
                    text = it.first,
                    modifier = Modifier
                        .padding(8.dp)
                )

            }
        }
        Column {
            //values
            items.forEach {
                Text(
                    text = it.second,
                    modifier = Modifier.padding(8.dp),
                )
            }
        }
    }
}

@Composable
fun ListItemFragment(
    content: @Composable () -> Unit
) {
    content()
}

@Composable
fun MainCategoryItemFragment(value: String, id: Int, onClicked: (Int) -> Unit) {
    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClicked(id) }
                .border(BorderStroke(1.dp, GreenBorder), shape = CircleShape)
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                text = value,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}