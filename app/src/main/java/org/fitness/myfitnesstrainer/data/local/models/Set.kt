package org.fitness.myfitnesstrainer.data.local.models
import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SetModel(val reps: List<Float> = emptyList()) : Parcelable {
    fun getNumberOfReps(): Int {
        return reps.size
    }

    fun getVolume(): Float {
        var volume: Float = 0F

        for(rep in reps) {
            volume += rep.toFloat()
        }

        return volume
    }
}
