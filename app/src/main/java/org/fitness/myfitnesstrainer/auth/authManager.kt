package org.fitness.myfitnesstrainer.auth

import android.accounts.Account
import android.accounts.AccountManager
import android.accounts.AccountManagerCallback
import android.accounts.AccountManagerFuture
import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.runBlocking
import org.fitness.myfitnesstrainer.api.MyFitnessClient
import org.fitness.myfitnesstrainer.data.remote.models.AuthRequest
import org.fitness.myfitnesstrainer.data.remote.models.NetworkResult
import timber.log.Timber


class AuthManager(activity: Activity) {
    private var activity = activity
    private var mAccountManager: AccountManager = AccountManager.get(activity)
    private var loggedInAccountName: String? = null
    val loggedIn: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val loggedInUser: MutableLiveData<Account> by lazy {
        MutableLiveData<Account>(null)
    }

    fun checkLogin() {
        val accounts = mAccountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE)

        if (accounts.isNotEmpty()) {
            val account = accounts[0]
            var token: String? = null

            token = mAccountManager.peekAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS)
            if (token != null) {
                showMessage("Auth: True")
            }
        } else {
            showMessage("Auth: False")
        }
    }

    private fun addAccount() {
        mAccountManager.addAccount(
            AccountGeneral.ACCOUNT_TYPE,
            AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS,
            null,
            null,
            activity,
            { future ->
                try {
                    val bnd = future.result
                    showMessage("Account was created")
                    Log.d("My Fitness", "AddNewAccount Bundle is $bnd")
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                    showMessage(e.message)
                }
            },
            null
        )
    }

    fun getAccount(): Account? {
        val accounts = mAccountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE)

        if (accounts.isNotEmpty()) {
            return accounts[0]
        }
        return null
    }

    fun getAuthToken() {
        val account: Account? = getAccount()

        if (account != null) {
            mAccountManager.getAuthToken(
                account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, null, activity,
                { future ->
                    var bnd: Bundle? = null
                    bnd = future.result

                    val authtoken = bnd!!.getString(AccountManager.KEY_AUTHTOKEN)

                    if (authtoken != null) {
                        loggedInUser.postValue(getAccount())
                        loggedIn.postValue(true)
                        showMessage("logged In: $loggedIn")
                    }
                },
                null
            )
        } else {
            addAccount()
        }
    }

    fun getTokenForAccountCreateIfNeeded() {
        Timber.i("GetTokenForAccountCreateIfNeeded Started")
        val future = mAccountManager.getAuthTokenByFeatures(
            AccountGeneral.ACCOUNT_TYPE, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, null, activity, null, null, { future ->
                var bnd: Bundle? = null

                try {
                    bnd = future.result
                    val authtoken = bnd!!.getString(AccountManager.KEY_AUTHTOKEN)

                    if (authtoken != null) {
                        loggedInAccountName = bnd!!.getString(AccountManager.KEY_ACCOUNT_NAME)
                        loggedIn.postValue(true)
                        showMessage("Posting True to logged in")
                    }
                } catch (e: Exception) {
                    showMessage("error")
                    e.printStackTrace()
                }
            }, null
        )
    }

    fun login(email: String, password: String): String {
        Timber.i("Logging In:  Email: $email  Password: $password")
        val authRequest = AuthRequest(email, password)

        var token: String = runBlocking {
            when (val response = MyFitnessClient.service.authenticate(authRequest)) {
                is NetworkResult.Success -> {
                    return@runBlocking response.data.token
                }

                is NetworkResult.Error -> {
                    showMessage("Failed to login: $response.code")
                    return@runBlocking ""
                }

                is NetworkResult.Exception -> {
                    showMessage("Network Error: $response.e")
                    throw Exception("Ya done son")
                }
            }
        }
        return token
    }

    fun logout() {
        val account = getAccount()

        if (account != null) {
//            invalidateAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS)
            loggedIn.postValue(false)
            removeAccount(account)
        }
    }

    private fun removeAccount(account: Account) {
        mAccountManager.removeAccount(
            account, activity, { future ->
                val bnd = future.result
                val result = bnd.getBoolean(AccountManager.KEY_BOOLEAN_RESULT)
                showMessage("account removed: ${account.name}")
            }, null
        )

    }

    private fun showMessage(msg: String?) {
        if (TextUtils.isEmpty(msg)) return
        activity.runOnUiThread {
            Toast.makeText(activity.baseContext, msg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun invalidateAuthToken(account: Account, authTokenType: String) {
        mAccountManager.getAuthToken(
            account, authTokenType, null, activity, { future ->
                val bnd = future.result
                val authtoken = bnd.getString(AccountManager.KEY_AUTHTOKEN)
                mAccountManager.invalidateAuthToken(account.type, authtoken)
            }, null
        )
    }
}