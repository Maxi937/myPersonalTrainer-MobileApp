package org.fitness.myfitnesstrainer.data.local.models

data class AuthRequest(
    val email: String,
    val password: String,
)

data class SignupRequest(
    val email: String,
    val password: String,
    val fname: String,
    val lname: String,
)