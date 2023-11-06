package org.fitness.myfitnesstrainer.api.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class apiStatus(
    val status: String,
) : Parcelable