package org.fitness.myfitnesstrainer.api

import org.fitness.myfitnesstrainer.data.remote.models.NetworkResult
import org.fitness.myfitnesstrainer.data.local.models.AuthRequest
import org.fitness.myfitnesstrainer.data.local.models.SignupRequest
import org.fitness.myfitnesstrainer.data.remote.models.Token
import org.fitness.myfitnesstrainer.data.remote.models.apiProfile
import org.fitness.myfitnesstrainer.data.remote.models.apiStatus
import org.fitness.myfitnesstrainer.data.local.models.ExerciseModel
import org.fitness.myfitnesstrainer.data.local.models.WorkoutModel
import org.fitness.myfitnesstrainer.data.remote.models.ApiAddExerciseResponse
import org.fitness.myfitnesstrainer.data.remote.models.ApiAddWorkoutResponse

interface MyFitnessService {
    suspend fun getProfile(): NetworkResult<apiProfile>

    suspend fun addWorkout(workoutModel: WorkoutModel): NetworkResult<ApiAddWorkoutResponse>

    suspend fun updateWorkout(workoutId: String, workoutModel: WorkoutModel): NetworkResult<ApiAddWorkoutResponse>

    suspend fun authenticate(authRequest: AuthRequest): NetworkResult<Token>

    suspend fun signup(signupRequest: SignupRequest): NetworkResult<Token>

    suspend fun addExercise(exercise: ExerciseModel): NetworkResult<ApiAddExerciseResponse>

    suspend fun deleteWorkout(workoutModel: WorkoutModel): NetworkResult<apiStatus>

    suspend fun deleteExercise(exerciseId: String): NetworkResult<apiStatus>

    suspend fun addHistory(workoutId: String, exercises: List<ExerciseModel>): NetworkResult<ApiAddWorkoutResponse>
}

class MyFitnessServiceImp(private val apiServices: ApiServices) : MyFitnessService, ApiHandler {
    override suspend fun getProfile(): NetworkResult<apiProfile> = handleApi { apiServices.getProfile() }

    override suspend fun addWorkout(workoutModel: WorkoutModel): NetworkResult<ApiAddWorkoutResponse> = handleApi { apiServices.addWorkout(workoutModel) }

    override suspend fun updateWorkout(workoutId: String, workoutModel: WorkoutModel): NetworkResult<ApiAddWorkoutResponse> = handleApi { apiServices.updateWorkout(workoutId, workoutModel) }

    override suspend fun authenticate(authRequest: AuthRequest): NetworkResult<Token> = handleApi { apiServices.authenticate(authRequest) }

    override suspend fun signup(signupRequest: SignupRequest): NetworkResult<Token> = handleApi { apiServices.signup(signupRequest) }

    override suspend fun addExercise(exercise: ExerciseModel): NetworkResult<ApiAddExerciseResponse> = handleApi { apiServices.addExercise(exercise) }

    override suspend fun deleteWorkout(workoutModel: WorkoutModel): NetworkResult<apiStatus> = handleApi { apiServices.deleteWorkout(workoutModel) }

    override suspend fun deleteExercise(exerciseId: String): NetworkResult<apiStatus> = handleApi { apiServices.deleteExercise(exerciseId) }

    override suspend fun addHistory(workoutId: String, exercises: List<ExerciseModel>): NetworkResult<ApiAddWorkoutResponse> = handleApi { apiServices.addHistory(workoutId, exercises) }
}
