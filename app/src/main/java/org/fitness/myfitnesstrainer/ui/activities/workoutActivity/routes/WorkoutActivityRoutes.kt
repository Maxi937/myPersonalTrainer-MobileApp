package org.fitness.myfitnesstrainer.ui.activities.workoutActivity.routes

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.fitness.myfitnesstrainer.data.local.models.WorkoutModel
import org.fitness.myfitnesstrainer.repository.MyFitnessCompletedWorkoutRepository
import org.fitness.myfitnesstrainer.ui.activities.workoutActivity.screens.activeWorkout.ActiveWorkout
import org.fitness.myfitnesstrainer.ui.activities.workoutActivity.screens.completeWorkout.CompleteWorkout
import org.fitness.myfitnesstrainer.ui.activities.workoutActivity.screens.confirmWorkout.ConfirmWorkout


@Composable
fun WorkoutActivityRoutes(workout: WorkoutModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "confirmWorkout") {
        composable("confirmWorkout") {
            ConfirmWorkout(workout) {
                navController.navigate("doWorkout")
            }
        }
        composable("doWorkout") {
            ActiveWorkout(workout) {newWorkout ->
                MyFitnessCompletedWorkoutRepository.completedWorkout = newWorkout
                navController.navigate("finish")

            }
        }
        composable("finish") {
            val completedWorkout = MyFitnessCompletedWorkoutRepository.completedWorkout
            CompleteWorkout(workout, completedWorkout)
        }
    }
}


