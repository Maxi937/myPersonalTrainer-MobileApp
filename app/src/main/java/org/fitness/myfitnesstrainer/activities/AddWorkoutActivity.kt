package org.fitness.myfitnesstrainer.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.fitness.myfitnesstrainer.adapters.AddWorkoutAdapter
import org.fitness.myfitnesstrainer.adapters.GenericAdapter
import org.fitness.myfitnesstrainer.api.RetrofitInstance
import org.fitness.myfitnesstrainer.api.models.AuthRequest
import org.fitness.myfitnesstrainer.databinding.ActivityAddWorkoutBinding
import org.fitness.myfitnesstrainer.databinding.CardExerciseDetailsBinding
import org.fitness.myfitnesstrainer.main.MainApp
import org.fitness.myfitnesstrainer.models.ExerciseModel
import org.fitness.myfitnesstrainer.models.WorkoutModel
import org.fitness.myfitnesstrainer.service.NetworkResult
import timber.log.Timber

class AddWorkoutActivity : AppCompatActivity() {
    lateinit var app : MainApp
    private lateinit var binding: ActivityAddWorkoutBinding
    private var exercises = ArrayList<ExerciseModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exercises = intent.extras?.getParcelableArrayList("exercises", ExerciseModel::class.java)!!;
        app = application as MainApp
        binding = ActivityAddWorkoutBinding.inflate(layoutInflater)
        var adapter = bindActivityAddWorkout(exercises, binding)

        binding.btnAddWorkout.setOnClickListener {
            val addedExercises = adapter.getChecked()
            Timber.i("Added E %s", addedExercises)
            val newWorkout = WorkoutModel(binding.txtInputNewWorkoutName.text.toString(), addedExercises)

            lifecycleScope.async {
                val success = addWorkout(newWorkout)
                if(success) {
                    app.refreshProfile()
                    return@async endAddWorkout()
                }
            }
        }
        setContentView(binding.root)
    }

    private fun endAddWorkout() {
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
        finish()
    }

    private fun bindActivityAddWorkout(data: List<ExerciseModel>, binding: ActivityAddWorkoutBinding): AddWorkoutAdapter {
        var mAdapter = AddWorkoutAdapter(data)

        binding.rvCardUserExercises.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
        return mAdapter
    }

    private suspend fun addWorkout(workoutModel: WorkoutModel): Boolean {
        Timber.i("Adding Workout")
        val addWorkoutDeferred = lifecycleScope.async {
            when (val response = RetrofitInstance.service.addWorkout(workoutModel)) {
                is NetworkResult.Success -> {
                    Timber.i("Add Workout Success")
                    Timber.i("Workout Added: %s", response.data)
                    return@async true
                }

                is NetworkResult.Error -> {
                    Timber.i("Add Workout Failure")
                    return@async false
                }

                is NetworkResult.Exception -> {
                    Timber.i("%s", response.e)
                    throw Exception("Ya done son")
                }
            }
        }
        return addWorkoutDeferred.await()
    }
}



