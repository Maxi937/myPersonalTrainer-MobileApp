package org.fitness.myfitnesstrainer.ui.activities.mainActivity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import org.fitness.myfitnesstrainer.auth.AuthManager
import org.fitness.myfitnesstrainer.data.local.models.Profile
import org.fitness.myfitnesstrainer.repository.MyFitnessRepository
import org.fitness.myfitnesstrainer.repository.ResourceStatus
import org.fitness.myfitnesstrainer.ui.activities.mainActivity.routes.BottomNavRoutes
import org.fitness.myfitnesstrainer.ui.composables.BottomNavBar.BottomNavBar
import org.fitness.myfitnesstrainer.ui.composables.TopNavBar.MyFitnessTopNavBar
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme
import timber.log.Timber


class MyFitnessActivity : ComponentActivity() {
    private lateinit var authManager: AuthManager
    private lateinit var myFitnessRepository: MyFitnessRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        authManager = AuthManager(this)
        myFitnessRepository = MyFitnessRepository
    }

    public override fun onStart() {
        super.onStart()

        setContent {
            val loggedIn by authManager.loggedIn.observeAsState()
            val profile by myFitnessRepository.getProfileResource().observeAsState()

            myFitnessActivityLoadingState(profile)

            if (loggedIn == true) {
                when (val p = profile) {
                    is ResourceStatus.IsLoading -> {}
                    is ResourceStatus.IsSuccess -> {
                        myFitnessActivity(
                            profile = p.data,
                            logoutCallback = {
                                authManager.logout()
                                myFitnessRepository.clearProfile()
                            })
                    }

                    is ResourceStatus.IsError -> when (p.code) {
                        401 -> authManager.getNewAuthToken()
                    }

                    is ResourceStatus.IsException -> throw (p.e!!)
                    null -> { myFitnessRepository.refresh() }
                }
            } else {
                authManager.login()
            }
        }
    }
}


@Composable
fun myFitnessActivityLoadingState(state: ResourceStatus<Profile>?) {
    var isLoading by remember { mutableStateOf(false)}

    when (val s = state) {
        is ResourceStatus.IsLoading -> isLoading = true
        else -> {
            LaunchedEffect(key1 = Unit){
                delay(1000)
                isLoading = false
            }
        }
    }

    MyFitnessTrainerTheme {
        AnimatedVisibility(
            modifier = Modifier.zIndex(2F),
            visible = isLoading,
            enter = fadeIn(initialAlpha = 1f),
            exit = shrinkHorizontally(
                animationSpec = tween(),
                shrinkTowards = Alignment.End,
            ) { fullWidth ->
                fullWidth / 4
            }
        ) {
            Surface {
                Column(
                    Modifier.fillMaxSize(),
                    Arrangement.Center,
                    Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(80.dp),
                        color = Color.White
                    )
                }
            }
        }
    }
}


@Composable
fun myFitnessActivity(profile: Profile, logoutCallback: () -> Unit) {
    val navController = rememberNavController()

    MyFitnessTrainerTheme {
        Surface(modifier = Modifier.zIndex(1F)) {
            Scaffold(
                topBar = { MyFitnessTopNavBar(navController) { logoutCallback() } },
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