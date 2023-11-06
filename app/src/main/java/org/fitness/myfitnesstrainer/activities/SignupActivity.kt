package org.fitness.myfitnesstrainer.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.fitness.myfitnesstrainer.api.NetworkResult
import org.fitness.myfitnesstrainer.api.RetrofitInstance
import org.fitness.myfitnesstrainer.api.models.AuthRequest
import org.fitness.myfitnesstrainer.api.models.SignupRequest
import org.fitness.myfitnesstrainer.databinding.ActivityLoginBinding
import org.fitness.myfitnesstrainer.databinding.ActivitySignUpBinding
import org.fitness.myfitnesstrainer.main.MainApp
import org.fitness.myfitnesstrainer.models.Profile
import timber.log.Timber

class SignupActivity : AppCompatActivity() {
    lateinit var app : MainApp
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MainApp

        binding = ActivitySignUpBinding.inflate(layoutInflater)

        binding.btnFinishSignup.setOnClickListener {
            lifecycleScope.async {
                val success = signup(binding.txtUnputSignupEmail.text.toString(), binding.txtUnputSignupPassword.text.toString(), binding.txtUnputSignupFname.text.toString(), binding.txtUnputSignupLname.text.toString()).await()
                if(success) {
                    return@async startLoginActivity()
                }
                else {
                    binding.btnFinishSignup.text= "Something Went Wrong"
                    binding.btnFinishSignup.setOnClickListener {
                        startLoginActivity()
                    }
                }
            }
        }
        setContentView(binding.root)
    }

    private suspend fun signup(email: String, password: String, fname: String, lname: String): Deferred<Boolean> {
        val signupRequest = SignupRequest(email, password, fname, lname)
        Timber.i("Signup")

        val loginDeferred = lifecycleScope.async {
            when (val response = RetrofitInstance.service.signup(signupRequest)) {
                is NetworkResult.Success -> {
                    Timber.i("Signup Success")
                    return@async true
                }

                is NetworkResult.Error -> {
                    Timber.i("Signup Failure")
                    Timber.i("response %s", response.code)
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

    private fun startLoginActivity() {
        finish()
        val intent = Intent(this@SignupActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}



