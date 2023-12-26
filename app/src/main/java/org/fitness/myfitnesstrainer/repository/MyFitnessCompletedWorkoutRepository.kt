package org.fitness.myfitnesstrainer.repository

import org.fitness.myfitnesstrainer.data.local.models.WorkoutModel

// This only exists because the navgraph for jetpack compose took away the ability to pass parcelables
object MyFitnessCompletedWorkoutRepository {
    lateinit var completedWorkout: WorkoutModel
}