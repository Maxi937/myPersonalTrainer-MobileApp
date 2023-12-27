package org.fitness.myfitnesstrainer.ui.composables.Exercise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.fitness.myfitnesstrainer.data.local.models.ExerciseModel
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessText.MyFitnessH3Subscript1

@Composable
fun ExerciseItem(exercise: ExerciseModel, padding: PaddingValues = PaddingValues(1.dp), action: @Composable() (() -> Unit)? = null) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(Modifier.padding(padding)),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(modifier = Modifier.padding(10.dp)) {
            Icon(
                imageVector = getExerciseIcon(exercise.bodyPart),
                contentDescription = "Localized description", Modifier.size(70.dp),
                tint = MaterialTheme.colorScheme.inversePrimary
            )
            Spacer(Modifier.size(5.dp))
            MyFitnessH3Subscript1(title = exercise.name, text = exercise.bodyPart)
            if (action != null) {
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    action()
                }
            }

        }
    }
}

@Composable
fun Sets(sets: List<List<Float>>) {
    for (set in sets) {
        SetsItem(set = set)
        Spacer(Modifier.size(4.dp))
    }
}

@Composable
fun SetsItem(set: List<Float>) {
    Row {
        Text("${set.size}", textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.size(5.dp))
        Text("\uD835\uDE6D", textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.size(5.dp))
        Text("${set[0]} Kg")
    }

}

@Composable
fun getExerciseIcon(bodyPart: String): ImageVector {
    return when (bodyPart.lowercase()) {
        "arm" -> Icons.Filled.Build
        "legs" -> Icons.Filled.Place
        "back" -> Icons.Filled.Star
        "shoulder" -> Icons.Filled.AddCircle
        else -> Icons.Filled.AccountCircle
    }
}