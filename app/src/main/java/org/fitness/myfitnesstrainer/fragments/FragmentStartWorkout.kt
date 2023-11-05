package org.fitness.myfitnesstrainer.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import org.fitness.myfitnesstrainer.activities.WorkoutActivity
import org.fitness.myfitnesstrainer.adapters.GenericAdapter
import org.fitness.myfitnesstrainer.databinding.CardExerciseBinding
import org.fitness.myfitnesstrainer.databinding.FragmentStartWorkoutBinding
import org.fitness.myfitnesstrainer.models.ExerciseModel
import org.fitness.myfitnesstrainer.models.WorkoutModel

class FragmentStartWorkout : Fragment() {
    private lateinit var workout: WorkoutModel
    private lateinit var binding: FragmentStartWorkoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workout = it.getParcelable("workout", WorkoutModel::class.java)!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentStartWorkoutBinding.inflate(layoutInflater);

        binding.txtWorkoutName.text = workout.name
        binding.rvExercises.adapter = bindCardWorkoutBinding(workout, binding)

        binding.btnStartWorkout.setOnClickListener {
            startWorkoutActivity()
        }
        val view : View = binding.root;
        return view
    }

    private fun bindCardWorkoutBinding(data: WorkoutModel, binding: FragmentStartWorkoutBinding): GenericAdapter<ExerciseModel> {
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

    private fun startWorkoutActivity() {
        val intent = Intent(activity, WorkoutActivity::class.java)
        intent.putExtra("workout", workout)
        activity?.startActivity(intent)
        activity?.supportFragmentManager?.popBackStack()
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