package org.fitness.myfitnesstrainer.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.fitness.myfitnesstrainer.service.NetworkResult
import org.fitness.myfitnesstrainer.api.RetrofitInstance
import org.fitness.myfitnesstrainer.api.models.AuthRequest
import org.fitness.myfitnesstrainer.databinding.ActivityLoginBinding
import org.fitness.myfitnesstrainer.main.MainApp
import org.fitness.myfitnesstrainer.models.Profile
import timber.log.Timber

class LoginActivity : AppCompatActivity() {
    lateinit var app : MainApp
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MainApp

        binding = ActivityLoginBinding.inflate(layoutInflater)

        binding.btnLogin.setOnClickListener {
            lifecycleScope.async {
                val success = login(binding.inptEmail.text.toString(), binding.inptPassword.text.toString()).await()
                if(success) {
                    app.refreshProfile().await()
                    Timber.i("Finished calling refresh profile")
                    finish()
                    return@async startMainActivity()
                }
//                else {
//                    return@async loginFailed()
//                }
            }
        }

        setContentView(binding.root)
    }

    private fun loginFailed() {
        Timber.i("Something went wrong")
    }


    private suspend fun login(email: String, password: String): Deferred<Boolean> {
        val authRequest = AuthRequest(email, password)
        Timber.i("Logging In")

        val loginDeferred = lifecycleScope.async {
            when (val response = RetrofitInstance.service.authenticate(authRequest)) {
                is NetworkResult.Success -> {
                    Timber.i("Login Success")
                    RetrofitInstance.TOKEN = response.data.token
                    return@async true
                }

                is NetworkResult.Error -> {
                    Timber.i("Login Failure")
                    return@async false
                }

                is NetworkResult.Exception -> {
                    Timber.i("%s", response.e)
                    throw Exception("Ya done son")
                }
            }
        }
        return loginDeferred
    }

    private fun startMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }
}



