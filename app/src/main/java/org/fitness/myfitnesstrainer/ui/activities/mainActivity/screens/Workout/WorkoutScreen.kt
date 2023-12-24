package org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Workout


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.fitness.myfitnesstrainer.data.local.models.ExerciseModel
import org.fitness.myfitnesstrainer.data.local.models.Profile
import org.fitness.myfitnesstrainer.data.local.models.WorkoutModel
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessCard.MyFitnessCard
import org.fitness.myfitnesstrainer.ui.composables.Screen.Screen
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessText.MyFitnessH3Subscript1
import org.fitness.myfitnesstrainer.ui.preview.ProfilePreviewParameterProvider
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme


@Composable
fun WorkoutScreen(workouts: List<WorkoutModel>) {
    val viewModel: WorkoutViewModel = viewModel()

    Screen {
        for (workout in workouts) {
            Workout(workout)
        }
    }
}

@Composable
fun Workout(workout: WorkoutModel) {
    val viewModel: WorkoutViewModel = viewModel()
    val context = LocalContext.current

    MyFitnessCard(onClick = { viewModel.startWorkout(context, workout) } ) {
        MyFitnessH3Subscript1(title = workout.name, text = checkHistoryForLatest(workout.history))
        Exercises(workout.exercises)
    }
}

fun checkHistoryForLatest(history: List<String>): String {
    return if (history.isEmpty()) {
        "Not Performed Yet"
    } else {
        history[0]
    }
}

@Composable
fun Exercises(exercises: List<ExerciseModel>) {
    for (exercise in exercises) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text("${exercise.sets.size}", textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.size(5.dp))
            Text("\uD835\uDE6D", textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.size(5.dp))
            Text(exercise.name)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun WorkoutPreview(@PreviewParameter(ProfilePreviewParameterProvider::class) profile: Profile) {
    MyFitnessTrainerTheme {
        WorkoutScreen(workouts = profile.workouts)
    }
}