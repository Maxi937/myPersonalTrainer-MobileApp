package org.fitness.myfitnesstrainer.ui.screens.App

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.lifecycle.Observer
import androidx.navigation.compose.rememberNavController
import org.fitness.myfitnesstrainer.auth.AuthManager
import org.fitness.myfitnesstrainer.ui.composables.BottomNavBar.BottomNavBar
import org.fitness.myfitnesstrainer.ui.composables.BottomNavBar.BottomNavRoutes
import org.fitness.myfitnesstrainer.ui.composables.TopNavBar.MyFitnessTopNavBar
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(authManager: AuthManager) {
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val loggedIn by authManager.loggedIn.observeAsState()

    Scaffold(
        topBar = { MyFitnessTopNavBar(authManager) },
        content = { paddingValues ->  Button(modifier = Modifier.padding(paddingValues), onClick = { authManager.checkLogin() }, content = {
            Text(loggedIn.toString())
        })},
        bottomBar = { BottomNavBar(navController) }
    )
}
//BottomNavRoutes(navController = navController, paddingValues)