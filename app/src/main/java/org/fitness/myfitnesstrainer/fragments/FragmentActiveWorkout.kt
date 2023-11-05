package org.fitness.myfitnesstrainer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.fitness.myfitnesstrainer.R
import org.fitness.myfitnesstrainer.adapters.ActiveWorkoutAdapter
import org.fitness.myfitnesstrainer.databinding.FragmentActiveWorkoutBinding
import org.fitness.myfitnesstrainer.models.ExerciseModel
import org.fitness.myfitnesstrainer.models.WorkoutModel

class FragmentActiveWorkout : Fragment() {
    private lateinit var workout: WorkoutModel
    private lateinit var binding: FragmentActiveWorkoutBinding
    private var completedExercises = emptyList<ExerciseModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workout = it.getParcelable("workout", WorkoutModel::class.java)!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentActiveWorkoutBinding.inflate(layoutInflater)
        var mAdapter = ActiveWorkoutAdapter(workout)


        binding.txtWorkoutName.text = workout.name

        binding.rvActiveExercises.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

        binding.btnFinishWorkout.setOnClickListener {
            val completedWorkout = mAdapter.completeWorkout()
            val bundle = bundleOf("workout" to completedWorkout)
            findNavController().navigate(R.id.action_fragment_active_workout_to_fragmentEndWorkout, bundle)
        }

        val view : View = binding.root;
        return view
    }


    companion object {
        @JvmStatic
        fun newInstance(workoutModel: WorkoutModel) = FragmentActiveWorkout().apply {
            arguments = Bundle().apply {
                putParcelable("workout", workoutModel)
            }
        }
    }
}