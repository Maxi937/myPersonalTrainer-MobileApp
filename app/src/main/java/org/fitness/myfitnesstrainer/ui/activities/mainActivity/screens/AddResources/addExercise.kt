package org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.AddResources

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import org.fitness.myfitnesstrainer.data.local.models.Profile
import org.fitness.myfitnesstrainer.repository.MyFitnessRepository
import org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Exercise.ExerciseScreen
import org.fitness.myfitnesstrainer.ui.composables.Message.showMessageError
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessButtons.MyFitnessPrimaryButton
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessCard.MyFitnessCard
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessInputFields.MyFitnessDropdownSelect
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessInputFields.MyFitnessMultiLineTextInput
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessInputFields.MyFitnessTextInput
import org.fitness.myfitnesstrainer.ui.composables.Screen.Screen
import org.fitness.myfitnesstrainer.ui.preview.ProfilePreviewParameterProvider
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme
import timber.log.Timber

@Composable
fun AddExerciseScreen(doneCallback: () -> Unit) {
    val myFitnessRepository = MyFitnessRepository

    var isError by remember { mutableStateOf(false) }
    val name = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    var selectedBodyPart = remember { mutableStateOf("") }

    Screen(
        content = {
            MyFitnessCard {
                MyFitnessTextInput(text = name, placeholder = "Name")
                Spacer(modifier = Modifier.size(8.dp))
                MyFitnessDropdownSelect(
                    selectedBodyPart,
                    bodyPartCategories.bodyPartCategories,
                    placeholder = "Body Part"
                )
                Spacer(modifier = Modifier.size(8.dp))
                MyFitnessMultiLineTextInput(text = description, placeholder = "Description")
            }
        },
        bottomContent = {
            MyFitnessPrimaryButton(text = "Add Exercise", modifier = Modifier.fillMaxWidth()) {
                if (name.value.isNotEmpty() && description.value.isNotEmpty() && selectedBodyPart.value.isNotEmpty()) {
                    Timber.i("Form Okay")
                    myFitnessRepository.createExercise(
                        name.value,
                        description.value,
                        selectedBodyPart.value
                    )
                } else {
                    Timber.i("Form Err")
                    isError = true
                }
            }
            if (isError) {
                showMessageError(msg = "Please complete all fields")
                isError = false
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AddExerciseScreenPreview() {
    MyFitnessTrainerTheme {
        AddExerciseScreen() {}
    }
}





