package org.fitness.myfitnesstrainer.ui.activities.workoutActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import org.fitness.myfitnesstrainer.data.local.models.WorkoutModel
import org.fitness.myfitnesstrainer.ui.activities.workoutActivity.routes.WorkoutActivityRoutes
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme


class WorkoutActivity : ComponentActivity() {
    private lateinit var workout: WorkoutModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        workout = intent.extras?.getParcelable("workout", WorkoutModel::class.java)!!
    }

    public override fun onStart() {
        super.onStart()

        setContent {
            MyFitnessTrainerTheme {
                Surface {
                    WorkoutActivityRoutes(workout)
                }
            }
        }
    }
}