package org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Exercise

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import org.fitness.myfitnesstrainer.data.local.models.ExerciseModel
import org.fitness.myfitnesstrainer.data.local.models.Profile
import org.fitness.myfitnesstrainer.ui.composables.Exercise.ExerciseItem
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessInputFields.MyFitnessTextInput
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessInputFields.filterMaxChars
import org.fitness.myfitnesstrainer.ui.composables.Screen.Screen
import org.fitness.myfitnesstrainer.ui.composables.Search.Search
import org.fitness.myfitnesstrainer.ui.preview.ProfilePreviewParameterProvider
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme

@Composable
fun ExerciseScreen(exercises: List<ExerciseModel>) {
    val viewModel: ExerciseViewModel = viewModel()
    val sort by remember { mutableStateOf(false) }

    val search = remember {
        mutableStateOf("")
    }
    var searchVisible = remember {
        mutableStateOf(false)
    }

    Search(searchVisible, search, "Search by Body Part")

    Screen(modifier = Modifier.padding(0.dp, 20.dp)) {
        for (exercise in viewModel.sort(exercises, sort, search.value)) {
            key(exercise._id) {
                SwipeToDeleteExercise(exercise = exercise, onDelete = {
                    if (exercise._id != null) {
                        viewModel.deleteExercise(exercise)
                    }
                }) {
                    ExerciseItem(exercise = exercise)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDeleteExercise(
    modifier: Modifier = Modifier,
    exercise: ExerciseModel,
    onDelete: () -> Unit,
    dismissContent: @Composable () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        var dismissState = rememberDismissState(initialValue = DismissValue.Default)

        if (dismissState.isDismissed(DismissDirection.EndToStart)) {
            onDelete()
        }

        SwipeToDismiss(
            state = dismissState,
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            directions = setOf(DismissDirection.EndToStart),
            background = {
                val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                val color by animateColorAsState(
                    when (dismissState.targetValue) {
                        DismissValue.Default -> Color.Transparent
                        DismissValue.DismissedToEnd -> Color.Green
                        DismissValue.DismissedToStart -> Color.Red
                    },
                    label = "trans dismiss"
                )
                val alignment = when (direction) {
                    DismissDirection.StartToEnd -> Alignment.CenterStart
                    DismissDirection.EndToStart -> Alignment.CenterEnd
                }
                val icon = when (direction) {
                    DismissDirection.StartToEnd -> Icons.Default.Done
                    DismissDirection.EndToStart -> Icons.Default.Delete
                }
                val scale by animateFloatAsState(
                    if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f,
                    label = "default dismiss"
                )

                Box(
                    Modifier
                        .fillMaxSize()
                        .background(color)
                        .padding(horizontal = 20.dp),
                    contentAlignment = alignment
                ) {
                    Icon(
                        icon,
                        contentDescription = "Localized description",
                        modifier = Modifier.scale(scale)
                    )
                }
            },
            dismissContent = {
                ExerciseItem(exercise = exercise)
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ExercisePreview(@PreviewParameter(ProfilePreviewParameterProvider::class) profile: Profile) {
    MyFitnessTrainerTheme {
        ExerciseScreen(exercises = profile.exercises)
    }
}




