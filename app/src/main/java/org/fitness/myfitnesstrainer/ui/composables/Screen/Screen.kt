package org.fitness.myfitnesstrainer.ui.composables.Screen

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Screen(
    scrollable: Boolean = true,
    arrangement: Arrangement.Vertical = Arrangement.Top,
    modifier: Modifier = Modifier,
    bottomContent: @Composable() () -> Unit = { null },
    content: @Composable() () -> Unit) {

    val scrollableModifier = if(scrollable) { Modifier.verticalScroll(rememberScrollState()) } else { Modifier }

    Column(modifier = Modifier
        .padding(10.dp)
        .fillMaxSize()
        .then(scrollableModifier)
        .then(modifier), verticalArrangement = arrangement) {
        content()
    }
    if (bottomContent != null) {
        Column(modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom) {
            bottomContent()
        }

    }
}