package org.fitness.myfitnesstrainer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.async
import org.fitness.myfitnesstrainer.adapters.GenericAdapter
import org.fitness.myfitnesstrainer.api.RetrofitInstance
import org.fitness.myfitnesstrainer.databinding.CardExerciseBinding
import org.fitness.myfitnesstrainer.databinding.FragmentEndWorkoutBinding
import org.fitness.myfitnesstrainer.main.MainApp
import org.fitness.myfitnesstrainer.models.WorkoutModel
import org.fitness.myfitnesstrainer.api.NetworkResult
import timber.log.Timber

//// TODO: INFORMATION IS GOING IN - ADAPTER NEEDS TO BE UPDATED
class FragmentEndWorkout : Fragment() {
    private lateinit var workout: WorkoutModel
    private lateinit var binding: FragmentEndWorkoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workout = it.getParcelable("workout", WorkoutModel::class.java)!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEndWorkoutBinding.inflate(layoutInflater);
        binding.txtWorkoutName.text = workout.name
        binding.txtVolume.text = workout.getVolume().toString()

        val mAdapter = GenericAdapter(workout.exercises)

        mAdapter.expressionViewHolderBinding = {exercise, viewBinding->
            var view = viewBinding as CardExerciseBinding
            view.txtCardExerciseExercise.text = exercise.name
            view.txtExerciseSets.text = exercise.getNumberOfSets().toString()
        }

        mAdapter.expressionOnCreateViewHolder = {viewGroup->
            CardExerciseBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        }

        binding.rvExercises.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

        binding.btnEndWorkout.setOnClickListener {
            finishWorkoutActivity()
        }

        val view : View = binding.root;
        return view
    }

    private fun finishWorkoutActivity() {
        lifecycleScope.async {
            val success = addHistory(workout)
            if(success) {
                (activity?.application as MainApp).refreshProfile()
                activity?.finish()
            }
            else {
                activity?.finish()
            }
        }
    }

    private suspend fun addHistory(workoutModel: WorkoutModel): Boolean {
        Timber.i("Adding History")
        val addWorkoutDeferred = lifecycleScope.async {
            when (val response = RetrofitInstance.service.addHistory(workoutModel)) {
                is NetworkResult.Success -> {
                    Timber.i("Add History Success")
                    return@async true
                }

                is NetworkResult.Error -> {
                    Timber.i("Add History Failure: %s", response.code)
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

    companion object {
        @JvmStatic
        fun newInstance(workoutModel: WorkoutModel) = FragmentStartWorkout().apply {
            arguments = Bundle().apply {
                putParcelable("workout", workoutModel)
            }
        }
    }
}