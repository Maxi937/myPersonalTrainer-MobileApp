package org.fitness.myfitnesstrainer.api.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class apiWorkout(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val createdBy: String,
    val exercises: List<apiExercise>,
    val name: String,
    val updatedAt: String
) : Parcelable