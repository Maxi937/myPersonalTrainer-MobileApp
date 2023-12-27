package org.fitness.myfitnesstrainer.ui.composables.MyFitnessInputFields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun MyFitnessMultiLineTextInput(text: MutableState<String>, placeholder: String, maxChars: Int = 260) {
    TextField(
        value = text.value,
        onValueChange = { text.value = filterMaxChars(maxChars, it) },
        placeholder = { Text(text = placeholder, color = Color.Gray) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = false,
        minLines = 2,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        prefix = {
            Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "A multiline text box"
        ) },
    )
}