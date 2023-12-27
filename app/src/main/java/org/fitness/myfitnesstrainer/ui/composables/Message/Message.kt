package org.fitness.myfitnesstrainer.ui.composables.Message

import android.app.Activity
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun showMessageError(msg: String) {
    val context = LocalContext.current
    Toast.makeText(context as Activity, msg, Toast.LENGTH_SHORT).show()
}