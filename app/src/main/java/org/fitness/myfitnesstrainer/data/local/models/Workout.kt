package org.fitness.myfitnesstrainer.data.local.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class WorkoutModel(
    val __v: Int? = null,
    val _id: String? = null,
    val createdAt: String? = null,
    val createdBy: String? = null,
    val exercises: List<ExerciseModel> = mutableListOf(),
    val name: String,
    val updatedAt: String? = null,
    val history: List<History> = emptyList(),
) : Parcelable {
    fun getVolume(): Float {
        var volume: Float = 0f

        for (exercise in exercises) {
            volume += exercise.getVolume()
        }
        return volume
    }
}






