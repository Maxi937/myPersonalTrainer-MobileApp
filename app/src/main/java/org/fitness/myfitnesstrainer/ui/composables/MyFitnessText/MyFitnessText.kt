package org.fitness.myfitnesstrainer.ui.composables.MyFitnessText

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyFitnessH1(title: String) {
    Text(text = "$title", fontSize = 32.sp, modifier = Modifier.padding(0.dp, 20.dp))
}

@Composable
fun MyFitnessH2(title: String) {
    Text(text = "$title", fontSize = 24.sp, modifier = Modifier.padding(0.dp, 16.dp))
}

@Composable
fun MyFitnessH3(title: String, modifier: Modifier = Modifier.padding(0.dp, 10.dp)) {
    Text(text = "$title", fontSize = 18.sp, modifier = modifier)
}

@Composable
fun MyFitnessSubscript1(text: String, modifier: Modifier = Modifier) {
    Text(text = "$text", fontSize = 14.sp, modifier = modifier, color = MaterialTheme.colorScheme.scrim)
}

@Composable
fun MyFitnessH3Subscript1(title: String, text: String) {
    Column(modifier = Modifier.padding(0.dp, 10.dp)) {
        MyFitnessH3(title, Modifier.padding(0.dp))
        MyFitnessSubscript1(text)
    }

}