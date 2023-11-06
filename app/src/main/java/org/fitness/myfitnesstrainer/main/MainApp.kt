package org.fitness.myfitnesstrainer.main

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.fitness.myfitnesstrainer.activities.AddWorkoutActivity
import org.fitness.myfitnesstrainer.activities.MainActivity
import org.fitness.myfitnesstrainer.api.RetrofitInstance
import org.fitness.myfitnesstrainer.models.Profile
import org.fitness.myfitnesstrainer.service.NetworkResult
import timber.log.Timber


class MainApp : Application() {
    private var instance: MainApp? = null
    lateinit var profile: Profile


    suspend fun refreshProfile() {
        profile = GlobalScope.async {
            profile = getProfile()
            Timber.i("profile %s", profile)
            return@async profile
        }.await()
    }

    fun login() {
        val intent = Intent(this@MainApp, MainActivity::class.java)
        startActivity(intent)
    }

    fun getInstance(): MainApp? {
        return instance
    }

    fun getContext(): Context? {
        return instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this;

        Timber.plant(Timber.DebugTree())
        Timber.i("My Fitness Trainer started")
    }

    suspend fun getProfile(): Profile {
        val profileDeferred = GlobalScope.async {
            when (val response = RetrofitInstance.service.getProfile()) {
                is NetworkResult.Success -> {
                    Timber.i("Profile Success")
                    return@async Profile(response.data.profile)
                }

                is NetworkResult.Error -> {
                    Timber.i("Http Err", response.errorMsg)
                    throw Exception("Bad Request")
                }

                is NetworkResult.Exception -> {
                    Timber.i("Not Connected to Internet")
                    throw Exception("Unable to connect to server")
                }
            }
        }
        return profileDeferred.await()
    }
}