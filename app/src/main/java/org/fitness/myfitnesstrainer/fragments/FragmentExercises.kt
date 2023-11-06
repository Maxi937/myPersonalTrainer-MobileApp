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
import org.fitness.myfitnesstrainer.activities.AddExerciseActivity
import org.fitness.myfitnesstrainer.activities.AddWorkoutActivity
import org.fitness.myfitnesstrainer.activities.MainActivity
import org.fitness.myfitnesstrainer.adapters.GenericAdapter
import org.fitness.myfitnesstrainer.api.RetrofitInstance
import org.fitness.myfitnesstrainer.databinding.CardExerciseDetailsBinding
import org.fitness.myfitnesstrainer.databinding.FragmentExercisesBinding
import org.fitness.myfitnesstrainer.models.ExerciseModel
import org.fitness.myfitnesstrainer.service.NetworkResult
import timber.log.Timber


class FragmentExercises : Fragment() {
    lateinit var activity: MainActivity
    private lateinit var binding: FragmentExercisesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as MainActivity
        binding = FragmentExercisesBinding.inflate(layoutInflater);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bindFragmentExercise(activity.app.profile!!.exercises, binding)
        val view : View = binding.root;
        return view
    }

    private fun bindFragmentExercise(data: List<ExerciseModel>, binding: FragmentExercisesBinding): GenericAdapter<ExerciseModel> {
        var mAdapter = GenericAdapter(data)

        binding.idAddExercise.setOnClickListener {
            val intent = Intent(activity, AddExerciseActivity::class.java)
            activity?.startActivity(intent)
            activity.finish()
        }

        mAdapter.expressionViewHolderBinding = {exercise, viewBinding->
            var view = viewBinding as CardExerciseDetailsBinding
            view.txtCardExerciseDetailsExerciseName.text = exercise.name
            view.btnDeleteExercise.visibility = View.VISIBLE
            view.btnDeleteExercise.setOnClickListener {
                lifecycleScope.async {
                    deleteExercise(exercise)
                    activity.app.refreshProfile().await()
                    mAdapter.deleteItemFromData(exercise)
                }
            }
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

    suspend fun deleteExercise(exercise: ExerciseModel) {
        Timber.i("Delete Exercise")
        val successDeferred = lifecycleScope.async {
            when (val response = RetrofitInstance.service.deleteExercise(exercise)) {
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

    companion object {
        @JvmStatic
        fun newInstance() = FragmentExercises()
    }
}