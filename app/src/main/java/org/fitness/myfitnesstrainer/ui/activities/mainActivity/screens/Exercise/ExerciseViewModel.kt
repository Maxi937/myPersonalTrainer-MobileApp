package org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Exercise

import androidx.lifecycle.ViewModel
import org.fitness.myfitnesstrainer.auth.AuthManager
import org.fitness.myfitnesstrainer.data.local.models.ExerciseModel
import org.fitness.myfitnesstrainer.repository.MyFitnessRepository

class ExerciseViewModel: ViewModel() {
    val myFitnessRepository = MyFitnessRepository

    fun deleteExercise(exercise: ExerciseModel) {
        myFitnessRepository.deleteExercise(exercise)
    }
}