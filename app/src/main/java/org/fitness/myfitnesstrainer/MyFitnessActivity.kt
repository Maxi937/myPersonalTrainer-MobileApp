package org.fitness.myfitnesstrainer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import org.fitness.myfitnesstrainer.ui.screens.Login.LoginScreen
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme
import timber.log.Timber

class MyFitnessActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("My Fitness Activity Started")
        setContent {
            MyFitnessTrainerTheme {
                Surface {
                    LoginScreen()
                }

//                val navController = rememberNavController()
//                Scaffold(
//                    bottomBar = { BottomNavBar(navController) }
//                ) {
//                    BottomNavRoutes(navController = navController) }
            }
        }
    }
}
