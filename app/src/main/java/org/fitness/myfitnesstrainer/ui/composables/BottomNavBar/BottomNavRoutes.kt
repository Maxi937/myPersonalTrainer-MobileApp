package org.fitness.myfitnesstrainer.ui.composables.BottomNavBar


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.fitness.myfitnesstrainer.ui.screens.Profile.ProfileScreen


@Composable
fun BottomNavRoutes(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Profile.route) {
        composable(BottomNavItem.Profile.route) {
            ProfileScreen("hello")
        }
        composable(BottomNavItem.Workout.route) {
            ProfileScreen("Workout")
        }
        composable(BottomNavItem.Exercise.route) {
            ProfileScreen("Exercise")
        }
    }
}