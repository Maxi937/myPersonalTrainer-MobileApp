package org.fitness.myfitnesstrainer.api

import org.fitness.myfitnesstrainer.api.models.AuthRequest
import org.fitness.myfitnesstrainer.api.models.Token
import org.fitness.myfitnesstrainer.api.models.apiExercise
import org.fitness.myfitnesstrainer.api.models.apiProfile
import org.fitness.myfitnesstrainer.api.models.apiStatus
import org.fitness.myfitnesstrainer.api.models.apiWorkout
import org.fitness.myfitnesstrainer.models.ExerciseModel
import org.fitness.myfitnesstrainer.models.WorkoutModel
import org.fitness.myfitnesstrainer.api.NetworkResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiServices {
    @POST("/api/users/authenticate")
    suspend fun authenticate(@Body authRequest: AuthRequest): Response<Token>

    @GET("/api/profile")
    suspend fun getProfile(): Response<apiProfile>

    @POST("/api/users")
    suspend fun signup(@Body authRequest: AuthRequest): Response<Token>

    @POST("/api/workouts")
    suspend fun addWorkout(@Body workout: WorkoutModel): Response<apiWorkout>

    @POST("/api/exercises")
    suspend fun addExercise(@Body exercise: ExerciseModel): Response<apiExercise>

    @POST("/api/workouts/delete")
    suspend fun deleteWorkout(@Body workout: WorkoutModel): Response<apiStatus>
    @POST("/api/exercises/delete")
    suspend fun deleteExercise(@Body exercise: ExerciseModel): Response<apiStatus>

    @POST("/api/workouts/history")
    suspend fun addHistory(@Body workout: WorkoutModel): Response<apiWorkout>
}