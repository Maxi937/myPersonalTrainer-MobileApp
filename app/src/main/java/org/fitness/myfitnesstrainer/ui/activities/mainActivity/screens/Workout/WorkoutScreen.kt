package org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Workout


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
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
import org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Exercise.SwipeToDeleteExercise
import org.fitness.myfitnesstrainer.ui.composables.Exercise.ExerciseItem
import org.fitness.myfitnesstrainer.ui.composables.Exercise.Sets
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessCard.MyFitnessCard
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessText.MyFitnessH3Subscript1
import org.fitness.myfitnesstrainer.ui.composables.Screen.Screen
import org.fitness.myfitnesstrainer.ui.composables.Search.Search
import org.fitness.myfitnesstrainer.ui.composables.Swipe.SwipeToDelete
import org.fitness.myfitnesstrainer.ui.preview.ProfilePreviewParameterProvider
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme
import timber.log.Timber
import java.time.LocalDateTime.parse
import java.time.format.DateTimeFormatter
import java.util.Date


@Composable
fun WorkoutScreen(workouts: List<WorkoutModel>) {
    val viewModel: WorkoutViewModel = viewModel()

    val sort by remember { mutableStateOf(false)}
    val showHistory by viewModel.showHistory.observeAsState()
    val editWorkout by viewModel.editWorkout.observeAsState()

    val search = remember {
        mutableStateOf("")
    }
    var searchVisible = remember {
        mutableStateOf(false)
    }

    Search(searchVisible, search, "Search Workout Name")
    Screen {
        when {
            showHistory != null -> ShowHistory(workouts = viewModel.getHistory(showHistory!!))
            editWorkout != null -> {
                val workout = viewModel.getWorkoutById(editWorkout!!)
                if (workout != null) { EditWorkout(workout = workout) } }
            else ->
                ShowWorkouts(viewModel.sort(workouts, sort, search.value))
        }
    }
}

@Composable
fun ShowWorkouts(workouts: List<WorkoutModel>) {
    for (workout in workouts) {
        Workout(workout)
        Spacer(Modifier.size(10.dp))
    }
}

@Composable
fun ShowHistory(workouts: List<WorkoutModel>) {
    val viewModel: WorkoutViewModel = viewModel()
    Workout(workout = workouts[0], showMenuActions = false)
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = { viewModel.showHistory.postValue(null) }) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "Localized description"
            )
        }
    }
    for (workout in workouts.drop(1)) {
        History(workout = workout)
        Spacer(Modifier.size(10.dp))
    }
}

@Composable
fun EditWorkout(workout: WorkoutModel) {
    val viewModel: WorkoutViewModel = viewModel()
    Workout(workout = workout, showMenuActions = false)
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        IconButton(onClick = {
            Timber.i("Sending: ${workout.exercises}")
            viewModel.updateWorkout(workout)
            viewModel.editWorkout.postValue(null)
        }) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "Localized description"
            )
        }
    }
    for (exercise in workout.exercises) {
        var numberOfSets by remember { mutableIntStateOf(exercise.sets.size) }

        ExerciseItem(exercise = exercise) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(numberOfSets.toString())
                Row {
                    IconButton(onClick = {
                        if (numberOfSets > 1) {
                            exercise.sets = exercise.sets.drop(1).toMutableList()
                            numberOfSets -= 1
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowLeft,
                            contentDescription = "Localized description"
                        )
                    }
                    IconButton(onClick = {
                        exercise.sets.add(listOf(50F, 50F, 50F))
                        numberOfSets += 1
                    }) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowRight,
                            contentDescription = "Localized description"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun History(workout: WorkoutModel) {
    MyFitnessCard(onClick = { }) {
        Row {
            MyFitnessH3Subscript1(
                title = workout.name.capitalize(), text = checkHistoryForLatest(workout.history)
            )
        }
        Exercises(workout.exercises)
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
    expanded: Boolean, workout: WorkoutModel, setExpanded: () -> Unit
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
            viewModel.editWorkout.postValue(workout._id.toString())
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