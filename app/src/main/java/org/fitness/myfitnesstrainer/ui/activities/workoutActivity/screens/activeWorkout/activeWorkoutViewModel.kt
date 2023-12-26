package org.fitness.myfitnesstrainer.ui.activities.workoutActivity.screens.activeWorkout

import androidx.lifecycle.ViewModel
import org.fitness.myfitnesstrainer.data.local.models.ExerciseModel
import org.fitness.myfitnesstrainer.data.local.models.WorkoutModel
import timber.log.Timber


//class ActiveWorkoutViewModelFactory(private val workout: WorkoutModel) : ViewModelProvider.NewInstanceFactory() {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T = ActiveWorkoutViewModel(workout) as T
//}

class ActiveWorkoutViewModel() : ViewModel() {
    private val completedExercises = mutableListOf<ExerciseModel>()

    fun completeSet(exercise: ExerciseModel, set: List<Float>) {
        var mExercise = completedExercises.find { it._id == exercise._id }

        if (mExercise != null) {
            mExercise.addSet(set)
        } else {
            mExercise = exercise.copy(sets = mutableListOf())
            completedExercises.add(mExercise)
            completeSet(mExercise, set)
        }
    }

    fun getCompletedWorkout(oldWorkoutModel: WorkoutModel): WorkoutModel {
        return oldWorkoutModel.copy(exercises = completedExercises)
    }

    fun uncompleteSet(exercise: ExerciseModel, set: List<Float>) {
        Timber.i("$completedExercises")
        completedExercises.find { it._id == exercise._id }?.removeSet(set)
    }
}