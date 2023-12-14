package org.fitness.myfitnesstrainer.data.remote.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class apiStatus(
    val status: String,
) : Parcelable