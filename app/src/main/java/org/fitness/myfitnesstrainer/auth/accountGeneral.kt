package org.fitness.myfitnesstrainer.auth

import kotlinx.coroutines.runBlocking
import org.fitness.myfitnesstrainer.api.MyFitnessClient
import org.fitness.myfitnesstrainer.data.remote.models.AuthRequest
import org.fitness.myfitnesstrainer.data.remote.models.NetworkResult
import timber.log.Timber

object AccountGeneral {
    const val ACCOUNT_TYPE = "org.fitness.myfitnesstrainer.auth"
    const val ACCOUNT_NAME = "myfitnesstrainer"
    const val AUTHTOKEN_TYPE_FULL_ACCESS = "Full access"
    const val AUTHTOKEN_TYPE_FULL_ACCESS_LABEL = "Full access to an account"

    //    val sServerAuthenticate: ServerAuthenticate = ParseComServerAuthenticate()
    fun login(email: String, password: String): String {
        Timber.i("Logging In:  Email: $email  Password: $password")
        val authRequest = AuthRequest(email, password)

        var token: String = runBlocking {
            when (val response = MyFitnessClient.service.authenticate(authRequest)) {
                is NetworkResult.Success -> {
                    return@runBlocking response.data.token
                }

                is NetworkResult.Error -> {
                    return@runBlocking ""
                }

                is NetworkResult.Exception -> {
                    throw Exception("Ya done son")
                }
            }
        }
        return token
    }

    suspend fun loginNon(email: String, password: String): String {
        Timber.i("Logging In:  Email: $email  Password: $password")
        val authRequest = AuthRequest(email, password)

        var token: String = when (val response = MyFitnessClient.service.authenticate(authRequest)) {
                is NetworkResult.Success -> {
                    response.data.token
                }

                is NetworkResult.Error -> {
                    return ""
                }

                is NetworkResult.Exception -> {
                    throw Exception("Ya done son")
                }
            }
        return token
    }
}

