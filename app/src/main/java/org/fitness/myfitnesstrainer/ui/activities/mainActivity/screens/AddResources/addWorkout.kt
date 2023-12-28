package org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.AddResources

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.fitness.myfitnesstrainer.data.local.models.ExerciseModel
import org.fitness.myfitnesstrainer.repository.MyFitnessRepository
import org.fitness.myfitnesstrainer.ui.composables.Exercise.ExerciseItem
import org.fitness.myfitnesstrainer.ui.composables.Message.showMessageError
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessButtons.MyFitnessPrimaryButton
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessInputFields.MyFitnessTextInput
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessInputFields.filterMaxChars
import org.fitness.myfitnesstrainer.ui.composables.Screen.Screen
import org.fitness.myfitnesstrainer.ui.composables.Search.Search
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme
import timber.log.Timber

@Composable
fun AddWorkoutScreen(doneCallback: () -> Unit) {
    val viewModel: addResourcesViewModel = viewModel()


    val search = remember {
        mutableStateOf("")
    }
    var searchVisible = remember {
        mutableStateOf(false)
    }

    val exercises = viewModel.getExercises(search.value)
    Search(searchVisible, search, "Search Body Part")

    var isError by remember { mutableStateOf(false) }
    val name = remember { mutableStateOf("") }
    val selectedExercises = remember { mutableStateOf(viewModel.selectedExercises) }

    Column(
        modifier = Modifier
            .padding(10.dp, 75.dp)
            .fillMaxWidth(), Arrangement.Center
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = name.value,
            onValueChange = { name.value = filterMaxChars(25, it) },
            placeholder = { Text("Workout Name", color = Color.Gray) })
    }

    Screen(
        arrangement = Arrangement.Center,
        scrollable = false,
        content = {
            Column(
                verticalArrangement = Arrangement.Top, modifier = Modifier
                    .height(360.dp)
                    .verticalScroll(rememberScrollState())
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                SelectExerciseList(exercises, search.value)
            }
        }
    )
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(10.dp), Arrangement.Bottom
    ) {
        MyFitnessPrimaryButton(text = "Done", modifier = Modifier.fillMaxWidth()) {
            if (name.value.isNotEmpty() && selectedExercises.value.isNotEmpty()) {
                viewModel.createWorkout(name.value)
                doneCallback()
            } else {
                isError = true
            }
        }
        if (isError) {
            if (name.value.isEmpty()) {
                showMessageError(msg = "Please fill in a Workout name")
            } else {
                showMessageError(msg = "Please select at least one exercise")
            }

            isError = false
        }
    }
}

@Composable
fun SelectExerciseList(exercises: List<ExerciseModel>, search: String) {
    val viewModel: addResourcesViewModel = viewModel()

    for (exercise in exercises) {
        var isChecked by remember { mutableStateOf(viewModel.getChecked(exercise)) }

        if(search.isNotEmpty()) {
            isChecked = viewModel.getChecked(exercise)
        }



        ExerciseItem(exercise = exercise, padding = PaddingValues(0.dp, 10.dp)) {
            Checkbox(checked = isChecked, onCheckedChange = {
                isChecked = it

                if (it) {
//                    selectedExercises.add(exercise)
                    viewModel.selectedExercises.add(exercise)
                } else {
//                    selectedExercises.remove(exercise)
                    viewModel.selectedExercises.remove(exercise)
                }
            })
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AddWorkoutScreenPreview() {
    MyFitnessTrainerTheme {
        AddWorkoutScreen() {}
    }
}





