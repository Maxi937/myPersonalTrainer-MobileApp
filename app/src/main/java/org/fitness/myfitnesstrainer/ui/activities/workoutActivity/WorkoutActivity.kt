package org.fitness.myfitnesstrainer.ui.activities.workoutActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import org.fitness.myfitnesstrainer.data.local.models.WorkoutModel
import org.fitness.myfitnesstrainer.ui.activities.workoutActivity.routes.WorkoutActivityRoutes
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme
import timber.log.Timber


class WorkoutActivity : ComponentActivity() {
    private lateinit var workout: WorkoutModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        workout = intent.extras?.getParcelable("workout", WorkoutModel::class.java)!!
        Timber.i(workout.name)
    }

    public override fun onStart() {
        super.onStart()

        setContent {
            MyFitnessTrainerTheme {
                val viewModel: WorkoutActivityViewModel = viewModel(factory = WorkoutActivityViewModelFactory(workout))
                WorkoutActivityRoutes()
            }

        }
    }
}