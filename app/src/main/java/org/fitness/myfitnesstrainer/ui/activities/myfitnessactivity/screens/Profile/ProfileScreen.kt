package org.fitness.myfitnesstrainer.ui.activities.myfitnessactivity.screens.Profile



import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme


@Composable
fun ProfileScreen(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello sssssssssssssssssssssssssssssssssssssssssssssssssssssssss $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyFitnessTrainerTheme {
        ProfileScreen("hello")
    }
}