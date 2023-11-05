package org.fitness.myfitnesstrainer.api

import org.fitness.myfitnesstrainer.api.models.AuthRequest
import org.fitness.myfitnesstrainer.api.models.Token
import org.fitness.myfitnesstrainer.api.models.apiProfile
import org.fitness.myfitnesstrainer.api.models.apiWorkout
import org.fitness.myfitnesstrainer.models.WorkoutModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServices {
//    @GET("/api/workouts")
//    suspend fun getWorkouts(): Response<apiWorkouts>
//
//    @GET("/api/exercises")
//    suspend fun getExercises(): Response<apiExercises>

    @POST("/api/users/authenticate")
    suspend fun authenticate(@Body authRequest: AuthRequest): Response<Token>

    @GET("/api/profile")
    suspend fun getProfile(): Response<apiProfile>

    @POST("/api/users")
    suspend fun signup(@Body authRequest: AuthRequest): Response<Token>

    @POST("/api/workouts")
    suspend fun addWorkout(@Body workout: WorkoutModel): Response<apiWorkout>
}