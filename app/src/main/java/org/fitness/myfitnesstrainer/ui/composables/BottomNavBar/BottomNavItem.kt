package org.fitness.myfitnesstrainer.ui.composables.BottomNavBar

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import org.fitness.myfitnesstrainer.R

sealed class BottomNavItem( val route: String, @StringRes val titleResId: Int, val icon: ImageVector) {
    object Profile : BottomNavItem("Profile", R.string.profile, Icons.Default.AccountCircle)
    object Workout : BottomNavItem("Workout", R.string.activity_workout, Icons.Default.Edit)
    object Exercise : BottomNavItem("Exercise", R.string.activity_exercise, Icons.Default.List)
}