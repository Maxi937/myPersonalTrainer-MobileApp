package org.fitness.myfitnesstrainer.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseModel(
    val name: String,
    val description: String,
    var sets: List<SetModel> = emptyList(),
) : Parcelable {

    fun addSet(set: SetModel) {
        val mSets = mutableListOf<SetModel>()

        for (set in sets) {
            mSets.add(set)
        }
        mSets.add(set)

        this.sets = mSets.toList()
    }

    fun getNumberOfSets(): Int {
        return sets.size
    }

    fun getVolume(): Float {
        var volume: Float = 0F

        for(set in sets) {
            volume += set.getVolume()
        }

        return volume
    }

}
