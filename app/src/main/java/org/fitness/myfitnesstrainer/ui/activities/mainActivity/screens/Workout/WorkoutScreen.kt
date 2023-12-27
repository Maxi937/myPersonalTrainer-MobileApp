package org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Workout


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.fitness.myfitnesstrainer.data.local.models.ExerciseModel
import org.fitness.myfitnesstrainer.data.local.models.History
import org.fitness.myfitnesstrainer.data.local.models.Profile
import org.fitness.myfitnesstrainer.data.local.models.WorkoutModel
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessCard.MyFitnessCard
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessText.MyFitnessH3Subscript1
import org.fitness.myfitnesstrainer.ui.composables.Screen.Screen
import org.fitness.myfitnesstrainer.ui.preview.ProfilePreviewParameterProvider
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme
import timber.log.Timber
import java.time.LocalDateTime.parse
import java.time.format.DateTimeFormatter
import java.util.Date


@Composable
fun WorkoutScreen(workouts: List<WorkoutModel>) {
    val viewModel: WorkoutViewModel = viewModel()

    val showHistory by viewModel.showHistory.observeAsState()

    Screen {
        if (showHistory != null) {
            var history = viewModel.getHistory(showHistory!!)
            ShowHistory(workouts = history)
        } else {
            ShowWorkouts(workouts)
        }
    }
}

@Composable
fun ShowWorkouts(workouts: List<WorkoutModel>) {
    for (workout in workouts) {
        Workout(workout)
    }
}

@Composable
fun ShowHistory(workouts: List<WorkoutModel>) {
    val viewModel: WorkoutViewModel = viewModel()
    Workout(workout = workouts[0], showMenuActions = false)
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = { viewModel.showHistory.postValue(null)}) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "Localized description"
            )
        }
    }
    for (workout in workouts.drop(1)) {
        Workout(workout = workout, showMenuActions = false)
    }
}

@Composable
fun Workout(workout: WorkoutModel, showMenuActions: Boolean = true) {
    val viewModel: WorkoutViewModel = viewModel()
    val context = LocalContext.current

    MyFitnessCard(onClick = { viewModel.startWorkout(context, workout) }) {
        Row {
            MyFitnessH3Subscript1(
                title = workout.name.capitalize(), text = checkHistoryForLatest(workout.history)
            )
            if (showMenuActions) {
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    MenuAction(workout)
                }

            }

        }
        Exercises(workout.exercises)
    }
}

@Composable
fun MenuAction(workout: WorkoutModel) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = !expanded }) {
        Icon(
            imageVector = Icons.Filled.MoreVert, contentDescription = "Localized description"
        )
        WorkoutMenu(expanded = expanded, workout) {
            expanded = false
        }
    }
}

@Composable
fun WorkoutMenu(
    expanded: Boolean,
    workout: WorkoutModel,
    setExpanded: () -> Unit
) {
    val viewModel: WorkoutViewModel = viewModel()

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { setExpanded() },
        modifier = Modifier.background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        DropdownMenuItem(text = {
            Text(
                "Show History", modifier = Modifier.fillMaxWidth()
            )
        }, onClick = {
            setExpanded()
            viewModel.showHistory.postValue(workout._id.toString())
        })
        DropdownMenuItem(text = {
            Text(
                "Edit Workout", modifier = Modifier.fillMaxWidth()
            )
        }, onClick = {
            setExpanded()
            viewModel.editWorkout(workout)
        })
        DropdownMenuItem(text = {
            Text(
                "Delete Workout", modifier = Modifier.fillMaxWidth()
            )
        }, onClick = {
            setExpanded()
            viewModel.deleteWorkout(workout)
        })
    }
}

fun checkHistoryForLatest(history: List<History>): String {
    Timber.i("Searching History: $history")

    if (history.isNotEmpty()) {
        if (history.last().createdAt != null) {
            return "Last Performed: " + history.last().createdAt.toString()
        }
    }
    return "Not Performed Yet"
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