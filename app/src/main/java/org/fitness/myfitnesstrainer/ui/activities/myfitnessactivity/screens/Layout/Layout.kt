package org.fitness.myfitnesstrainer.ui.activities.myfitnessactivity.screens.Layout

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import org.fitness.myfitnesstrainer.auth.AuthManager
import org.fitness.myfitnesstrainer.ui.composables.BottomNavBar.BottomNavBar
import org.fitness.myfitnesstrainer.ui.activities.myfitnessactivity.routes.BottomNavRoutes
import org.fitness.myfitnesstrainer.ui.composables.TopNavBar.MyFitnessTopNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Layout(authManager: AuthManager) {
    val viewModel: LayoutViewModel = viewModel(factory = LayoutViewModelFactory(authManager))
    val navController = rememberNavController()
    val profile by viewModel.profile.observeAsState()

    if(profile != null) {
        Scaffold(
            topBar = { MyFitnessTopNavBar(authManager) },
            content = { paddingValues ->  BottomNavRoutes(navController = navController, padding = paddingValues) },
            bottomBar = { BottomNavBar(navController) }
        )
    } else {
        authManager.getTokenForAccountCreateIfNeeded()
    }
}
//