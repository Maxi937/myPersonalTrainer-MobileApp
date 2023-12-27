package org.fitness.myfitnesstrainer.ui.composables.Search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessInputFields.filterMaxChars

@Composable
fun Search(
    searchVisible: MutableState<Boolean>,
    search: MutableState<String>,
    searchBoxText: String = ""
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .zIndex(100f)
            .padding(5.dp)
    ) {

        AnimatedVisibility(visible = searchVisible.value) {
            TextField(
                value = search.value,
                trailingIcon = {
                    IconButton(onClick = { search.value = "" }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Localized description",
                            tint = MaterialTheme.colorScheme.inversePrimary
                        )

                    }

                },
                onValueChange = { search.value = filterMaxChars(10, it) },
                placeholder = { Text(text = searchBoxText, color = Color.Gray) },
                modifier = Modifier.width(220.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
            )
        }

        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
            FloatingActionButton(
                onClick = {
                    searchVisible.value = !searchVisible.value
                },
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Localized description",
                    tint = MaterialTheme.colorScheme.inversePrimary
                )

            }

        }

    }
}