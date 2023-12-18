package org.fitness.myfitnesstrainer.auth

import android.app.Service
import android.content.Intent
import android.os.IBinder
import timber.log.Timber


class MyFitnessAuthenticatorService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        Timber.i("I got her")
        val authenticator = MyFitnessAuthenticator(this)
        return authenticator.iBinder
    }
}