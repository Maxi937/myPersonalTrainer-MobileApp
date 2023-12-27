package org.fitness.myfitnesstrainer.ui.composables.TopNavBar

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import org.fitness.myfitnesstrainer.auth.AuthManager

@Composable
fun MyFitnessTopBarMenu(logout: () -> Unit, expanded: Boolean, setExpanded: () -> Unit) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { setExpanded() },
        modifier = Modifier.background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        DropdownMenuItem(
            text = { Text("Logout", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },
            onClick = { logout() }
        )
    }
}