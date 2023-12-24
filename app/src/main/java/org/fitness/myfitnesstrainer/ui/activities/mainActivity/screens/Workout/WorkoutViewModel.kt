package org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Workout

import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import org.fitness.myfitnesstrainer.data.local.models.WorkoutModel
import org.fitness.myfitnesstrainer.ui.activities.workoutActivity.WorkoutActivity
import timber.log.Timber


class WorkoutViewModel() : ViewModel() {
    fun startWorkout(context: Context, workout: WorkoutModel) {

        val intent = Intent(context, WorkoutActivity::class.java)
        intent.putExtra("workout", workout)
        Timber.i("Putting Workout: ${intent.extras}")
        context.startActivity(intent)
    }
}