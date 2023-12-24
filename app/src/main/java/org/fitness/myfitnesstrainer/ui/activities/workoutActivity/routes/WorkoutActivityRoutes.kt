package org.fitness.myfitnesstrainer.ui.activities.workoutActivity.routes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.fitness.myfitnesstrainer.ui.activities.workoutActivity.WorkoutActivityViewModel
import org.fitness.myfitnesstrainer.ui.activities.workoutActivity.screens.confirmWorkout.ConfirmWorkout
import org.fitness.myfitnesstrainer.ui.activities.workoutActivity.screens.activeWorkout.DoWorkout

@Composable
fun WorkoutActivityRoutes() {
    val viewModel: WorkoutActivityViewModel = viewModel()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "confirmWorkout") {
        composable("confirmWorkout") {
            ConfirmWorkout(
                workout = viewModel.workout,
                navigation = { navController.navigate("doWorkout") })
        }
        composable("doWorkout") { DoWorkout(workout = viewModel.workout) }
    }
}
