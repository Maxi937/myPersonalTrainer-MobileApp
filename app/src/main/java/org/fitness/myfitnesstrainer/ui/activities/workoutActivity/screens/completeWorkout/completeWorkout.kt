package org.fitness.myfitnesstrainer.ui.activities.workoutActivity.screens.completeWorkout

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import org.fitness.myfitnesstrainer.data.local.models.Profile
import org.fitness.myfitnesstrainer.data.local.models.WorkoutModel
import org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Workout.Exercises
import org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Workout.checkHistoryForLatest
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessButtons.MyFitnessPrimaryButton
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessCard.MyFitnessCard
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessText.MyFitnessH3Subscript1
import org.fitness.myfitnesstrainer.ui.composables.Screen.Screen
import org.fitness.myfitnesstrainer.ui.preview.ProfilePreviewParameterProvider
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme

@Composable
fun CompleteWorkout(oldWorkout: WorkoutModel, newWorkout: WorkoutModel) {
    val activity = (LocalContext.current as? Activity)

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
            activity?.finish()
        }
    }
}

@Composable
fun WorkoutResult(workout: WorkoutModel) {
    MyFitnessCard {
        MyFitnessH3Subscript1(title = workout.name, text = checkHistoryForLatest(workout.history))
        Exercises(workout.exercises)
    }
}

@Preview(showBackground = true)
@Composable
fun CompleteWorkoutPreview(@PreviewParameter(ProfilePreviewParameterProvider::class) profile: Profile) {
    MyFitnessTrainerTheme {
        CompleteWorkout(profile.workouts[0], profile.workouts[0])
    }
}