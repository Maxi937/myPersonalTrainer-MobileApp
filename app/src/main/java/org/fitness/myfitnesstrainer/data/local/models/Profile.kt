package org.fitness.myfitnesstrainer.data.local.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile(
    val exercises: List<ExerciseModel> = emptyList(),
    val workouts: List<WorkoutModel> = emptyList(),
    val history: List<WorkoutModel> = emptyList(),
    val userDetails: UserDetails = UserDetails()
) : Parcelable


//    private fun unwrapHistory(apiWorkouts: List<apiWorkout>): List<WorkoutModel> {
//        var mutWorkouts: MutableList<WorkoutModel> = mutableListOf()
//
//        for (apiWorkout in apiWorkouts) {
//            val workout: WorkoutModel = WorkoutModel(apiWorkout.name, unwrapExercises(apiWorkout.exercises), apiWorkout.date)
//            mutWorkouts.add(workout)
//        }
//        return mutWorkouts.toList()
//    }
//
//    private fun unwrapWorkouts(apiWorkouts: List<apiWorkout>): List<WorkoutModel> {
//        var mutWorkouts: MutableList<WorkoutModel> = mutableListOf()
//
//        for (apiWorkout in apiWorkouts) {
//            val workout: WorkoutModel = WorkoutModel(apiWorkout.name, unwrapExercises(apiWorkout.exercises))
//            mutWorkouts.add(workout)
//        }
//        return mutWorkouts.toList()
//    }
//    private fun unwrapExercises(apiExercises: List<apiExercise>): List<ExerciseModel> {
//        var mutExercises: MutableList<ExerciseModel> = mutableListOf()
//
//        for (apiExercise in apiExercises) {
//            val exercise = ExerciseModel(apiExercise.name, apiExercise.description)
//
//            for (apiSet in apiExercise.sets) {
//                val sets = SetModel(apiSet)
//                exercise.addSet(sets)
//            }
//            mutExercises.add(exercise)
//        }
//        return mutExercises.toList()
//    }