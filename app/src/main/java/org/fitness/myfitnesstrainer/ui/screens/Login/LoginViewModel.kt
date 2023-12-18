package org.fitness.myfitnesstrainer.ui.screens.Login

import android.R
import android.accounts.Account
import android.accounts.AccountManager
import android.accounts.AccountManager.KEY_ERROR_MESSAGE
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.content.Intent.getIntent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import org.fitness.myfitnesstrainer.data.remote.models.AuthRequest
import org.fitness.myfitnesstrainer.data.repository.api.NetworkResult
import org.fitness.myfitnesstrainer.data.repository.api.RetrofitInstance
import timber.log.Timber

class LoginViewModel (app: Application = Application()) : AndroidViewModel(app) {
    suspend fun submit(email: String, password: String): Intent {
        var authtoken: String? = null
        val data = Bundle()
        try {
            authtoken = login(email, password).await()
            data.putString(AccountManager.KEY_ACCOUNT_NAME, email)
            data.putString(AccountManager.KEY_ACCOUNT_TYPE, "com.myFitnessTrainer.auth_example")
            data.putString(AccountManager.KEY_AUTHTOKEN, authtoken)
            data.putString("PARAM_USER_PASS", password)
        } catch (e: Exception) {
            data.putString("KEY_ERROR_MESSAGE", e.message)
        }
        val res = Intent()
        res.putExtras(data)
        return res
    }

    fun login(email: String, password: String): Deferred<String> {
        Timber.i("Logging In:  Email: $email  Password: $password")
        val authRequest = AuthRequest(email, password)

        val deferredToken = this.viewModelScope.async {
            when (val response = RetrofitInstance.service.authenticate(authRequest)) {
                is NetworkResult.Success -> {
                    Timber.i("Login Success")
                    RetrofitInstance.TOKEN = response.data.token
                    Timber.i(response.data.token)
                    return@async response.data.token
                }

                is NetworkResult.Error -> {
                    Timber.i("Login Failure")
                    Timber.i("response %s", response.code)
                    return@async ""
                }

                is NetworkResult.Exception -> {
                    Timber.i("%s", response.e)
                    throw Exception("Ya done son")
                }
            }
        }
        return deferredToken
    }
}