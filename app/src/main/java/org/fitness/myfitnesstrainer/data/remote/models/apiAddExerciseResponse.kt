package org.fitness.myfitnesstrainer.data.remote.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.fitness.myfitnesstrainer.data.local.models.ExerciseModel


@Parcelize
data class ApiAddExerciseResponse(
    val status: String,
    val exercise: ExerciseModel
) : Parcelable