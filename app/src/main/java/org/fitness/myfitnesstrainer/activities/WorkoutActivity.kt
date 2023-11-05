package org.fitness.myfitnesstrainer.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import org.fitness.myfitnesstrainer.R
import org.fitness.myfitnesstrainer.databinding.ActivityWorkoutBinding
import org.fitness.myfitnesstrainer.models.WorkoutModel


class WorkoutActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityWorkoutBinding
    private lateinit var workout: WorkoutModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workout = intent.extras?.getParcelable("workout", WorkoutModel::class.java)!!
        binding = ActivityWorkoutBinding.inflate(layoutInflater)


        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(binding.navHostFragmentActiveWorkout.id) as NavHostFragment
        this.navController = navHostFragment.navController

        val bundle = bundleOf("workout" to workout)
        navHostFragment.navController.setGraph(R.navigation.nav_graph_active_workout, intent.extras)
    }

}



