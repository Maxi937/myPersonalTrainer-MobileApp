package org.fitness.myfitnesstrainer.service

import org.fitness.myfitnesstrainer.api.ApiHandler
import org.fitness.myfitnesstrainer.api.ApiServices
import org.fitness.myfitnesstrainer.api.NetworkResult
import org.fitness.myfitnesstrainer.api.models.AuthRequest
import org.fitness.myfitnesstrainer.api.models.Token
import org.fitness.myfitnesstrainer.api.models.apiExercise
import org.fitness.myfitnesstrainer.api.models.apiProfile
import org.fitness.myfitnesstrainer.api.models.apiStatus
import org.fitness.myfitnesstrainer.api.models.apiWorkout
import org.fitness.myfitnesstrainer.models.ExerciseModel
import org.fitness.myfitnesstrainer.models.WorkoutModel
import retrofit2.Response
import retrofit2.http.Body

interface MyFitnessService {
    suspend fun getProfile(): NetworkResult<apiProfile>

    suspend fun addWorkout(workoutModel: WorkoutModel): NetworkResult<apiWorkout>

    suspend fun authenticate(authRequest: AuthRequest): NetworkResult<Token>

    suspend fun signup(authRequest: AuthRequest): NetworkResult<Token>

    suspend fun addExercise(exercise: ExerciseModel): NetworkResult<apiExercise>

    suspend fun deleteWorkout(workoutModel: WorkoutModel): NetworkResult<apiStatus>

    suspend fun deleteExercise(exercise: ExerciseModel): NetworkResult<apiStatus>

    suspend fun addHistory(workout: WorkoutModel): NetworkResult<apiWorkout>
}

class MyFitnessServiceImp(private val apiServices: ApiServices) : MyFitnessService, ApiHandler {
    override suspend fun getProfile(): NetworkResult<apiProfile> = handleApi { apiServices.getProfile() }

    override suspend fun addWorkout(workoutModel: WorkoutModel): NetworkResult<apiWorkout> = handleApi { apiServices.addWorkout(workoutModel) }

    override suspend fun authenticate(authRequest: AuthRequest): NetworkResult<Token> = handleApi { apiServices.authenticate(authRequest) }

    override suspend fun signup(authRequest: AuthRequest): NetworkResult<Token> = handleApi { apiServices.signup(authRequest) }

    override suspend fun addExercise(exercise: ExerciseModel): NetworkResult<apiExercise> = handleApi { apiServices.addExercise(exercise) }

    override suspend fun deleteWorkout(workoutModel: WorkoutModel): NetworkResult<apiStatus> = handleApi { apiServices.deleteWorkout(workoutModel) }

    override suspend fun deleteExercise(exercise: ExerciseModel): NetworkResult<apiStatus> = handleApi { apiServices.deleteExercise(exercise) }

    override suspend fun addHistory(workout: WorkoutModel): NetworkResult<apiWorkout> = handleApi { apiServices.addHistory(workout) }
}
