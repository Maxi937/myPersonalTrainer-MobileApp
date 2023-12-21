package org.fitness.myfitnesstrainer.auth

import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Surface
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.async
import org.fitness.myfitnesstrainer.ui.theme.MyFitnessTrainerTheme
import timber.log.Timber

// AuthenticatorActivity depreciated - had to add the code from it to this app compat to make sure the response was being received from the caller
class AuthenticatorActivity : AppCompatActivity () {
    private var mAccountManager: AccountManager? = null
    private var mAuthTokenType: String? = null
    private var mResultBundle: Bundle? = null
    private var mAccountAuthenticatorResponse: AccountAuthenticatorResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("Auth Activity Started")
        super.onCreate(savedInstanceState)

        mAccountAuthenticatorResponse = intent.getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, AccountAuthenticatorResponse::class.java);
        mAccountAuthenticatorResponse?.onRequestContinued()

        mAccountManager = AccountManager.get(baseContext)
        mAuthTokenType = intent.getStringExtra("ARG_AUTH_TYPE")
        if (mAuthTokenType == null) mAuthTokenType = AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS

        setContent {
            MyFitnessTrainerTheme {
                Surface {
                    LoginScreen({ email, password ->
                        lifecycleScope.async {
                            val intent = submit(email, password)
                            if (intent.hasExtra("KEY_ERROR_MESSAGE")) {
                                Toast.makeText(
                                    baseContext,
                                    intent.getStringExtra("KEY_ERROR_MESSAGE"),
                                    Toast.LENGTH_SHORT
                                ).show();
                            } else {
                                finishLogin(intent);
                            }

                        }

                    })
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

    private suspend fun submit(email: String, password: String): Intent {
        var authtoken: String? = null
        val data = Bundle()
        try {
            authtoken = AccountGeneral.loginNon(email, password)
            data.putString(AccountManager.KEY_ACCOUNT_NAME, email)
            data.putString(
                AccountManager.KEY_ACCOUNT_TYPE,
                intent.getStringExtra("ARG_ACCOUNT_TYPE")
            )
            data.putString(AccountManager.KEY_AUTHTOKEN, authtoken)
            data.putString("PARAM_USER_PASS", password)
        } catch (e: Exception) {
            data.putString("KEY_ERROR_MESSAGE", e.message)
        }
        val res = Intent()
        res.putExtras(data)
        return res
    }

    private fun finishLogin(intent: Intent) {
        val accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
        val accountPassword = intent.getStringExtra("PARAM_USER_PASS")
        val account = Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE))

        if (getIntent().getBooleanExtra("ARG_IS_ADDING_NEW_ACCOUNT", false)) {
            Timber.i("> finishLogin > addAccountExplicitly")
            val authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN)
            val authtokenType = mAuthTokenType

            mAccountManager!!.addAccountExplicitly(account, accountPassword, null)
            mAccountManager!!.setAuthToken(account, authtokenType, authtoken)
        } else {
            Timber.i("> finishLogin > setPassword")
            mAccountManager!!.setPassword(account, accountPassword)
        }

        setAccountAuthenticatorResult(intent.extras!!)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun finish() {
        if (mAccountAuthenticatorResponse != null) {
            // send the result bundle back if set, otherwise send an error.
            if (mResultBundle != null) {
                mAccountAuthenticatorResponse!!.onResult(mResultBundle)
            } else {
                mAccountAuthenticatorResponse!!.onError(
                    AccountManager.ERROR_CODE_CANCELED,
                    "canceled"
                )
            }
            mAccountAuthenticatorResponse = null
        }
        super.finish()
    }
}