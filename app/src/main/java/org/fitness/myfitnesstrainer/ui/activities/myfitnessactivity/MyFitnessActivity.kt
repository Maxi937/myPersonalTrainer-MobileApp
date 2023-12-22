package org.fitness.myfitnesstrainer.ui.activities.myfitnessactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.fitness.myfitnesstrainer.auth.AuthManager
import org.fitness.myfitnesstrainer.ui.activities.myfitnessactivity.screens.Layout.Layout
import org.fitness.myfitnesstrainer.ui.activities.myfitnessactivity.screens.Layout.LayoutViewModel
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme


class MyFitnessActivity : ComponentActivity() {
    private lateinit var viewModel : LayoutViewModel
    private lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        authManager = AuthManager(this)
    }

    public override fun onStart() {
        super.onStart()

        setContent {
            MyFitnessTrainerTheme {
                Layout(authManager)
            }
        }
    }
}