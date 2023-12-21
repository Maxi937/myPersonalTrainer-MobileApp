package org.fitness.myfitnesstrainer.ui.composables.BottomNavBar


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.fitness.myfitnesstrainer.ui.screens.Profile.ProfileScreen


@Composable
fun BottomNavRoutes(navController: NavHostController, padding: PaddingValues) {
    NavHost(navController, startDestination = BottomNavItem.Profile.route, modifier = Modifier.padding(padding)) {
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