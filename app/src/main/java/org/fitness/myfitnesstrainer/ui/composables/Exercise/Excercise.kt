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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.fitness.myfitnesstrainer.data.local.models.ExerciseModel
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessText.MyFitnessH3Subscript1

@Composable
fun ExerciseItem(exercise: ExerciseModel, padding: PaddingValues = PaddingValues(1.dp), action: @Composable() (() -> Unit)? = null) {
    Card(
        modifier = Modifier.fillMaxWidth().then(Modifier.padding(padding)),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(modifier = Modifier.padding(10.dp)) {
            Icon(
                imageVector = getExerciseIcon(exercise.bodyPart),
                contentDescription = "Localized description", Modifier.size(70.dp)
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
fun getExerciseIcon(bodyPart: String): ImageVector {
    return when (bodyPart.lowercase()) {
        "arms" -> Icons.Filled.Star
        else -> Icons.Filled.AccountCircle
    }
}