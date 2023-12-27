package org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Exercise

import androidx.lifecycle.ViewModel
import org.fitness.myfitnesstrainer.auth.AuthManager
import org.fitness.myfitnesstrainer.data.local.models.ExerciseModel
import org.fitness.myfitnesstrainer.data.local.models.WorkoutModel
import org.fitness.myfitnesstrainer.repository.MyFitnessRepository

class ExerciseViewModel: ViewModel() {
    val myFitnessRepository = MyFitnessRepository

    fun deleteExercise(exercise: ExerciseModel) {
        myFitnessRepository.deleteExercise(exercise)
    }

    fun sort(exercises: List<ExerciseModel>, sort: Boolean, search: String?): List<ExerciseModel> {
        var result: List<ExerciseModel> = exercises

        when(sort) {
            true -> {}
            else -> result = sortExercisesAlphabetically(result)
        }

        if(search != "" && search != null) {
            result = result.filter { search.lowercase() in it.bodyPart.lowercase() }
        }

        return result
    }

    fun sortExercisesAlphabetically(exercises: List<ExerciseModel>): List<ExerciseModel> {
        return exercises.sortedBy { it.name }
    }
}