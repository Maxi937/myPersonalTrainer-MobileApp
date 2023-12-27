package org.fitness.myfitnesstrainer.ui.activities.workoutActivity

import android.content.SharedPreferences
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
import org.fitness.myfitnesstrainer.repository.MyFitnessRepository
import org.fitness.myfitnesstrainer.repository.PreferencesManager
import org.fitness.myfitnesstrainer.ui.activities.workoutActivity.routes.WorkoutActivityRoutes
import org.fitness.myfitnesstrainer.ui.theme.DarkColorScheme
import org.fitness.myfitnesstrainer.ui.theme.LightColorScheme
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme
import timber.log.Timber


class WorkoutActivity : ComponentActivity() {
    private lateinit var workout: WorkoutModel
    lateinit var myFitnessRepository: MyFitnessRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myFitnessRepository = MyFitnessRepository
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