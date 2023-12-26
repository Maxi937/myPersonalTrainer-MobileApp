package org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Exercise

import androidx.lifecycle.ViewModel
import org.fitness.myfitnesstrainer.auth.AuthManager
import org.fitness.myfitnesstrainer.repository.MyFitnessRepository

class ExerciseViewModel: ViewModel() {
    val profileRepository = MyFitnessRepository

    fun deleteExercise(exerciseId: String) {
        profileRepository.deleteExercise(exerciseId)
    }
}