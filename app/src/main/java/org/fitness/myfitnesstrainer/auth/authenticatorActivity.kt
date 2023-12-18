package org.fitness.myfitnesstrainer.auth

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Surface
import org.fitness.myfitnesstrainer.ui.screens.Login.LoginScreen
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme
import timber.log.Timber


class AuthenticatorActivity : AppCompatActivity() {
    val ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE"
    val ARG_AUTH_TYPE = "AUTH_TYPE"
    val ARG_ACCOUNT_NAME = "ACCOUNT_NAME"
    val ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT"
    val KEY_ERROR_MESSAGE = "ERR_MSG"
    val PARAM_USER_PASS = "USER_PASS"

    private var mAccountManager: AccountManager? = null
    private var mAuthTokenType: String? = null
    private var mResultBundle: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAccountManager = AccountManager.get(baseContext)
        val accountName = intent.getStringExtra(ARG_ACCOUNT_NAME)
        mAuthTokenType = intent.getStringExtra(ARG_AUTH_TYPE)
        if (mAuthTokenType == null) mAuthTokenType = AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS

        setContent {
            MyFitnessTrainerTheme {
                Surface {
                    LoginScreen(::finishLogin)
                }
            }
        }
    }

//    protected fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
//        // The sign up activity returned that the user has successfully created an account
//        if (requestCode == REQ_SIGNUP && resultCode == RESULT_OK) {
//            finishLogin(data)
//        } else super.onActivityResult(requestCode, resultCode, data)
//    }

        private fun setAccountAuthenticatorResult(result: Bundle) {
            mResultBundle = result
        }

        private fun finishLogin(intent: Intent) {
            val accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
            val accountPassword = intent.getStringExtra(PARAM_USER_PASS)
            val account = Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE))

            if (getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {

                val authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN)
                val authtokenType = mAuthTokenType

                // Creating the account on the device and setting the auth token we got
                // (Not setting the auth token will cause another call to the server to authenticate the user)
                mAccountManager!!.addAccountExplicitly(account, accountPassword, null)
                mAccountManager!!.setAuthToken(account, authtokenType, authtoken)
            } else {
                Timber.i(mAccountManager?.accounts.toString())
                mAccountManager!!.setPassword(account, accountPassword)
            }


            setAccountAuthenticatorResult(intent.extras!!)
            setResult(RESULT_OK, intent)
            finish()
        }
    }