package org.fitness.myfitnesstrainer.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.async
import org.fitness.myfitnesstrainer.R
import org.fitness.myfitnesstrainer.activities.AddExerciseActivity
import org.fitness.myfitnesstrainer.activities.MainActivity
import org.fitness.myfitnesstrainer.adapters.GenericAdapter
import org.fitness.myfitnesstrainer.api.NetworkResult
import org.fitness.myfitnesstrainer.api.RetrofitInstance
import org.fitness.myfitnesstrainer.databinding.CardExerciseBinding
import org.fitness.myfitnesstrainer.databinding.CardExerciseDetailsBinding
import org.fitness.myfitnesstrainer.databinding.CardWorkoutDetailsBinding
import org.fitness.myfitnesstrainer.databinding.FragmentExercisesBinding
import org.fitness.myfitnesstrainer.databinding.FragmentHistoryBinding
import org.fitness.myfitnesstrainer.models.ExerciseModel
import org.fitness.myfitnesstrainer.models.WorkoutModel
import timber.log.Timber


class FragmentHistory : Fragment() {
    lateinit var activity: MainActivity
    private lateinit var binding: FragmentHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as MainActivity
        binding = FragmentHistoryBinding.inflate(layoutInflater);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindFragmentHistory(activity.app.profile!!.history, binding)
        val view : View = binding.root;
        return view
    }

    private fun bindFragmentHistory(data: List<WorkoutModel>, binding: FragmentHistoryBinding): GenericAdapter<WorkoutModel> {
        var mAdapter = GenericAdapter(data)


        mAdapter.expressionViewHolderBinding = {workout, viewBinding->
            var view = viewBinding as CardWorkoutDetailsBinding
            view.txtWorkoutName.text = workout.name
            view.rvExercises.adapter = bindCardWorkoutBinding(workout, viewBinding)
            view.btnDeleteWorkout.text = workout.date
            view.btnDeleteWorkout.isClickable = false
        }

        mAdapter.expressionOnCreateViewHolder = {viewGroup->
            CardWorkoutDetailsBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        }

        binding.rvHistoryWorkouts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
        return mAdapter
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

    companion object {
        @JvmStatic
        fun newInstance() = FragmentHistory()
    }
}