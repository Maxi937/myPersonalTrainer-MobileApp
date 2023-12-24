package org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import org.fitness.myfitnesstrainer.data.local.models.Profile
import org.fitness.myfitnesstrainer.repository.MyFitnessRepository
import org.fitness.myfitnesstrainer.repository.ResourceStatus
import org.fitness.myfitnesstrainer.ui.composables.BottomNavBar.BottomNavBar
import org.fitness.myfitnesstrainer.ui.activities.mainActivity.routes.BottomNavRoutes
import org.fitness.myfitnesstrainer.ui.composables.TopNavBar.MyFitnessTopNavBar
import timber.log.Timber

@Composable
fun Layout(myFitnessRepository: MyFitnessRepository) {
    val viewModel: LayoutViewModel = viewModel(factory = LayoutViewModelFactory(myFitnessRepository))
    val profile by viewModel.getProfileRepository().observeAsState()

    when (val p = profile) {
        is ResourceStatus.IsLoading -> myFitnessActivityLoadingState()
        is ResourceStatus.IsSuccess -> myFitnessActivity(profile = p.data, logoutCallback = { viewModel.logout() })
        is ResourceStatus.IsError -> Timber.i("Error" + p.errorMsg)
        is ResourceStatus.IsException -> throw(p.e!!)
        null -> viewModel.login()
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun myFitnessActivity(profile: Profile, logoutCallback: () -> Unit) {
    val navController = rememberNavController()

    Scaffold(
        topBar = { MyFitnessTopNavBar { logoutCallback() } },
        content = { paddingValues ->
            BottomNavRoutes(
                navController = navController,
                padding = paddingValues,
                profile = profile
            )
        },
        bottomBar = { BottomNavBar(navController) }
    )
}

@Composable
fun myFitnessActivityLoadingState() {
    Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
        CircularProgressIndicator(modifier = Modifier.size(80.dp), color = Color.White)
    }

}