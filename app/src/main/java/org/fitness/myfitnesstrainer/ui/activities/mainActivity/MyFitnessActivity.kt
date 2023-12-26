package org.fitness.myfitnesstrainer.ui.activities.mainActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import org.fitness.myfitnesstrainer.auth.AuthManager
import org.fitness.myfitnesstrainer.data.local.models.Profile
import org.fitness.myfitnesstrainer.repository.MyFitnessProfileRepository
import org.fitness.myfitnesstrainer.repository.ResourceStatus
import org.fitness.myfitnesstrainer.ui.activities.mainActivity.routes.BottomNavRoutes
import org.fitness.myfitnesstrainer.ui.composables.BottomNavBar.BottomNavBar
import org.fitness.myfitnesstrainer.ui.composables.TopNavBar.MyFitnessTopNavBar
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme
import timber.log.Timber


class MyFitnessActivity : ComponentActivity() {
    private lateinit var authManager: AuthManager
    private lateinit var myFitnessProfileRepository: MyFitnessProfileRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        authManager = AuthManager(this)
        myFitnessProfileRepository = MyFitnessProfileRepository(authManager)
    }

    public override fun onStart() {
        super.onStart()

        setContent {
            val profile by myFitnessProfileRepository.profile.observeAsState()

            when (val p = profile) {
                is ResourceStatus.IsLoading -> myFitnessActivityLoadingState()
                is ResourceStatus.IsSuccess -> myFitnessActivity(profile = p.data, logoutCallback = { authManager.logout() })
                is ResourceStatus.IsError -> Timber.i("Error" + p.errorMsg)
                is ResourceStatus.IsException -> throw (p.e!!)
                null -> authManager.login()
            }

        }
    }
}

@Composable
fun myFitnessActivityLoadingState() {
    MyFitnessTrainerTheme {
        Surface {
            Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
                CircularProgressIndicator(modifier = Modifier.size(80.dp), color = Color.White)
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun myFitnessActivity(profile: Profile, logoutCallback: () -> Unit) {
    val navController = rememberNavController()

    MyFitnessTrainerTheme {
        Surface {
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
    }
}