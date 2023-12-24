package org.fitness.myfitnesstrainer.api

import org.fitness.myfitnesstrainer.data.remote.models.NetworkResult
import org.fitness.myfitnesstrainer.data.local.models.AuthRequest
import org.fitness.myfitnesstrainer.data.local.models.SignupRequest
import org.fitness.myfitnesstrainer.data.remote.models.Token
import org.fitness.myfitnesstrainer.data.remote.models.apiProfile
import org.fitness.myfitnesstrainer.data.remote.models.apiStatus
import org.fitness.myfitnesstrainer.data.local.models.ExerciseModel
import org.fitness.myfitnesstrainer.data.local.models.WorkoutModel

interface MyFitnessService {
    suspend fun getProfile(): NetworkResult<apiProfile>

    suspend fun addWorkout(workoutModel: WorkoutModel): NetworkResult<WorkoutModel>

    suspend fun authenticate(authRequest: AuthRequest): NetworkResult<Token>

    suspend fun signup(signupRequest: SignupRequest): NetworkResult<apiStatus>

    suspend fun addExercise(exercise: ExerciseModel): NetworkResult<ExerciseModel>

    suspend fun deleteWorkout(workoutModel: WorkoutModel): NetworkResult<apiStatus>

    suspend fun deleteExercise(exercise: ExerciseModel): NetworkResult<apiStatus>

    suspend fun addHistory(workout: WorkoutModel): NetworkResult<WorkoutModel>
}

class MyFitnessServiceImp(private val apiServices: ApiServices) : MyFitnessService, ApiHandler {
    override suspend fun getProfile(): NetworkResult<apiProfile> = handleApi { apiServices.getProfile() }

    override suspend fun addWorkout(workoutModel: WorkoutModel): NetworkResult<WorkoutModel> = handleApi { apiServices.addWorkout(workoutModel) }

    override suspend fun authenticate(authRequest: AuthRequest): NetworkResult<Token> = handleApi { apiServices.authenticate(authRequest) }

    override suspend fun signup(signupRequest: SignupRequest): NetworkResult<apiStatus> = handleApi { apiServices.signup(signupRequest) }

    override suspend fun addExercise(exercise: ExerciseModel): NetworkResult<ExerciseModel> = handleApi { apiServices.addExercise(exercise) }

    override suspend fun deleteWorkout(workoutModel: WorkoutModel): NetworkResult<apiStatus> = handleApi { apiServices.deleteWorkout(workoutModel) }

    override suspend fun deleteExercise(exercise: ExerciseModel): NetworkResult<apiStatus> = handleApi { apiServices.deleteExercise(exercise) }

    override suspend fun addHistory(workout: WorkoutModel): NetworkResult<WorkoutModel> = handleApi { apiServices.addHistory(workout) }
}
