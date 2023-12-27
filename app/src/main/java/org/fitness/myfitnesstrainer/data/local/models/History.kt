package org.fitness.myfitnesstrainer.data.local.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class History(
    val exercises: List<ExerciseModel> = emptyList(),
    val createdAt: String? = null
) : Parcelable