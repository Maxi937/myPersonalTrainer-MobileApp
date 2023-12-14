package org.fitness.myfitnesstrainer.ui.screens.Login

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.currentCoroutineContext
import org.fitness.myfitnesstrainer.data.remote.models.AuthRequest
import org.fitness.myfitnesstrainer.data.repository.api.NetworkResult
import org.fitness.myfitnesstrainer.data.repository.api.RetrofitInstance
import timber.log.Timber

class LoginViewModel (app: Application = Application()) : AndroidViewModel(app) {

//    var firebaseAuthManager : FirebaseAuthManager = FirebaseAuthManager(app)
//    var liveFirebaseUser : MutableLiveData<FirebaseUser> = firebaseAuthManager.liveFirebaseUser

    suspend fun login(email: String, password: String): Deferred<Boolean> {
        Timber.i("Logging In:  Email: $email  Password: $password")
        val authRequest = AuthRequest(email, password)

        val defferedLogin = this.viewModelScope.async {
            when (val response = RetrofitInstance.service.authenticate(authRequest)) {
                is NetworkResult.Success -> {
                    Timber.i("Login Success")
                    RetrofitInstance.TOKEN = response.data.token
                    Timber.i(response.data.token)
                    return@async true
                }

                is NetworkResult.Error -> {
                    Timber.i("Login Failure")
                    Timber.i("response %s", response.code)
                    return@async false
                }

                is NetworkResult.Exception -> {
                    Timber.i("%s", response.e)
                    throw Exception("Ya done son")
                }
            }
        }
        return defferedLogin
    }

}