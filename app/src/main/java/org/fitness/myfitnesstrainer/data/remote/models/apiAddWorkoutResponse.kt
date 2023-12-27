package org.fitness.myfitnesstrainer.data.remote.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.fitness.myfitnesstrainer.data.local.models.ExerciseModel
import org.fitness.myfitnesstrainer.data.local.models.WorkoutModel


@Parcelize
data class ApiAddWorkoutResponse(
    val status: String,
    val workout: WorkoutModel
) : Parcelable