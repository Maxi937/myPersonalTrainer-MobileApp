package org.fitness.myfitnesstrainer.api.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.fitness.myfitnesstrainer.models.Profile
@Parcelize
data class apiProfile(
    val profile: xProfile,
    val status: String
) : Parcelable