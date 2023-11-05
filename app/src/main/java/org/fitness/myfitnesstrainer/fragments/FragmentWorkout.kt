package org.fitness.myfitnesstrainer.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import org.fitness.myfitnesstrainer.activities.MainActivity
import org.fitness.myfitnesstrainer.databinding.FragmentWorkoutBinding
import org.fitness.myfitnesstrainer.models.WorkoutModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.fitness.myfitnesstrainer.R
import org.fitness.myfitnesstrainer.activities.AddWorkoutActivity
import org.fitness.myfitnesstrainer.activities.WorkoutActivity
import org.fitness.myfitnesstrainer.adapters.GenericAdapter
import org.fitness.myfitnesstrainer.databinding.CardExerciseBinding
import org.fitness.myfitnesstrainer.databinding.CardWorkoutDetailsBinding
import org.fitness.myfitnesstrainer.models.ExerciseModel
import kotlin.reflect.KFunction1


class FragmentWorkout : Fragment() {
    lateinit var activity: MainActivity
    private lateinit var binding: FragmentWorkoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as MainActivity
        binding = FragmentWorkoutBinding.inflate(layoutInflater);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWorkoutBinding.inflate(layoutInflater);
        bindFragmentWorkout(activity.profile.workouts, binding, ::navigateStartWorkout)
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
            val exercises: List<ExerciseModel> = activity.profile.exercises
            val mExercises = ArrayList<ExerciseModel>()

            for(e in exercises) {
                mExercises.add(e)
            }

            val intent = Intent(activity, AddWorkoutActivity::class.java)
            intent.putParcelableArrayListExtra("exercises", mExercises)
            activity?.startActivity(intent)
        }

        mAdapter.expressionViewHolderBinding = {workout, viewBinding->
            var view = viewBinding as CardWorkoutDetailsBinding
            view.txtWorkoutName.text = workout.name
            view.rvExercises.adapter = bindCardWorkoutBinding(workout, viewBinding)
            view.root.setOnClickListener {
                onClick(workout)
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

