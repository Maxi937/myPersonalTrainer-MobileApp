package org.fitness.myfitnesstrainer.ui.composables.Screen

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
fun Screen(arrangement: Arrangement.Vertical = Arrangement.Top, modifier: Modifier = Modifier, content: @Composable() () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()).then(modifier)
        , verticalArrangement = arrangement
    ) {
        content()
    }
}