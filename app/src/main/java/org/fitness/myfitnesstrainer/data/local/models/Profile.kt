package org.fitness.myfitnesstrainer.data.local.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Profile(
    val exercises: List<ExerciseModel> = emptyList(),
    val workouts: List<WorkoutModel> = emptyList(),
    val userDetails: UserDetails = UserDetails()
) : Parcelable