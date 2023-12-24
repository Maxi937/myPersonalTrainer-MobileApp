package org.fitness.myfitnesstrainer.ui.activities.mainActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.fitness.myfitnesstrainer.auth.AuthManager
import org.fitness.myfitnesstrainer.repository.MyFitnessRepository
import org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Layout.Layout
import org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Layout.LayoutViewModel
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme


class MyFitnessActivity : ComponentActivity() {
    private lateinit var viewModel : LayoutViewModel
    private lateinit var authManager: AuthManager
    private lateinit var myFitnessRepository: MyFitnessRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        authManager = AuthManager(this)
        myFitnessRepository = MyFitnessRepository(authManager)
    }

    public override fun onStart() {
        super.onStart()

        setContent {
            MyFitnessTrainerTheme {
                Surface {
                    Layout(myFitnessRepository)
                }
            }
        }
    }
}