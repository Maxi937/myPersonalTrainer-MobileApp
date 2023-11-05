package org.fitness.myfitnesstrainer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import org.fitness.myfitnesstrainer.activities.MainActivity
import org.fitness.myfitnesstrainer.adapters.GenericAdapter
import org.fitness.myfitnesstrainer.databinding.CardExerciseDetailsBinding
import org.fitness.myfitnesstrainer.databinding.FragmentExercisesBinding
import org.fitness.myfitnesstrainer.models.ExerciseModel


class FragmentExercises : Fragment() {
    lateinit var activity: MainActivity
    private lateinit var binding: FragmentExercisesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as MainActivity
        binding = FragmentExercisesBinding.inflate(layoutInflater);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentExercisesBinding.inflate(layoutInflater);
        bindFragmentExercise(activity.profile.exercises, binding)
        val view : View = binding.root;
        return view
    }

    private fun bindFragmentExercise(data: List<ExerciseModel>, binding: FragmentExercisesBinding): GenericAdapter<ExerciseModel> {
        var mAdapter = GenericAdapter(data)

        mAdapter.expressionViewHolderBinding = {exercise, viewBinding->
            var view = viewBinding as CardExerciseDetailsBinding
            view.txtCardExerciseDetailsExerciseName.text = exercise.name
        }

        mAdapter.expressionOnCreateViewHolder = {viewGroup->
            CardExerciseDetailsBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        }

        binding.rvExerciseCards.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
        return mAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentExercises()
    }
}