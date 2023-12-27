package org.fitness.myfitnesstrainer.repository

import androidx.compose.material3.ColorScheme
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.fitness.myfitnesstrainer.api.MyFitnessClient
import org.fitness.myfitnesstrainer.data.local.models.ExerciseModel
import org.fitness.myfitnesstrainer.data.local.models.History
import org.fitness.myfitnesstrainer.data.local.models.Profile
import org.fitness.myfitnesstrainer.data.local.models.WorkoutModel
import org.fitness.myfitnesstrainer.data.remote.models.NetworkResult
import org.fitness.myfitnesstrainer.ui.theme.DarkColorScheme
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date

object MyFitnessRepository {
    private val scope = CoroutineScope(Dispatchers.IO)

    val theme: MutableLiveData<ColorScheme> by lazy {
        MutableLiveData<ColorScheme>(DarkColorScheme)
    }

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
                is NetworkResult.Success -> {
                    profile.isSuccess(response.data.profile)
                }

                is NetworkResult.Error -> profile.isError(code = response.code, response.errorMsg)
                is NetworkResult.Exception -> profile.isException(response.e)
            }
        }
    }

    fun getWorkouts(): List<WorkoutModel> {
        when (val p = profile.value) {
            is ResourceStatus.IsSuccess -> return p.data.workouts
            else -> return emptyList()
        }
    }

    fun getExercises(): List<ExerciseModel> {
        when (val p = profile.value) {
            is ResourceStatus.IsSuccess -> return p.data.exercises
            else -> return emptyList()
        }
    }

    fun deleteExercise(exercise: ExerciseModel) {
        scope.launch {
            deleteProfileExercise(exercise._id!!)
            MyFitnessClient.service.deleteExercise(exercise._id)
        }
    }

    private fun deleteProfileExercise(exerciseId: String) {
        when (val p = profile.value) {
            is ResourceStatus.IsSuccess -> {
                val exercise = p.data.exercises.find { it._id == exerciseId }
                val e = p.data.exercises.toMutableList()
                e.remove(exercise)
                Timber.i("Removed: $exercise")
                profile.update(p.data.copy(exercises = e))
                Timber.i("Updated: ${p.data.exercises}")
            }
            // Show message something went wrong or save deletion to another var and run it on reconnect not sure yet
            else -> {}
        }
    }

    fun createExercise(name: String, description: String, bodyPart: String) {
        val exercise = ExerciseModel(name = name, description = description, bodyPart = bodyPart)
        Timber.i("Creating Exercise: $exercise")

        scope.launch {
            when (val response = MyFitnessClient.service.addExercise(exercise)) {
                is NetworkResult.Success -> {
                    updateProfileExercises(response.data.exercise)
                }

                else -> {}
            }
        }
    }

    private fun updateProfileExercises(exercise: ExerciseModel) {
        when (val p = profile.value) {
            is ResourceStatus.IsSuccess -> {
                val exercises = p.data.exercises.toMutableList()
                exercises.add(exercise)
                profile.update(p.data.copy(exercises = exercises))
            }
            // Show message something went wrong or save deletion to another var and run it on reconnect not sure yet
            else -> {}
        }
    }

    fun createWorkout(name: String, exercises: List<ExerciseModel>) {
        val workout = WorkoutModel(name = name, exercises = exercises)
        Timber.i("Creating Workout: $workout")

        scope.launch {
            when (val response = MyFitnessClient.service.addWorkout(workout)) {
                is NetworkResult.Success -> {
                    Timber.i("New Workout Created: ${response.data.workout}")
                    updateProfileWorkouts(response.data.workout)
                }

                else -> {}
            }
        }
    }

    fun deleteWorkout(workout: WorkoutModel) {
        scope.launch {
            deleteProfileWorkout(workout._id!!)
            MyFitnessClient.service.deleteWorkout(workout)
        }
    }

    private fun deleteProfileWorkout(workoutId: String) {
        when (val p = profile.value) {
            is ResourceStatus.IsSuccess -> {
                val workout = p.data.workouts.find { it._id == workoutId }
                val w = p.data.workouts.toMutableList()
                w.remove(workout)
                profile.update(p.data.copy(workouts = w))
                Timber.i("Updated: ${p.data.exercises}")
            }
            // Show message something went wrong or save deletion to another var and run it on reconnect not sure yet
            else -> {}
        }
    }


    private fun updateProfileWorkouts(workout: WorkoutModel) {
        when (val p = profile.value) {
            is ResourceStatus.IsSuccess -> {
                val workouts = p.data.workouts.toMutableList()
                workouts.add(workout)
                profile.update(p.data.copy(workouts = workouts))
            }
            // Show message something went wrong or save deletion to another var and run it on reconnect not sure yet
            else -> {}
        }
    }

    fun completeWorkout(workout: WorkoutModel, exercisesCompleted: List<ExerciseModel>) {
        scope.launch {
            completeProfileWorkout(workout, exercisesCompleted)
            MyFitnessClient.service.addHistory(workout._id!!, exercisesCompleted)
        }
    }

    private fun completeProfileWorkout(
        workout: WorkoutModel,
        exercisesCompleted: List<ExerciseModel>
    ) {
        val date = SimpleDateFormat("dd-MMM-YY").format(Date())
        val thistory: History = History(exercisesCompleted, date.toString())

        when (val p = profile.value) {
            is ResourceStatus.IsSuccess -> {
                val workout = p.data.workouts.find { it._id == workout._id }

                if (workout != null) {
                    if (workout.history != null) {
                        val history = workout.history.toMutableList()
                        history.add(thistory)
                        val w = workout.copy(history = history)
                        val workouts = p.data.workouts.toMutableList()
                        workouts.remove(workout)
                        workouts.add(w)
                        profile.update(p.data.copy(workouts = workouts))
                    } else {
                        val w = workout.copy(history = listOf(thistory))
                        val workouts = p.data.workouts.toMutableList()
                        workouts.remove(workout)
                        workouts.add(w)
                        profile.update(p.data.copy(workouts = workouts))
                    }
                }

            }
            // Show message something went wrong or save deletion to another var and run it on reconnect not sure yet
            else -> {}
        }
    }

    fun clearProfile() {
        profile.postValue(null)
    }
}