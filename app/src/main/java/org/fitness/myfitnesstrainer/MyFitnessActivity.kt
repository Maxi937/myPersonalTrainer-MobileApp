package org.fitness.myfitnesstrainer

import android.accounts.Account
import android.accounts.AccountManager
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.runBlocking
import org.fitness.myfitnesstrainer.auth.AccountGeneral
import org.fitness.myfitnesstrainer.auth.AuthManager
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme
import org.fitness.myfitnesstrainer.ui.composables.BottomNavBar.BottomNavBar
import org.fitness.myfitnesstrainer.ui.composables.BottomNavBar.BottomNavRoutes
import org.fitness.myfitnesstrainer.ui.composables.TopNavBar.MyFitnessTopNavBar
import timber.log.Timber
import org.fitness.myfitnesstrainer.ui.screens.App.App


class MyFitnessActivity : ComponentActivity() {
    private lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        authManager = AuthManager(this)

        authManager.getTokenForAccountCreateIfNeeded()
    }

    public override fun onStart() {
        super.onStart()

        setContent {
            MyFitnessTrainerTheme {
                App(authManager)
            }
        }

//        authManager.loggedIn.observe(this) { loggedIn ->
//            if (loggedIn) {
//                setContent {
//                    MyFitnessTrainerTheme {
//                        App(authManager)
//                    }
//                }
//            }
//        }
    }
}