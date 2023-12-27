package org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.AddResources

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.fitness.myfitnesstrainer.data.local.models.ExerciseModel
import org.fitness.myfitnesstrainer.repository.MyFitnessRepository
import org.fitness.myfitnesstrainer.ui.composables.Exercise.ExerciseItem
import org.fitness.myfitnesstrainer.ui.composables.Message.showMessageError
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessButtons.MyFitnessPrimaryButton
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessInputFields.MyFitnessTextInput
import org.fitness.myfitnesstrainer.ui.composables.Screen.Screen
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme
import timber.log.Timber

@Composable
fun AddWorkoutScreen(doneCallback: () -> Unit) {
    val myFitnessRepository = MyFitnessRepository
    val exercises = MyFitnessRepository.getExercises()

    var isError by remember { mutableStateOf(false) }
    val name = remember { mutableStateOf("") }
    val selectedExercises = remember { mutableStateOf(mutableListOf<ExerciseModel>()) }

    Column(modifier = Modifier.padding(10.dp)) {
        MyFitnessTextInput(text = name, placeholder = "Workout Name", maxChars = 35)
    }

    Screen(
        arrangement = Arrangement.Center,
        scrollable = false,
        content = {
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier
                .height(360.dp)
                .verticalScroll(rememberScrollState())) {
                SelectExerciseList(selectedExercises, exercises)
            }
        }
    )
    Column(modifier = Modifier.fillMaxHeight(), Arrangement.Bottom) {
        MyFitnessPrimaryButton(text = "Done", modifier = Modifier.fillMaxWidth()) {
            if (name.value.isNotEmpty() && selectedExercises.value.isNotEmpty()) {
                Timber.i("Form Okay")
                Timber.i("exercises: $selectedExercises")
                myFitnessRepository.createWorkout(name.value, selectedExercises.value.toList())
            } else {
                Timber.i("Form Err")
                isError = true
            }
        }
        if (isError) {
            showMessageError(msg = "Please select at least one exercise")
            isError = false
        }
    }
}

@Composable
fun SelectExerciseList(
    stateList: MutableState<MutableList<ExerciseModel>>, exercises: List<ExerciseModel>
) {
    for ((index, exercise) in exercises.withIndex()) {
        var isChecked by remember { mutableStateOf(false) }

        ExerciseItem(exercise = exercise, padding = PaddingValues(10.dp)) {
            Checkbox(checked = isChecked, onCheckedChange = {
                isChecked = it

                if (it) {
                    stateList.value.add(exercise)
                } else {
                    stateList.value.remove(exercise)
                }
            })
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AddWorkoutScreenPreview() {
    MyFitnessTrainerTheme {
        AddExerciseScreen() {}
    }
}





