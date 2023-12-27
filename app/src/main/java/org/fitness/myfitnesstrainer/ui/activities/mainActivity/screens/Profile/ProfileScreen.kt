package org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Profile


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.fitness.myfitnesstrainer.data.local.models.Profile
import org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Workout.Workout
import org.fitness.myfitnesstrainer.ui.composables.Exercise.ExerciseItem
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessText.MyFitnessH3
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessText.MyFitnessH3Subscript1
import org.fitness.myfitnesstrainer.ui.composables.Screen.Screen
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessText.MyFitnessSubscript1
import org.fitness.myfitnesstrainer.ui.preview.ProfilePreviewParameterProvider
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme


@Composable
fun ProfileScreen(profile: Profile) {
    val viewModel: ProfileViewModel = viewModel()

    val mostRecentWorkout = viewModel.getMostRecentWorkout()
    val favouriteExercise = viewModel.getFavouriteExercise()

    Screen {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Localized description", Modifier.size(70.dp),
                tint = MaterialTheme.colorScheme.inversePrimary
            )
            Spacer(modifier = Modifier.size(10.dp))
            Column {
                Text(text = profile.userDetails.fname)
                Spacer(modifier = Modifier.size(3.dp))
                MyFitnessSubscript1(text = "${viewModel.getNumberOfWorkoutsCompleted()}")
            }

        }
        if (mostRecentWorkout != null) {
            Spacer(Modifier.size(50.dp))
            MyFitnessH3(title = "Most Recent")
            Workout(workout = mostRecentWorkout, showMenuActions = false)
        }
        if (favouriteExercise.first != null && favouriteExercise.second != null) {
            Spacer(Modifier.size(50.dp))
            MyFitnessH3Subscript1(title = "Favourite Exercise", text = favouriteExercise.first)
            ExerciseItem(exercise = favouriteExercise.second!!)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview(@PreviewParameter(ProfilePreviewParameterProvider::class) profile: Profile) {
    MyFitnessTrainerTheme {
        ProfileScreen(profile = profile)
    }
}