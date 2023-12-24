package org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Profile


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import org.fitness.myfitnesstrainer.data.local.models.Profile
import org.fitness.myfitnesstrainer.ui.composables.Screen.Screen
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessText.MyFitnessSubscript1
import org.fitness.myfitnesstrainer.ui.preview.ProfilePreviewParameterProvider
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme


@Composable
fun ProfileScreen(profile: Profile) {
    Screen {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Localized description", Modifier.size(70.dp)
            )
            Spacer(modifier = Modifier.size(10.dp))
            Column {
                Text(text = profile.userDetails.fname)
                Spacer(modifier = Modifier.size(3.dp))
                MyFitnessSubscript1(text = "${profile.workouts.size.toString()} Workouts")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview(@PreviewParameter(ProfilePreviewParameterProvider::class) profile: Profile) {
    MyFitnessTrainerTheme {
        ProfileScreen(profile = profile)
    }
}