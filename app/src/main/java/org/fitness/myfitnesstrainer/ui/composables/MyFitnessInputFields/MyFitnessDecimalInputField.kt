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
fun MyFitnessDecimalInputField(
    value: MutableState<String>,
    maxNumbersAfterDecimal: Int = 2,
    maxNumbersBeforeDecimal: Int = 6,
    modifier: Modifier = Modifier
) {
    TextField(
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        value = value.value,
        onValueChange = {
            onDecimalValueChange(value.value, it) { newValue ->
                if (newValue.contains(".")) {
                    val wholeNumbers =
                        filterMaxChars(maxNumbersBeforeDecimal, newValue.substringBefore("."))
                    val decimalNumbers =
                        filterMaxChars(maxNumbersAfterDecimal, newValue.substringAfter("."))
                    value.value = "${wholeNumbers}.${decimalNumbers}"
                } else {
                    value.value = filterMaxChars(maxNumbersBeforeDecimal, newValue)

                }
            }
        },
        modifier = modifier,
        singleLine = true
    )
}

fun onDecimalValueChange(oldValue: String, newValue: String, setValue: (setValue: String) -> Unit) {
    if (newValue.isEmpty()) {
        setValue(newValue)
    } else {
        newValue
        when (newValue.toDoubleOrNull()) {
            null -> setValue(oldValue) //old value
            else -> setValue(newValue)   //new value
        }
    }
}