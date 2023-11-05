package org.fitness.myfitnesstrainer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import org.fitness.myfitnesstrainer.databinding.CardActiveExercisesBinding
import org.fitness.myfitnesstrainer.databinding.CardActiveSetsBinding
import org.fitness.myfitnesstrainer.models.ExerciseModel
import org.fitness.myfitnesstrainer.models.SetModel
import org.fitness.myfitnesstrainer.models.WorkoutModel

class ActiveWorkoutAdapter(data: WorkoutModel) : GenericAdapter<ExerciseModel>(data.exercises) {
    val workout = data

    val childAdapters : MutableList<ActiveSetsAdapter> = mutableListOf()

    fun completeWorkout(): WorkoutModel {
        val completedExercises: MutableList<ExerciseModel> = mutableListOf()

        for(adapter in childAdapters) {
            val completedExercise = adapter.completeExercise()

            if (completedExercise.sets.isNotEmpty()) {
                completedExercises.add(completedExercise)
            }
        }
        return WorkoutModel(workout.name, completedExercises)
    }

    override var expressionViewHolderBinding: ((ExerciseModel, ViewBinding) -> Unit)? = { exercise, viewBinding->
        var view = viewBinding as CardActiveExercisesBinding
        val bAdapter = ActiveSetsAdapter(exercise, viewBinding)

        view.txtCardActiveExercisesExerciseName.text = exercise.name
        view.rvActiveSets.adapter = bAdapter

        viewBinding.rvActiveSets.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = bAdapter
        }
        childAdapters.add(bAdapter)
    }

    override var expressionOnCreateViewHolder: ((ViewGroup) -> ViewBinding)? = {viewGroup->
        CardActiveExercisesBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
    }

    inner class ActiveSetsAdapter(data: ExerciseModel, binding: CardActiveExercisesBinding) : GenericAdapter<SetModel>(data.sets) {
        val exercise = data
        val checkedSets : MutableList<SetModel> = mutableListOf()

        fun completeExercise(): ExerciseModel {
            return ExerciseModel(exercise.name, exercise.description, checkedSets)
        }

        override var expressionViewHolderBinding: ((SetModel, ViewBinding) -> Unit)? = { set, viewBinding->
            var view = viewBinding as CardActiveSetsBinding
            view.txtSetReps.text = set.reps.size.toString()
            view.inputWeight.hint = 0.toString()
            view.checkedTextView.setOnClickListener() {
                checkedSets.add(set)
            }
        }

        override var expressionOnCreateViewHolder: ((ViewGroup) -> ViewBinding)? = {viewGroup->
            CardActiveSetsBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        }
    }
}

