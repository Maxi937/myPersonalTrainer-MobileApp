package org.fitness.myfitnesstrainer.ui.activities.workoutActivity.screens.confirmWorkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import org.fitness.myfitnesstrainer.data.local.models.Profile
import org.fitness.myfitnesstrainer.data.local.models.WorkoutModel
import org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Workout.Exercises
import org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Workout.checkHistoryForLatest
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessButtons.PrimaryButton
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessCard.MyFitnessCard
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessText.MyFitnessH3Subscript1
import org.fitness.myfitnesstrainer.ui.composables.Screen.Screen
import org.fitness.myfitnesstrainer.ui.preview.ProfilePreviewParameterProvider
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme

@Composable
fun ConfirmWorkout(workout: WorkoutModel, navigation: () -> Unit ) {
    Screen {
        Column(modifier = Modifier.fillMaxHeight(0.5f), verticalArrangement = Arrangement.Center) {
            Workout(workout)
        }
        Column(modifier = Modifier.fillMaxHeight(1.0f), verticalArrangement = Arrangement.Bottom) {
                PrimaryButton(text = "Start Workout", modifier = Modifier.fillMaxWidth()) {
                    navigation()
                }
            }
        }

}

@Composable
fun Workout(workout: WorkoutModel) {
    MyFitnessCard {
        MyFitnessH3Subscript1(title = workout.name, text = checkHistoryForLatest(workout.history))
        Exercises(workout.exercises)
    }
}