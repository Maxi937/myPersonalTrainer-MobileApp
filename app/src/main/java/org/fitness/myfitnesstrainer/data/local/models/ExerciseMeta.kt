package org.fitness.myfitnesstrainer.data.local.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseMeta (val defaultWeight: Double = 50.toDouble()) : Parcelable