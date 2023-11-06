package org.fitness.myfitnesstrainer.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WorkoutModel(
    val name: String = "",
    var exercises: List<ExerciseModel> = emptyList(),
    var date: String = ""
) : Parcelable {

    fun getVolume(): Float {
        var volume: Float = 0f

        for(exercise in exercises) {
            volume += exercise.getVolume()
        }
        return volume
    }

    fun addExercise(exercise: ExerciseModel) {
        val mExercises = mutableListOf<ExerciseModel>()

        for (item in exercises) {
            mExercises.add(item)
        }
        mExercises.add(exercise)

        this.exercises = mExercises.toList()
    }
}



