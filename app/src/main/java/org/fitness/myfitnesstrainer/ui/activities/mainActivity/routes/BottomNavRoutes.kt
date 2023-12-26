package org.fitness.myfitnesstrainer.ui.activities.mainActivity.routes


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.fitness.myfitnesstrainer.data.local.models.Profile
import org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.AddResources.AddExerciseScreen
import org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.AddResources.AddWorkoutScreen
import org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Exercise.ExerciseScreen
import org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Profile.ProfileScreen
import org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Workout.WorkoutScreen
import org.fitness.myfitnesstrainer.ui.composables.BottomNavBar.BottomNavItem


@Composable
fun BottomNavRoutes(navController: NavHostController, padding: PaddingValues, profile: Profile) {
    NavHost(navController, startDestination = BottomNavItem.Profile.route, modifier = Modifier.padding(padding)) {
        composable(BottomNavItem.Profile.route) {
            ProfileScreen(profile)
        }
        composable(BottomNavItem.Workout.route) {
            WorkoutScreen(workouts = profile.workouts)
        }
        composable(BottomNavItem.Exercise.route) {
            ExerciseScreen(exercises = profile.exercises)
        }
        composable("addWorkout") {
            AddWorkoutScreen()
        }
        composable("addExercise") {
            AddExerciseScreen()
        }
    }
}