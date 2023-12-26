package org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.AddResources

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import org.fitness.myfitnesstrainer.auth.AuthManager
import org.fitness.myfitnesstrainer.data.local.models.ExerciseModel
import org.fitness.myfitnesstrainer.repository.MyFitnessRepository
import org.fitness.myfitnesstrainer.repository.ResourceStatus
import org.fitness.myfitnesstrainer.ui.activities.mainActivity.myFitnessActivity
import org.fitness.myfitnesstrainer.ui.activities.mainActivity.myFitnessActivityLoadingState
import org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Exercise.ExerciseViewModel
import org.fitness.myfitnesstrainer.ui.composables.Screen.Screen

@Composable
fun AddWorkoutScreen() {

    Screen(modifier = Modifier.fillMaxSize().zIndex(100f)) {
        Text("Add Workout")
    }

}

fun NewWorkoutForm() {
    val MyFitnessRepository = MyFitnessRepository

    val exercises = MyFitnessRepository.getExercises()

}