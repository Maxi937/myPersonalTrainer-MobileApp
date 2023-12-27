package org.fitness.myfitnesstrainer.data.local.models

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import timber.log.Timber

@Parcelize
data class ExerciseModel(
    val __v: Int? = null,
    val _id: String? = null,
    val bodyPart: String,
    var sets: MutableList<List<Float>> = mutableListOf(),
    val createdAt: String? = null,
    val description: String,
    val name: String,
    val meta: ExerciseMeta? = ExerciseMeta(),
    val updatedAt: String? = null
) : Parcelable {
    fun addSet(set: List<Float>) {
        this.sets.add(set)
    }

    fun addDefaultSet() {
        this.sets.add(listOf(50F,50F,50F,50F))
    }

    fun removeSet(set: List<Float>) {
        Timber.i("Searching: $set")
        this.sets.remove(set)
    }

    fun getNumberOfSets(): Int {
        return sets.size
    }

    fun getVolume(): Float {
        var volume: Float = 0F

        for (set in sets) {
            volume += set.sum()
        }
        return volume
    }
}
