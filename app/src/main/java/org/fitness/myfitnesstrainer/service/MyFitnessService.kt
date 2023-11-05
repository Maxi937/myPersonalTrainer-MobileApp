package org.fitness.myfitnesstrainer.service

import org.fitness.myfitnesstrainer.api.ApiServices
import org.fitness.myfitnesstrainer.api.models.AuthRequest
import org.fitness.myfitnesstrainer.api.models.Token
import org.fitness.myfitnesstrainer.api.models.apiProfile
import org.fitness.myfitnesstrainer.api.models.apiWorkout
import org.fitness.myfitnesstrainer.models.WorkoutModel
import retrofit2.Response

interface MyFitnessService {
    suspend fun getProfile(): NetworkResult<apiProfile>

    suspend fun addWorkout(workoutModel: WorkoutModel): NetworkResult<apiWorkout>

    suspend fun authenticate(authRequest: AuthRequest): NetworkResult<Token>

    suspend fun signup(authRequest: AuthRequest): NetworkResult<Token>

}

class MyFitnessServiceImp(private val apiServices: ApiServices) : MyFitnessService, ApiHandler {
    override suspend fun getProfile(): NetworkResult<apiProfile> = handleApi { apiServices.getProfile() }

    override suspend fun addWorkout(workoutModel: WorkoutModel): NetworkResult<apiWorkout> = handleApi { apiServices.addWorkout(workoutModel) }

    override suspend fun authenticate(authRequest: AuthRequest): NetworkResult<Token> = handleApi { apiServices.authenticate(authRequest) }

    override suspend fun signup(authRequest: AuthRequest): NetworkResult<Token> = handleApi { apiServices.signup(authRequest) }
}
