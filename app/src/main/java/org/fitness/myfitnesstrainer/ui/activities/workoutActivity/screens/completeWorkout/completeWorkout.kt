package org.fitness.myfitnesstrainer.ui.activities.workoutActivity.screens.completeWorkout

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.fitness.myfitnesstrainer.data.local.models.ExerciseModel
import org.fitness.myfitnesstrainer.data.local.models.Profile
import org.fitness.myfitnesstrainer.data.local.models.WorkoutModel
import org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Workout.Exercises
import org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Workout.checkHistoryForLatest
import org.fitness.myfitnesstrainer.ui.activities.workoutActivity.WorkoutActivity
import org.fitness.myfitnesstrainer.ui.composables.Exercise.Sets
import org.fitness.myfitnesstrainer.ui.composables.Exercise.getExerciseIcon
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessButtons.MyFitnessPrimaryButton
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessCard.MyFitnessCard
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessText.MyFitnessH2
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessText.MyFitnessH3
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessText.MyFitnessH3Subscript1
import org.fitness.myfitnesstrainer.ui.composables.Screen.Screen
import org.fitness.myfitnesstrainer.ui.preview.ProfilePreviewParameterProvider
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme

@Composable
fun CompleteWorkout(oldWorkout: WorkoutModel, newWorkout: WorkoutModel) {
    val activity = (LocalContext.current as? WorkoutActivity)

    Screen {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.7f)
                .padding(0.dp, 65.dp),
            verticalArrangement = Arrangement.Center
        ) {
            WorkoutResult(newWorkout)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxHeight(1f)
            .padding(10.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        MyFitnessPrimaryButton(text = "Finish Workout", modifier = Modifier.fillMaxWidth()) {
            activity?.myFitnessRepository?.completeWorkout(oldWorkout, newWorkout.exercises)
            activity?.finish()
        }
    }
}

@Composable
fun WorkoutResult(workout: WorkoutModel) {
    MyFitnessCard {
        MyFitnessH3Subscript1(title = workout.name, text = checkHistoryForLatest(workout.history))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Localized description",
                tint = MaterialTheme.colorScheme.inversePrimary
            )
            Spacer(modifier = Modifier.size(5.dp))
            Text("${workout.getVolume()} Kg", fontSize = 18.sp)

        }
        ExercisesResult(workout.exercises)
    }
}

@Composable
fun ExercisesResult(exercises: List<ExerciseModel>) {
    for (exercise in exercises) {
        Column(modifier = Modifier.padding(0.dp, 10.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp)
            ) {
                Text("${exercise.name}", fontSize = 18.sp)
                Row(
                    modifier = Modifier.fillMaxWidth(), Arrangement.End, Alignment.CenterVertically
                ) {
                    Text("${exercise.getVolume()} Kg", textAlign = TextAlign.Right)
                    Spacer(modifier = Modifier.size(5.dp))
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Localized description",
                        tint = MaterialTheme.colorScheme.inversePrimary
                    )
                }
            }
            Sets(exercise.sets)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CompleteWorkoutPreview(@PreviewParameter(ProfilePreviewParameterProvider::class) profile: Profile) {
    MyFitnessTrainerTheme {
        CompleteWorkout(profile.workouts[0], profile.workouts[0])
    }
}