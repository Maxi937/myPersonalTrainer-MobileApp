package org.fitness.myfitnesstrainer.auth

import android.R
import android.accounts.AccountManager
import android.accounts.AccountManager.KEY_ERROR_MESSAGE
import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme
import timber.log.Timber


class SignUpActivity : ComponentActivity() {
    private var mAccountType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("Starting SignUp")
        super.onCreate(savedInstanceState)
        mAccountType = intent.getStringExtra("ARG_ACCOUNT_TYPE")
        Timber.i("Account Type: $mAccountType")

        setContent {
            SignupScreen(onCancel = { cancel() }, onSubmit = { email, password, fname, lname ->
                intent = createAccount(email, password, fname, lname)
                finishSignUp(intent)
            })
        }
    }

    private fun cancel() {
        setResult(RESULT_CANCELED)
        finish()
    }

    private fun createAccount(
        email: String,
        password: String,
        fname: String,
        lname: String
    ): Intent {
        var email = email.trim { it <= ' ' }
        var fname = fname.trim { it <= ' ' }
        var password = password.trim { it <= ' ' }
        var lname = lname.trim { it <= ' ' }

        var authtoken: String? = null
        val data = Bundle()

        try {
            authtoken = AccountGeneral.signUp(email, password, fname, lname)

            data.putString(AccountManager.KEY_ACCOUNT_NAME, email)
            data.putString(AccountManager.KEY_ACCOUNT_TYPE, mAccountType)
            data.putString(AccountManager.KEY_AUTHTOKEN, authtoken)
            data.putString("PARAM_USER_PASS", password)
        } catch (e: Exception) {
            data.putString(KEY_ERROR_MESSAGE, e.message)
        }
        val res = Intent()
        res.putExtras(data)
        return res
    }

    private fun finishSignUp(intent: Intent) {
        if (intent.hasExtra(KEY_ERROR_MESSAGE)) {
            Toast.makeText(
                baseContext,
                intent.getStringExtra(KEY_ERROR_MESSAGE),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun finish() {
        Timber.i("Deadass Im done")
        super.finish()
    }
}