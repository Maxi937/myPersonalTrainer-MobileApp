package org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.AddResources

import androidx.lifecycle.ViewModel
import org.fitness.myfitnesstrainer.data.local.models.ExerciseModel
import org.fitness.myfitnesstrainer.repository.MyFitnessRepository

class addResourcesViewModel : ViewModel() {
    val myFitnessRepository = MyFitnessRepository
    var selectedExercises: MutableList<ExerciseModel> = mutableListOf()


    fun createWorkout(name: String) {
        myFitnessRepository.createWorkout(name, selectedExercises)
    }

    fun getChecked(exercise: ExerciseModel): Boolean {
        val e = selectedExercises.find { it._id == exercise._id }
        return e != null
    }

    fun getExercises(): List<ExerciseModel> {
        return MyFitnessRepository.getExercises()
    }


}