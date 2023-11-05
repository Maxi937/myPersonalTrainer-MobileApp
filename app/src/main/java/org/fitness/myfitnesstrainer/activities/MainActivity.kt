package org.fitness.myfitnesstrainer.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.fitness.myfitnesstrainer.R
import org.fitness.myfitnesstrainer.api.RetrofitInstance
import org.fitness.myfitnesstrainer.databinding.ActivityMainBinding
import org.fitness.myfitnesstrainer.models.Profile
import org.fitness.myfitnesstrainer.models.WorkoutModel
import org.fitness.myfitnesstrainer.service.NetworkResult
import timber.log.Timber


// TODO: At the moment, the use of the nav controller overrides the back button functionality to return to whatever the default start is set to - the Dashboard fragment
// Change this if unwanted - probably would prefer to just minimise / exit app

// Navigation
// NOTE: The Nav graph ID's and the Menu Item ID's must match

class MainActivity : AppCompatActivity() {
    lateinit var profile : Profile
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalScope.async {
            profile = getProfile()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)

        // set view to activity main.xml - contains layout for fragment container and bottom nav bar
        setContentView(binding.root)

        // Settings Bar Menu
        setSupportActionBar(binding.menuTopSettings)

        // Set up nav host *findbyfragmentid used R.id.navhostfragment in docs, think this is old syntax - instead I am using binding
        val navHostFragment = supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        this.navController = navHostFragment.navController

        // Attaches nav controller to the bottom nav in the main activity xml
        binding.bottomNav.setupWithNavController(this.navController)
    }

    // This inflates the top_menu_settings.xml menu items into the top settings bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu_settings, menu);
        return super.onCreateOptionsMenu(menu)
    }

    // Uses the nav controller to navigate if an option is selected from the settings menu
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val navController = findNavController(binding.navHostFragment.id)
//        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
//    }

    @SuppressLint("TimberArgCount")
    private suspend fun getProfile(): Profile {
        val profileDeferred = GlobalScope.async {
            when (val response = RetrofitInstance.service.getProfile()) {
                is NetworkResult.Success -> {
                    Timber.i("Profile Success")
                    return@async Profile(response.data.profile)
                }

                is NetworkResult.Error -> {
                    Timber.i("Http Err", response.errorMsg)
                    throw Exception("Bad Request")
                }

                is NetworkResult.Exception -> {
                    Timber.i("Not Connected to Internet")
                    throw Exception("Unable to connect to server")
                }
            }
        }
        return profileDeferred.await()
    }
}



