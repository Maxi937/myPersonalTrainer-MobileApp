package org.fitness.myfitnesstrainer.api

import org.fitness.myfitnesstrainer.data.local.models.AuthRequest
import org.fitness.myfitnesstrainer.data.remote.models.Token
import org.fitness.myfitnesstrainer.data.remote.models.apiProfile
import org.fitness.myfitnesstrainer.data.remote.models.apiStatus
import org.fitness.myfitnesstrainer.data.local.models.ExerciseModel
import org.fitness.myfitnesstrainer.data.local.models.WorkoutModel
import org.fitness.myfitnesstrainer.data.local.models.SignupRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServices {
    @POST("/api/users/authenticate")
    suspend fun authenticate(@Body authRequest: AuthRequest): Response<Token>

    @GET("/api/profile")
    suspend fun getProfile(): Response<apiProfile>

    @POST("/api/users")
    suspend fun signup(@Body signupRequest: SignupRequest): Response<apiStatus>

    @POST("/api/workouts")
    suspend fun addWorkout(@Body workout: WorkoutModel): Response<WorkoutModel>

    @POST("/api/exercises")
    suspend fun addExercise(@Body exercise: ExerciseModel): Response<ExerciseModel>

    @POST("/api/workouts/delete")
    suspend fun deleteWorkout(@Body workout: WorkoutModel): Response<apiStatus>

    @POST("/api/exercises/delete")
    suspend fun deleteExercise(@Body exercise: ExerciseModel): Response<apiStatus>

    @POST("/api/workouts/history")
    suspend fun addHistory(@Body workout: WorkoutModel): Response<WorkoutModel>
}