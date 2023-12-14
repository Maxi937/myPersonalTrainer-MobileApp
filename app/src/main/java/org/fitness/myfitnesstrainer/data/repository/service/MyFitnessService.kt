package org.fitness.myfitnesstrainer.data.repository.service

import org.fitness.myfitnesstrainer.data.repository.api.ApiHandler
import org.fitness.myfitnesstrainer.data.repository.api.ApiServices
import org.fitness.myfitnesstrainer.data.repository.api.NetworkResult
import org.fitness.myfitnesstrainer.data.remote.models.AuthRequest
import org.fitness.myfitnesstrainer.data.remote.models.SignupRequest
import org.fitness.myfitnesstrainer.data.remote.models.Token
import org.fitness.myfitnesstrainer.data.remote.models.apiExercise
import org.fitness.myfitnesstrainer.data.remote.models.apiProfile
import org.fitness.myfitnesstrainer.data.remote.models.apiStatus
import org.fitness.myfitnesstrainer.data.remote.models.apiWorkout
import org.fitness.myfitnesstrainer.data.local.models.ExerciseModel
import org.fitness.myfitnesstrainer.data.local.models.WorkoutModel

interface MyFitnessService {
    suspend fun getProfile(): NetworkResult<apiProfile>

    suspend fun addWorkout(workoutModel: WorkoutModel): NetworkResult<apiWorkout>

    suspend fun authenticate(authRequest: AuthRequest): NetworkResult<Token>

    suspend fun signup(signupRequest: SignupRequest): NetworkResult<apiStatus>

    suspend fun addExercise(exercise: ExerciseModel): NetworkResult<apiExercise>

    suspend fun deleteWorkout(workoutModel: WorkoutModel): NetworkResult<apiStatus>

    suspend fun deleteExercise(exercise: ExerciseModel): NetworkResult<apiStatus>

    suspend fun addHistory(workout: WorkoutModel): NetworkResult<apiWorkout>
}

class MyFitnessServiceImp(private val apiServices: ApiServices) : MyFitnessService, ApiHandler {
    override suspend fun getProfile(): NetworkResult<apiProfile> = handleApi { apiServices.getProfile() }

    override suspend fun addWorkout(workoutModel: WorkoutModel): NetworkResult<apiWorkout> = handleApi { apiServices.addWorkout(workoutModel) }

    override suspend fun authenticate(authRequest: AuthRequest): NetworkResult<Token> = handleApi { apiServices.authenticate(authRequest) }

    override suspend fun signup(signupRequest: SignupRequest): NetworkResult<apiStatus> = handleApi { apiServices.signup(signupRequest) }

    override suspend fun addExercise(exercise: ExerciseModel): NetworkResult<apiExercise> = handleApi { apiServices.addExercise(exercise) }

    override suspend fun deleteWorkout(workoutModel: WorkoutModel): NetworkResult<apiStatus> = handleApi { apiServices.deleteWorkout(workoutModel) }

    override suspend fun deleteExercise(exercise: ExerciseModel): NetworkResult<apiStatus> = handleApi { apiServices.deleteExercise(exercise) }

    override suspend fun addHistory(workout: WorkoutModel): NetworkResult<apiWorkout> = handleApi { apiServices.addHistory(workout) }
}
