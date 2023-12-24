package org.fitness.myfitnesstrainer.data.local.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetails(
    val email: String = "",
    val fname: String = "",
    val lname: String = ""
) : Parcelable