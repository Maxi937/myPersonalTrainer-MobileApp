package org.fitness.myfitnesstrainer.ui.composables.ButtonWithSpinner

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ButtonWithSpinner(modifier: Modifier = Modifier, buttonText: String, showLoader: Boolean = false, onClick: () -> Unit) {
    Button(modifier = modifier.height(48.dp), onClick = onClick, colors = ButtonDefaults.buttonColors(Color.Magenta)) {
        AnimatedContent(targetState = showLoader, label = "btn",
            transitionSpec = {
            if (targetState > initialState) {
                (slideInVertically { height -> height } + fadeIn()).togetherWith(slideOutVertically { height -> -height } + fadeOut())
            } else {
                (slideInVertically { height -> -height } + fadeIn()).togetherWith(slideOutVertically { height -> height } + fadeOut())
            }.using(
                SizeTransform(clip = false)
            )
        }) { targetIsSelected ->
            if (targetIsSelected)
                    Row() {
                        CircularProgressIndicator(modifier= Modifier.size(32.dp), color = Color.White)
                    }
            else
                Text(text = buttonText)
        }
    }
}