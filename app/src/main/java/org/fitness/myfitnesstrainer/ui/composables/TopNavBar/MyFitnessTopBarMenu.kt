package org.fitness.myfitnesstrainer.ui.composables.TopNavBar

import android.widget.Toast
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.fitness.myfitnesstrainer.auth.AuthManager

@Composable
fun MyFitnessTopBarMenu(authManager: AuthManager, expanded: Boolean, setExpanded: () -> Unit) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { setExpanded() }
    ) {
        DropdownMenuItem(
            text = { Text("Logout") },
            onClick = { authManager.logout() }
        )
    }
}