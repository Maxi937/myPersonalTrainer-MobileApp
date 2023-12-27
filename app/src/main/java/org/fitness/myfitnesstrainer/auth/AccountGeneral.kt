package org.fitness.myfitnesstrainer.auth

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.fitness.myfitnesstrainer.api.MyFitnessClient
import org.fitness.myfitnesstrainer.data.local.models.AuthRequest
import org.fitness.myfitnesstrainer.data.local.models.SignupRequest
import org.fitness.myfitnesstrainer.data.remote.models.NetworkResult
import timber.log.Timber

object AccountGeneral {
    private val scope = CoroutineScope(Dispatchers.IO)
    const val ACCOUNT_TYPE = "org.fitness.myfitnesstrainer.auth"
    const val ACCOUNT_NAME = "myfitnesstrainer"
    const val AUTHTOKEN_TYPE_FULL_ACCESS = "Full access"
    const val AUTHTOKEN_TYPE_FULL_ACCESS_LABEL = "Full access to an account"

    fun login(email: String, password: String): String {
        Timber.i("Logging In:  Email: $email  Password: $password")
        val authRequest = AuthRequest(email, password)

        var token: String = runBlocking {
            when (val response = MyFitnessClient.service.authenticate(authRequest)) {
                is NetworkResult.Success -> return@runBlocking response.data.token
                is NetworkResult.Error -> return@runBlocking ""
                is NetworkResult.Exception -> return@runBlocking throw Exception("Something went wrong")
            }
        }
        return token
    }

    fun signUp(email: String, password: String, fname: String, lname: String): String {
        Timber.i("Signing Up:  Email: $email  Password: $password")
        val authRequest = SignupRequest(email, password, fname, lname)

        var token: String = runBlocking {
            when (val response = MyFitnessClient.service.signup(authRequest)) {
                is NetworkResult.Success -> return@runBlocking response.data.token
                is NetworkResult.Error -> return@runBlocking ""
                is NetworkResult.Exception -> return@runBlocking throw Exception("Something went wrong")
            }
        }
        return token
    }
}

