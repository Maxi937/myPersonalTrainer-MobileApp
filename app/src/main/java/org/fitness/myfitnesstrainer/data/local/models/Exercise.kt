package org.fitness.myfitnesstrainer.data.local.models

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

fun unwrapSets(sets: List<List<Float>>?): List<SetModel> {
    val result = ArrayList<SetModel>()

    if (sets != null) {
        for (set in sets) {
            result.add(SetModel(set))
        }
    }

    return result.toList()
}

@Parcelize
data class ExerciseModel(
    val __v: Int? = null,
    val _id: String? = null,
    val bodyPart: String,
    var sets: List<List<Float>> = emptyList(),
    val createdAt: String? = null,
    val description: String,
    val name: String,
    val updatedAt: String? = null
): Parcelable {
    fun addSet(set: List<Float>) {
        val mSets = this.sets.toMutableList()
        mSets.add(set)
        this.sets = mSets.toList()
    }

    fun getNumberOfSets(): Int {
        return sets.size
    }

    fun getVolume(): Float {
        var volume: Float = 0F

        for(set in sets) {
            volume += set.sum()
        }
        return volume
    }
}
