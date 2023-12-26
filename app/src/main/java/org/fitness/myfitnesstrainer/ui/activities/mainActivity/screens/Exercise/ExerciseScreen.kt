package org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Exercise

import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import org.fitness.myfitnesstrainer.data.local.models.ExerciseModel
import org.fitness.myfitnesstrainer.data.local.models.Profile
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessText.MyFitnessH3
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessText.MyFitnessH3Subscript1
import org.fitness.myfitnesstrainer.ui.composables.Screen.Screen
import org.fitness.myfitnesstrainer.ui.preview.ProfilePreviewParameterProvider
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme
import timber.log.Timber
import kotlin.math.roundToInt

@Composable
fun ExerciseScreen(exercises: List<ExerciseModel>) {
    Screen {
        for (exercise in exercises) {
            ExerciseItem(exercise)
        }
    }
}

@Composable
fun ExerciseItem(exercise: ExerciseModel) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var width = 0

    Card(
        modifier = Modifier
            .padding(0.dp, 4.dp)
            .fillMaxWidth()
            .onGloballyPositioned {
                width = it.size.width
            }
            .offset { IntOffset(offsetX.roundToInt(), 0) }
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    offsetX += delta
                },
                onDragStopped = {
                    Timber.i("Offset: ${it.dp}")
                    Timber.i("Width: ${width}")
                    offsetX = 0F
                }
            )

        ,
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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExercisePreview(@PreviewParameter(ProfilePreviewParameterProvider::class) profile: Profile) {
    MyFitnessTrainerTheme {
        ExerciseScreen(exercises = profile.exercises)
    }
}


@Composable
fun getExerciseIcon(bodyPart: String): ImageVector {
    return when (bodyPart.lowercase()) {
        "arms" -> Icons.Filled.Star
        else -> Icons.Filled.AccountCircle
    }
}

