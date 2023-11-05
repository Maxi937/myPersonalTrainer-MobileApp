package org.fitness.myfitnesstrainer.main

import android.app.Application
import android.content.Context
import org.fitness.myfitnesstrainer.models.Profile
import timber.log.Timber


class MainApp : Application() {
    private var instance: MainApp? = null
    var profile: Profile? = null

    fun setMProfile(profile: Profile) {
        this.profile = profile
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
}