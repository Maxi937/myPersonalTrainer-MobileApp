package org.fitness.myfitnesstrainer.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
            val profile = GlobalScope.async {
                val success = login(binding.inptEmail.text.toString(), binding.inptPassword.text.toString())
                if(success) {
                    return@async startMainActivity()

                }
                else {
                    return@async loginFailed()
                }
            }
        }

//        binding.btnSignup.setOnClickListener {
//            GlobalScope.async {
//                signUp(binding.inptEmail.text.toString(), binding.inptPassword.text.toString())
//            }
//        }

        setContentView(binding.root)
    }

    private fun loginFailed() {
        Timber.i("Something went wrong")
    }


    private suspend fun login(email: String, password: String): Boolean {
        val authRequest = AuthRequest(email, password)
        Timber.i("Logging In")

        val loginDeferred = GlobalScope.async {
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
        return loginDeferred.await()
    }

    private fun startMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }
}



