package org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Profile

import androidx.lifecycle.ViewModel
import org.fitness.myfitnesstrainer.data.local.models.ExerciseModel
import org.fitness.myfitnesstrainer.data.local.models.WorkoutModel
import org.fitness.myfitnesstrainer.repository.MyFitnessRepository
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale


class ProfileViewModel : ViewModel() {
    val myFitnessRepository: MyFitnessRepository = MyFitnessRepository

    fun getMostRecentWorkout(): WorkoutModel? {
        val workouts = MyFitnessRepository.getWorkouts()
        val format = SimpleDateFormat("dd-MMM-YYYY", Locale.ENGLISH)
        var mostRecentDate: Date? = null
        var result: WorkoutModel? = null


        for (workout in workouts) {
            for (h in workout.history) {
                var dt = format.parse(h.createdAt)
                if (mostRecentDate == null) {
                    mostRecentDate = dt
                    result = workout.copy(exercises = h.exercises)
                } else {
                    if (dt.after(mostRecentDate) || dt == mostRecentDate) {
                        result = workout.copy(exercises = h.exercises)
                    }
                }
            }
        }
        return result
    }

    fun getFavouriteExercise(): Pair<String, ExerciseModel?> {
        val workouts = MyFitnessRepository.getWorkouts()
        var counted: Int = 0
        var result: ExerciseModel? = null
        val exercises = MyFitnessRepository.getExercises()

        for (e in exercises) {
            var count = 0

            for (workout in workouts) {
                for (h in workout.history) {
                    for (he in h.exercises) {
                        if (he._id == e._id) {
                            count += 1
                        }
                    }
                }
            }
            if (count > counted || counted == 0) {
                counted = count
                result = e
            }
        }
        if (counted == 1) {
            return Pair("Performed $counted Time", result)
        }
        else {
            return return Pair("Performed $counted Times", result)
        }

    }

    fun getNumberOfWorkoutsCompleted(): String {
        val workouts = myFitnessRepository.getWorkouts()
        var number: Int = 0

        if (workouts != null) {
            for (workout in workouts) {
                if (workout.history != null && workout.history.isNotEmpty()) {
                    number += workout.history.size
                }
            }
        }
        if (number == 1) {
            return "$number Workout Completed"
        } else {
            return "$number Workouts Completed"
        }
    }

    fun getNumberOfWorkoutsCompletedThisWeek(): String {
        val format: SimpleDateFormat = SimpleDateFormat("dd-MMM-YYYY", Locale.ENGLISH)
        val c: Calendar = Calendar.getInstance()
        c.time = Date()
        val currentWeek = c.get(Calendar.WEEK_OF_YEAR)

        val workouts = myFitnessRepository.getWorkouts()
        var number = 0

        if (workouts != null) {
            for (workout in workouts) {
                if (workout.history != null && workout.history.isNotEmpty()) {
                    for (i in workout.history) {
                        var dt: Date = format.parse(i.createdAt)
                        c.time = dt
                        var createdDateWeek: Int = c[Calendar.WEEK_OF_YEAR]
                        Timber.i(dt.toString())
                        Timber.i(currentWeek.toString())
                        Timber.i(createdDateWeek.toString())

                        if (currentWeek == createdDateWeek) {
                            number += 1
                        }
                    }

                }
            }
        }
        if (number == 1) {
            return "$number Workout Completed"
        } else {
            return "$number Workouts Completed"
        }
    }
}