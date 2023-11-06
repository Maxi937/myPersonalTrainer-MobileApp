package org.fitness.myfitnesstrainer.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.async
import org.fitness.myfitnesstrainer.R
import org.fitness.myfitnesstrainer.activities.AddWorkoutActivity
import org.fitness.myfitnesstrainer.activities.MainActivity
import org.fitness.myfitnesstrainer.adapters.GenericAdapter
import org.fitness.myfitnesstrainer.api.RetrofitInstance
import org.fitness.myfitnesstrainer.databinding.CardExerciseBinding
import org.fitness.myfitnesstrainer.databinding.CardWorkoutDetailsBinding
import org.fitness.myfitnesstrainer.databinding.FragmentWorkoutBinding
import org.fitness.myfitnesstrainer.models.ExerciseModel
import org.fitness.myfitnesstrainer.models.WorkoutModel
import org.fitness.myfitnesstrainer.service.NetworkResult
import timber.log.Timber
import kotlin.reflect.KFunction1


class FragmentWorkout : Fragment() {
    lateinit var activity: MainActivity
    lateinit var mAdapter: GenericAdapter<WorkoutModel>
    private lateinit var binding: FragmentWorkoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as MainActivity
        binding = FragmentWorkoutBinding.inflate(layoutInflater);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mAdapter = bindFragmentWorkout(activity.app.profile!!.workouts, binding, ::navigateStartWorkout)
        val view : View = binding.root;
        return view
    }

    private fun navigateStartWorkout(workout: WorkoutModel?) {
        val bundle = bundleOf("workout" to workout)
        findNavController().navigate(R.id.action_fragment_workout_to_fragmentStartWorkout, bundle)
    }

    private fun bindFragmentWorkout(data: List<WorkoutModel>, binding: FragmentWorkoutBinding, onClick: KFunction1<WorkoutModel?, Unit>): GenericAdapter<WorkoutModel> {
        var mAdapter = GenericAdapter(data)

        binding.idAddWorkout.setOnClickListener {
           addWorkout()
        }

        mAdapter.expressionViewHolderBinding = {workout, viewBinding->
            var view = viewBinding as CardWorkoutDetailsBinding
            view.txtWorkoutName.text = workout.name
            view.rvExercises.adapter = bindCardWorkoutBinding(workout, viewBinding)
            view.root.setOnClickListener {
                onClick(workout)
            }
            view.btnDeleteWorkout.setOnClickListener {
                lifecycleScope.async {
                    deleteWorkout(workout)
                    activity.app.refreshProfile().await()
                    mAdapter.deleteItemFromData(workout)
                }
            }
        }

        mAdapter.expressionOnCreateViewHolder = {viewGroup->
            CardWorkoutDetailsBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        }

        binding.rvWorkout.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
        return mAdapter
    }

    private suspend fun deleteWorkout(workout: WorkoutModel) {
        Timber.i("Delete Workout")
        val successDeferred = lifecycleScope.async {
            when (val response = RetrofitInstance.service.deleteWorkout(workout)) {
                is NetworkResult.Success -> {
                    Timber.i("Delete Exercise Success")
                }

                is NetworkResult.Error -> {
                    Timber.i("Delete Exercise Failure")
                    return@async false
                }

                is NetworkResult.Exception -> {
                    Timber.i("%s", response.e)
                    throw Exception("Ya done son")
                }
            }
        }.await()
    }
    private fun addWorkout() {
        val exercises: List<ExerciseModel> = activity.app.profile!!.exercises
        val mExercises = ArrayList<ExerciseModel>()

        for(e in exercises) {
            mExercises.add(e)
        }

        val intent = Intent(activity, AddWorkoutActivity::class.java)
        intent.putParcelableArrayListExtra("exercises", mExercises)
        activity?.startActivity(intent)
        activity.finish()
    }

    private fun bindCardWorkoutBinding(data: WorkoutModel, binding: CardWorkoutDetailsBinding): GenericAdapter<ExerciseModel> {
        val binding = binding
        var mAdapter = GenericAdapter(data.exercises)

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
        return mAdapter
    }
}

