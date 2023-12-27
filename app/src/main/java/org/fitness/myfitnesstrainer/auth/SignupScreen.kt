package org.fitness.myfitnesstrainer.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.fitness.myfitnesstrainer.ui.composables.ButtonWithSpinner.ButtonWithSpinner
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessInputFields.MyFitnessTextInput
import org.fitness.myfitnesstrainer.ui.composables.MyFitnessText.MyFitnessH1
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    onCancel: () -> Unit,
    onSubmit: (email: String, password: String, fname: String, lname: String) -> Unit
) {
    var isInProgress by remember { mutableStateOf(false) }
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var fname = remember { mutableStateOf("") }
    var lname = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    MyFitnessTrainerTheme {
        Surface {
            Column {
                Row(modifier=Modifier.fillMaxWidth().padding(10.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    MyFitnessH1(title = "Sign Up")
                    Row(Modifier
                        .fillMaxWidth()
                        .padding(10.dp), Arrangement.End, Alignment.CenterVertically) {
                        IconButton(onClick = { onCancel() }) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Localized description",
                                tint = MaterialTheme.colorScheme.inversePrimary
                            )
                        }

                    }

                }
            }
            Column(
                modifier
                    .fillMaxSize()
                    .padding(35.dp), Arrangement.Center) {
                MyFitnessTextInput(text = fname, placeholder = "First Name")
                Spacer(Modifier.size(6.dp))
                MyFitnessTextInput(text = lname, placeholder = "Last Name")
                Spacer(Modifier.size(6.dp))
                MyFitnessTextInput(text = email, placeholder = "Email")
                Spacer(Modifier.size(6.dp))
                MyFitnessTextInput(text = password, placeholder = "Password")

            }
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                ButtonWithSpinner(
                    modifier = Modifier.fillMaxWidth(), buttonText = "Sign Up", isInProgress
                ) {
                    isInProgress = true
                    coroutineScope.launch {
                        onSubmit(email.value, password.value, fname.value, lname.value)
                        isInProgress = false
                    }
                }
            }
        }
    }
}




