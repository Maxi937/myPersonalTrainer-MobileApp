package org.fitness.myfitnesstrainer.ui.screens.App

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import org.fitness.myfitnesstrainer.auth.AuthManager
import timber.log.Timber

class AppViewModel(activity: Activity) : ViewModel() {
    var authManager : AuthManager = AuthManager(activity = activity)

    fun logOut() {
//        AuthManager
    }

}