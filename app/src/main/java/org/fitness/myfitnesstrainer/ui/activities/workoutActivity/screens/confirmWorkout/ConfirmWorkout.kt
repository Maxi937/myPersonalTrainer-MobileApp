package org.fitness.myfitnesstrainer.ui.activities.workoutActivity.screens.confirmWorkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import org.fitness.myfitnesstrainer.data.local.models.Profile
import org.fitness.myfitnesstrainer.data.local.models.WorkoutModel
import org.fitness.myfitnesstrainer.repository.MyFitnessRepository
import org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Workout.Exercises
import org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Workout.checkHistoryForLatest
import org.fitness.myfitnesstrainer.ui.activities.workoutActivity.WorkoutActivity
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessButtons.MyFitnessPrimaryButton
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessCard.MyFitnessCard
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessText.MyFitnessH3Subscript1
import org.fitness.myfitnesstrainer.ui.composables.Screen.Screen
import org.fitness.myfitnesstrainer.ui.preview.ProfilePreviewParameterProvider
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme


@Composable
fun ConfirmWorkout(workout: WorkoutModel, navigation: () -> Unit) {
    Screen {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.7f)
                .padding(0.dp, 65.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Workout(workout)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxHeight(1f)
            .padding(10.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        MyFitnessPrimaryButton(text = "Start Workout", modifier = Modifier.fillMaxWidth()) {
            navigation()
        }
    }
}

@Composable
fun Cancel(workout: WorkoutModel) {
    MyFitnessRepository.deleteWorkout(workout)
    val activity = (LocalContext.current as? WorkoutActivity)
    activity?.finish()
}

@Composable
fun Workout(workout: WorkoutModel) {
    var cancel by remember { mutableStateOf(false) }

    if (cancel) {
        Cancel(workout)
    }

    MyFitnessCard {
        Row {
            MyFitnessH3Subscript1(
                title = workout.name,
                text = checkHistoryForLatest(workout.history)
            )
            Row(modifier = Modifier.fillMaxWidth(), Arrangement.End) {
                IconButton(onClick = {
                    cancel = true
                }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Localized description"
                    )
                }
            }
        }
        Exercises(workout.exercises)
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutPreview(@PreviewParameter(ProfilePreviewParameterProvider::class) profile: Profile) {
    MyFitnessTrainerTheme {
        ConfirmWorkout(workout = profile.workouts[0], {})
    }
}