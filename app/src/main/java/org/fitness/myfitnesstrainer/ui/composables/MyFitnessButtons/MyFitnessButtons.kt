package org.fitness.myfitnesstrainer.ui.composables.MyFitnessButtons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MyFitnessPrimaryButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(modifier = modifier, contentPadding = PaddingValues(20.dp), onClick = { onClick() }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)) {
        Text(text)
    }
}
@Composable
fun MyFitnessTertiaryButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(modifier = modifier, contentPadding = PaddingValues(20.dp), onClick = { onClick() }, colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)) {
        Text(text)
    }
}
