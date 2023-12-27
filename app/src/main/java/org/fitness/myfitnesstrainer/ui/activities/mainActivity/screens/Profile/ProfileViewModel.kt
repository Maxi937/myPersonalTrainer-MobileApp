package org.fitness.myfitnesstrainer.ui.activities.mainActivity.screens.Profile

import androidx.lifecycle.ViewModel
import org.fitness.myfitnesstrainer.repository.MyFitnessRepository
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ProfileViewModel: ViewModel() {
    val myFitnessRepository: MyFitnessRepository = MyFitnessRepository
    fun getNumberOfWorkoutsCompleted(): String {
        val workouts = myFitnessRepository.getWorkouts()
        var number: Int = 0

        if(workouts != null) {
            for (workout in workouts) {
                if(workout.history != null && workout.history.isNotEmpty()) {
                    number += workout.history.size
                }
            }
        }
        if(number == 1) {
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

        if(workouts != null) {
            for (workout in workouts) {
                if(workout.history != null && workout.history.isNotEmpty()) {
                    for (i in workout.history) {
                        var dt: Date = format.parse(i.createdAt)
                        c.time = dt
                        var createdDateWeek: Int = c[Calendar.WEEK_OF_YEAR]
                        Timber.i(dt.toString())
                        Timber.i(currentWeek.toString())
                        Timber.i(createdDateWeek.toString())

                        if (currentWeek == createdDateWeek){
                            number += 1
                        }
                    }

                }
            }
        }
        if(number == 1) {
            return "$number Workout Completed"
        } else {
            return "$number Workouts Completed"
        }
    }
}