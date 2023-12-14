package org.fitness.myfitnesstrainer.data.remote.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.fitness.myfitnesstrainer.data.local.models.Profile
@Parcelize
data class apiProfile(
    val profile: xProfile,
    val status: String
) : Parcelable