package org.fitness.myfitnesstrainer.api.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.fitness.myfitnesstrainer.models.Profile
@Parcelize
data class xProfile(
    val exercises: List<apiExercise> = emptyList(),
    val workouts: List<apiWorkout> = emptyList(),
    val userDetails: UserDetails = UserDetails()
) : Parcelable