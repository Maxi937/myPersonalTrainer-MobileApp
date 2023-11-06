package org.fitness.myfitnesstrainer.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.fitness.myfitnesstrainer.api.models.UserDetails
import org.fitness.myfitnesstrainer.api.models.apiExercise
import org.fitness.myfitnesstrainer.api.models.apiWorkout
import org.fitness.myfitnesstrainer.api.models.xProfile
import timber.log.Timber

@Parcelize
data class Profile(private val xProfile: xProfile) : Parcelable {
    var exercises: List<ExerciseModel> = unwrapExercises(xProfile.exercises)
    var workouts: List<WorkoutModel> = unwrapWorkouts(xProfile.workouts)
    var history: List<WorkoutModel> = unwrapHistory(xProfile.history)
    var fname: String = xProfile.userDetails.fname
    var lname: String = xProfile.userDetails.lname
    var email: String = xProfile.userDetails.email

    init {
        Timber.i("Initing new profile")
        Timber.i("%s", xProfile.history)
    }

    private fun unwrapHistory(apiWorkouts: List<apiWorkout>): List<WorkoutModel> {
        var mutWorkouts: MutableList<WorkoutModel> = mutableListOf()

        for (apiWorkout in apiWorkouts) {
            val workout: WorkoutModel = WorkoutModel(apiWorkout.name, unwrapExercises(apiWorkout.exercises), apiWorkout.date)
            mutWorkouts.add(workout)
        }
        return mutWorkouts.toList()
    }

    private fun unwrapWorkouts(apiWorkouts: List<apiWorkout>): List<WorkoutModel> {
        var mutWorkouts: MutableList<WorkoutModel> = mutableListOf()

        for (apiWorkout in apiWorkouts) {
            val workout: WorkoutModel = WorkoutModel(apiWorkout.name, unwrapExercises(apiWorkout.exercises))
            Timber.i("api: %s", apiWorkout)
//            if(apiWorkout.date.isNotEmpty()) {
//                workout.date = apiWorkout.date
//            }
            mutWorkouts.add(workout)
        }
        return mutWorkouts.toList()
    }
    private fun unwrapExercises(apiExercises: List<apiExercise>): List<ExerciseModel> {
        var mutExercises: MutableList<ExerciseModel> = mutableListOf()

        for (apiExercise in apiExercises) {
            val exercise = ExerciseModel(apiExercise.name, apiExercise.description)

            for (apiSet in apiExercise.sets) {
                val sets = SetModel(apiSet)
                exercise.addSet(sets)
            }
            mutExercises.add(exercise)
        }
        return mutExercises.toList()
    }
}