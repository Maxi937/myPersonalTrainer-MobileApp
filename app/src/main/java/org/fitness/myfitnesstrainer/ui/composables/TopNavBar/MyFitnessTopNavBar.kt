package org.fitness.myfitnesstrainer.ui.composables.TopNavBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyFitnessTopNavBar(navController: NavController, logout: () -> Unit) {
    var route by remember { mutableStateOf("") }
    Timber.i(route)

    LaunchedEffect(navController.currentBackStackEntryFlow) {
        navController.currentBackStackEntryFlow.collect {
            route = it.destination.route.toString()
        }
    }

    TopAppBar(
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                "My Fitness Trainer",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            when (route) {
                "Workout" -> addWorkoutAction() {
                    navController.navigate("addWorkout")
                }
                "Exercise" -> addExerciseAction() {
                    navController.navigate("addExercise")
                }
            }
            logoutAction(logout)
        },
    )
}


@Composable
fun addWorkoutAction(onClicked : () -> Unit) {
    IconButton(onClick = { onClicked() }) {
        Icon(
            imageVector = Icons.Filled.AddCircle,
            contentDescription = "Localized description"
        )
    }
}

@Composable
fun addExerciseAction(onClicked : () -> Unit) {
    IconButton(onClick = { onClicked }) {
        Icon(
            imageVector = Icons.Filled.AddCircle,
            contentDescription = "Localized description"
        )
    }
}

@Composable
fun logoutAction(logout: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = !expanded }) {
        Icon(
            imageVector = Icons.Filled.Menu,
            contentDescription = "Localized description"
        )
        MyFitnessTopBarMenu(logout, expanded = expanded) {
            expanded = false
        }
    }
}