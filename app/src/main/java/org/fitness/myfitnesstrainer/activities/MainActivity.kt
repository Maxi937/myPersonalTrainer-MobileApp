package org.fitness.myfitnesstrainer.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import org.fitness.myfitnesstrainer.R
import org.fitness.myfitnesstrainer.databinding.ActivityMainBinding
import org.fitness.myfitnesstrainer.fragments.FragmentWorkout
import org.fitness.myfitnesstrainer.main.MainApp

class MainActivity : AppCompatActivity() {
    lateinit var app: MainApp
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MainApp
//        app.refreshProfile()

        binding = ActivityMainBinding.inflate(layoutInflater)

        // set view to activity main.xml - contains layout for fragment container and bottom nav bar
        setContentView(binding.root)

        // Settings Bar Menu
        setSupportActionBar(binding.menuTopSettings)

        // Set up nav host *findbyfragmentid used R.id.navhostfragment in docs, think this is old syntax - instead I am using binding
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        this.navController = navHostFragment.navController

        // Attaches nav controller to the bottom nav in the main activity xml
        binding.bottomNav.setupWithNavController(this.navController)
    }

    // This inflates the top_menu_settings.xml menu items into the top settings bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu_settings, menu);
        return super.onCreateOptionsMenu(menu)
    }

    fun refresh() {
        recreate()
    }
}





