package org.fitness.myfitnesstrainer.ui.activities.workoutActivity.screens.activeWorkout

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.fitness.myfitnesstrainer.data.local.models.ExerciseModel
import org.fitness.myfitnesstrainer.data.local.models.WorkoutModel
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessButtons.MyFitnessTertiaryButton
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessInputFields.MyFitnessDecimalInputField
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessInputFields.MyFitnessIntInputField
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessText.MyFitnessH2
import org.fitness.myfitnesstrainer.ui.composables.Screen.Screen

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ActiveWorkout(workout: WorkoutModel, navigation: (newWorkout: WorkoutModel) -> Unit) {
    val viewModel: ActiveWorkoutViewModel = viewModel()

    Scaffold(topBar = { WorkoutActivityTopBar {
        val newWorkout = viewModel.getCompletedWorkout(workout)
        navigation(newWorkout)
    }
    }) {
        Screen(modifier = Modifier.padding(it)) {
            for (e in workout.exercises) {
                MyFitnessH2(e.name)
                ExerciseTable(e)
            }
            Control(text = "Add Exercise") {
//            viewModel.addExercise()
            }
            Control(text = "Cancel Workout") {
//            viewModel.cancel()
            }

        }
    }
}

@Composable
fun ExerciseTable(exercise: ExerciseModel) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 5.dp)
        ) {
            Text("Set", modifier = Modifier.weight(.10F), textAlign = TextAlign.Center)
            Text("Weight", modifier = Modifier.weight(.30F), textAlign = TextAlign.Center)
            Text("Reps", modifier = Modifier.weight(.30F), textAlign = TextAlign.Center)
            Text("Done", modifier = Modifier.weight(.15F), textAlign = TextAlign.Right)
        }
        ActiveSets(exercise = exercise)
    }
}

@Composable
fun ActiveSets(exercise: ExerciseModel) {
    val viewModel: ActiveWorkoutViewModel = viewModel()

    var sets by remember { mutableStateOf(exercise.sets.size) }

    for (i in 1..sets) {
        var reps = remember { mutableStateOf("") }
        var weight = remember { mutableStateOf("") }
        var isChecked by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = (i).toString(),
                modifier = Modifier.weight(.10F),
                textAlign = TextAlign.Center
            )
            MyFitnessDecimalInputField(
                value = weight, modifier = Modifier
                    .weight(.30F)
                    .padding(10.dp)
            )
            MyFitnessIntInputField(
                value = reps, modifier = Modifier
                    .weight(.30F)
                    .padding(10.dp)
            )
            Checkbox(checked = isChecked, onCheckedChange = {
                val mReps = reps.value.toIntOrNull()
                val mWeight = weight.value.toFloatOrNull()

                if (mReps != null && mWeight != null) {
                    isChecked = it
                    val set: List<Float> = List(mReps) { mWeight }

                    if (isChecked) {
                        viewModel.completeSet(exercise, set)
                    } else {
                        viewModel.uncompleteSet(exercise, set)
                    }
                }
            })
        }
    }
    Control(text = "Add Set") {
        sets += 1
    }
}

@Composable
fun Control(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    MyFitnessTertiaryButton(
        text = text.uppercase(), modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        onClick()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutActivityTopBar(onClick: () -> Unit) {
    TopAppBar(
        title = {""},
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        actions = {
            IconButton(onClick = {
                onClick()
            }) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(38.dp),
                    tint = MaterialTheme.colorScheme.inversePrimary
                )
            }
        }
    )
}


//@Preview(showBackground = true)
//@Composable
//fun DoWorkoutPreview(@PreviewParameter(ProfilePreviewParameterProvider::class) profile: Profile) {
//    val viewModel: WorkoutActivityViewModel = viewModel(factory = WorkoutActivityViewModelFactory(profile.workouts[0]))
//    val dummyfunc = {}
//
//    MyFitnessTrainerTheme {
//        ActiveWorkout(dummyfunc)
//    }
//}

