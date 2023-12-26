package org.fitness.myfitnesstrainer.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.fitness.myfitnesstrainer.api.MyFitnessClient
import org.fitness.myfitnesstrainer.data.local.models.ExerciseModel
import org.fitness.myfitnesstrainer.data.local.models.Profile
import org.fitness.myfitnesstrainer.data.local.models.WorkoutModel
import org.fitness.myfitnesstrainer.data.remote.models.NetworkResult
import timber.log.Timber
import java.util.Date

object MyFitnessRepository {
    private val scope = CoroutineScope(Dispatchers.IO)

    private val profile: Resource<Profile> by lazy {
        Resource(null)
    }

    fun getProfileResource(): Resource<Profile> {
        return profile
    }

    fun refresh() {
        profile.isLoading()

        scope.launch {
            when (val response = MyFitnessClient.service.getProfile()) {
                is NetworkResult.Success -> { profile.isSuccess(response.data.profile) }
                is NetworkResult.Error -> profile.isError(code=response.code, response.errorMsg)
                is NetworkResult.Exception -> profile.isException(response.e)
            }
        }
    }

    fun getExercises(): List<ExerciseModel> {
        when (val p = profile.value) {
            is ResourceStatus.IsSuccess -> {
                return p.data.exercises
            }
            // Show message something went wrong or save deletion to another var and run it on reconnect not sure yet
            else -> {
                return emptyList()
            }
        }
    }



    fun deleteExercise(exerciseId: String) {
        when(val p = profile.value) {
            is ResourceStatus.IsSuccess -> {
                val exercise = p.data.exercises.find { it._id == exerciseId }
                val e = p.data.exercises.toMutableList()
                e.remove(exercise)
                Timber.i("Removed: $exercise")
                profile.update(p.data.copy(exercises = e))
                // local profile is update
                // TODO: add call to delete data on db
                Timber.i("Updated: ${p.data.exercises}")
            }
            // Show message something went wrong or save deletion to another var and run it on reconnect not sure yet
            else -> { }
        }
    }

    fun createExercise(exercise: ExerciseModel) {
        when(val p = profile.value) {
            is ResourceStatus.IsSuccess -> {
                val exercises = p.data.exercises.toMutableList()
                exercises.add(exercise)
                profile.update(p.data.copy(exercises = exercises))
            }
            // Show message something went wrong or save deletion to another var and run it on reconnect not sure yet
            else -> { }
        }
    }

    fun deleteWorkout(workoutId: String) {
        when(val p = profile.value) {
            is ResourceStatus.IsSuccess -> {
                val workout = p.data.workouts.find { it._id == workoutId }
                val w = p.data.workouts.toMutableList()
                w.remove(workout)
                profile.update(p.data.copy(workouts = w))
                // local profile is update
                // TODO: add call to delete data on db
                Timber.i("Updated: ${p.data.exercises}")
            }
            // Show message something went wrong or save deletion to another var and run it on reconnect not sure yet
            else -> { }
        }
    }

    fun createWorkout(workout: WorkoutModel) {
        when(val p = profile.value) {
            is ResourceStatus.IsSuccess -> {
                val workouts = p.data.workouts.toMutableList()
                workouts.add(workout)
                profile.update(p.data.copy(workouts = workouts))
            }
            // Show message something went wrong or save deletion to another var and run it on reconnect not sure yet
            else -> { }
        }
    }

    fun completeWorkout(workoutId: String) {
        when(val p = profile.value) {
            is ResourceStatus.IsSuccess -> {
                val workout = p.data.workouts.find { it._id == workoutId }

                if (workout?.history != null) {
                    val history = workout.history.toMutableList()
                    history.add(Date())
                    val w = workout.copy(history = history)
                    val workouts = p.data.workouts.toMutableList()
                    workouts.add(w)
                    profile.update(p.data.copy(workouts = workouts))
                }
                else {
                    // new history
                }

            }
            // Show message something went wrong or save deletion to another var and run it on reconnect not sure yet
            else -> { }
        }
    }

    fun clearProfile() {
        profile.postValue(null)
    }
}