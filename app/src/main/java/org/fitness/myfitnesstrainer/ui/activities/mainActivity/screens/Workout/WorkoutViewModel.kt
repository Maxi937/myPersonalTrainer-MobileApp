package org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Workout

import android.content.Context
import android.content.Intent
import androidx.compose.material3.ColorScheme
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.fitness.myfitnesstrainer.data.local.models.WorkoutModel
import org.fitness.myfitnesstrainer.repository.MyFitnessRepository
import org.fitness.myfitnesstrainer.ui.activities.workoutActivity.WorkoutActivity
import org.fitness.myfitnesstrainer.ui.theme.DarkColorScheme
import timber.log.Timber


class WorkoutViewModel() : ViewModel() {
    val myFitnessRepository = MyFitnessRepository

    val showHistory: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val editWorkout: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun startWorkout(context: Context, workout: WorkoutModel) {
        val intent = Intent(context, WorkoutActivity::class.java)
        intent.putExtra("workout", workout)
        context.startActivity(intent)
    }

    fun updateWorkout(workout: WorkoutModel) {
        workout._id?.let { MyFitnessRepository.updateWorkout(it, workout) }
    }

    fun getWorkoutById(workoutId: String): WorkoutModel? {
        return myFitnessRepository.getWorkoutById(workoutId)
    }

    fun editWorkout(workout: WorkoutModel) {
        Timber.i("Edit Workout")
    }

    fun deleteWorkout(workout: WorkoutModel) {
        myFitnessRepository.deleteWorkout(workout)
    }

    fun getHistory(workoutId: String): List<WorkoutModel> {
        return myFitnessRepository.getWorkoutHistory(workoutId)
    }
}