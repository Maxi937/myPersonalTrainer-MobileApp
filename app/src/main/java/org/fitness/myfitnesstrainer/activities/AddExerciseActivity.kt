package org.fitness.myfitnesstrainer.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.fitness.myfitnesstrainer.api.RetrofitInstance
import org.fitness.myfitnesstrainer.databinding.ActivityAddExerciseBinding
import org.fitness.myfitnesstrainer.main.MainApp
import org.fitness.myfitnesstrainer.models.ExerciseModel
import org.fitness.myfitnesstrainer.models.SetModel
import org.fitness.myfitnesstrainer.api.NetworkResult
import timber.log.Timber

class AddExerciseActivity : AppCompatActivity() {
    lateinit var app : MainApp
    private lateinit var binding: ActivityAddExerciseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MainApp

        binding = ActivityAddExerciseBinding.inflate(layoutInflater)

        binding.btnAddExercise.setOnClickListener {
            val newExercise = ExerciseModel(binding.txtInputNewExerciseName.text.toString(), binding.txtInputNewExerciseDescription.text.toString())
            val numberOfSets = binding.txtInputNewExerciseSets.text.toString().toInt()
            val numberOfReps = binding.txtInputNewExerciseReps.text.toString().toInt()

            val reps = List<Float>(numberOfReps) {
                0.0f
            }

            for(i in 0..numberOfSets) {
                newExercise.addSet(SetModel(reps))
            }

            Timber.i("New Exercise %s", newExercise)

            GlobalScope.async {
                val success = addExercise(newExercise)
                if(success) {
                    app.refreshProfile()
                    return@async endAddExercise()
                }
            }
        }
        setContentView(binding.root)
    }

    private fun endAddExercise() {
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
        finish()
    }

    private suspend fun addExercise(exerciseModel: ExerciseModel): Boolean {
        Timber.i("Adding Workout")
        val addExerciseDeferred = lifecycleScope.async {
            when (val response = RetrofitInstance.service.addExercise(exerciseModel)) {
                is NetworkResult.Success -> {
                    Timber.i("Add Exercise Success")
                    Timber.i("Exercise Added: %s", response.data)
                    return@async true
                }

                is NetworkResult.Error -> {
                    Timber.i("Add Exercise Failure")
                    return@async false
                }

                is NetworkResult.Exception -> {
                    Timber.i("%s", response.e)
                    throw Exception("Ya done son")
                }
            }
        }
        return addExerciseDeferred.await()
    }
}



