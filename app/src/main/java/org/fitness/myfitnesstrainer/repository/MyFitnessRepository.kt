package org.fitness.myfitnesstrainer.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.fitness.myfitnesstrainer.api.MyFitnessClient
import org.fitness.myfitnesstrainer.auth.AuthManager
import org.fitness.myfitnesstrainer.data.local.models.Profile
import org.fitness.myfitnesstrainer.data.remote.models.NetworkResult
import timber.log.Timber

class MyFitnessRepository(authManager: AuthManager) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val authManager = authManager

    val profile: Resource<Profile> by lazy {
        Resource(null)
    }

    init {
        authManager.loggedIn.observeForever { loggedIn ->
            when (loggedIn) {
                true -> getProfile()
                false -> profile.clear()
            }
        }
    }

    fun login() {
        authManager.invalidateAuthToken()
        authManager.getTokenForAccountCreateIfNeeded()
    }

    fun logout() {
        authManager.logout()
    }

    private fun getProfile() {
        Timber.i("Request Auth: ${MyFitnessClient.TOKEN}")
        profile.isLoading()

        scope.launch {
            when (val response = MyFitnessClient.service.getProfile()) {
                is NetworkResult.Success -> profile.isSuccess(response.data.profile)
                is NetworkResult.Error -> when(response.code) {
                    401 -> login()
                    else -> profile.isError(response.code.toString())
                }
                is NetworkResult.Exception -> profile.isException(response.e)
            }
        }

    }
}