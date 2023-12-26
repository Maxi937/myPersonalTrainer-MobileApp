package org.fitness.myfitnesstrainer.ui.composables.MyFitnessInputFields

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyFitnessIntInputField(value: MutableState<String>, maxChars: Int = 2, modifier: Modifier = Modifier) {
    TextField(
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        value = value.value,
        onValueChange = {
            onIntValueChange(value.value, it) { newValue ->
                value.value = filterMaxChars(maxChars, newValue)
            }
        },
        modifier = modifier,
        singleLine = true
    )
}

fun onIntValueChange(oldValue: String, newValue: String, setValue: (setValue: String) -> Unit) {
    if (newValue.isEmpty()) {
        setValue(newValue)
    } else {
        newValue
        when (newValue.toIntOrNull()) {
            null -> setValue(oldValue) //old value
            else -> setValue(newValue)   //new value
        }
    }
}



