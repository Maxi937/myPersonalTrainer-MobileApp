package org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.AddResources

import org.fitness.myfitnesstrainer.data.local.models.ExerciseModel
import org.fitness.myfitnesstrainer.data.local.models.WorkoutModel

fun searchWorkouts(workouts: List<WorkoutModel>, search: String?): List<WorkoutModel> {
    var result: List<WorkoutModel> = workouts

    if(search != "" && search != null) {
        result = result.filter { search.lowercase() in it.name.lowercase() }
    }
    return result
}

fun searchExercises(exercises: List<ExerciseModel>, search: String?): List<ExerciseModel> {
    var result: List<ExerciseModel> = exercises

    if(search != "" && search != null) {
        result = result.filter { search.lowercase() in it.bodyPart.lowercase() }
    }
    return result
}