package org.fitness.myfitnesstrainer.ui.activities.myfitnessactivity.screens.Layout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.runBlocking
import org.fitness.myfitnesstrainer.api.MyFitnessClient
import org.fitness.myfitnesstrainer.auth.AuthManager
import org.fitness.myfitnesstrainer.data.local.models.Profile
import org.fitness.myfitnesstrainer.data.remote.models.NetworkResult
import timber.log.Timber

class LayoutViewModel(authManager: AuthManager) : ViewModel() {
    private val authManager = authManager
    val profile: MutableLiveData<Profile?> = MutableLiveData()

    init {
        authManager.loggedIn.observeForever() { loggedIn ->
            if (loggedIn) {
                getProfile()
            }
        }
    }

    private fun getProfile() {
        Timber.i("Getting Profile, Auth: ${MyFitnessClient.TOKEN}")
        runBlocking {
            when (val response = MyFitnessClient.service.getProfile()) {
                is NetworkResult.Success -> {
                    Timber.i("Success: ${profile.toString()}")
                    profile.postValue(Profile(response.data.profile))
                }

                is NetworkResult.Error -> {
                    Timber.i("Fail: ${response.code}")
                    profile.postValue(null)
                }

                is NetworkResult.Exception -> {
                    throw Exception("Ya done son")
                }
            }
        }
    }
}