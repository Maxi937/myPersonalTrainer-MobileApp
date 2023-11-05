package org.fitness.myfitnesstrainer.api.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class apiExercise(
    val __v: Int,
    val _id: String,
    val bodyPart: String,
    val sets: List<List<Float>>,
    val createdAt: String,
    val description: String,
    val name: String,
    val updatedAt: String
) : Parcelable