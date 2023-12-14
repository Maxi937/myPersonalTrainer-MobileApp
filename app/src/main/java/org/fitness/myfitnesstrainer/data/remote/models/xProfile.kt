package org.fitness.myfitnesstrainer.data.remote.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.fitness.myfitnesstrainer.data.local.models.Profile
@Parcelize
data class xProfile(
    val exercises: List<apiExercise> = emptyList(),
    val workouts: List<apiWorkout> = emptyList(),
    val history: List<apiWorkout> = emptyList(),
    val userDetails: UserDetails = UserDetails()
) : Parcelable