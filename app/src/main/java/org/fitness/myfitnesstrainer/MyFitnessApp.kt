package org.fitness.myfitnesstrainer

import android.accounts.AccountManager
import android.app.Application
import android.content.Context
import timber.log.Timber


class MyFitnessApp : Application() {
    private var instance: MyFitnessApp? = null

    fun getInstance(): MyFitnessApp? {
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