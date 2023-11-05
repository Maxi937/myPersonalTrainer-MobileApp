package org.fitness.myfitnesstrainer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import org.fitness.myfitnesstrainer.databinding.CardActiveExercisesBinding
import org.fitness.myfitnesstrainer.databinding.CardActiveSetsBinding
import org.fitness.myfitnesstrainer.databinding.CardExerciseDetailsBinding
import org.fitness.myfitnesstrainer.models.ExerciseModel
import org.fitness.myfitnesstrainer.models.SetModel
import org.fitness.myfitnesstrainer.models.WorkoutModel
import timber.log.Timber

class AddWorkoutAdapter(data: List<ExerciseModel>) : GenericAdapter<ExerciseModel>(data) {
    private val checkedExercises: MutableList<ExerciseModel> = mutableListOf()

    fun getChecked(): List<ExerciseModel> {
        return checkedExercises.toList()
    }

    override var expressionViewHolderBinding: ((ExerciseModel, ViewBinding) -> Unit)? = { exercise, viewBinding->
        var view = viewBinding as CardExerciseDetailsBinding
        view.txtCardExerciseDetailsExerciseName.text = exercise.name
        view.checkAddExercise.visibility = View.VISIBLE
        view.root.setOnClickListener {
            Timber.i("Adding to adapter: %s", exercise)
            checkedExercises.add(exercise)
            view.checkAddExercise.isChecked = true
        }
    }

    override var expressionOnCreateViewHolder: ((ViewGroup) -> ViewBinding)? = {viewGroup->
        CardExerciseDetailsBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
    }



}

