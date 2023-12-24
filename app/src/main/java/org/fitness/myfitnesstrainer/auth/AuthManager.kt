package org.fitness.myfitnesstrainer.auth

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import org.fitness.myfitnesstrainer.api.MyFitnessClient
import timber.log.Timber


class AuthManager(activity: Activity) {
    private var activity = activity
    private var mAccountManager: AccountManager = AccountManager.get(activity)
    private var loggedInAccountName: String? = null

    val loggedIn: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    private fun addTokenToClient(token: String) {
        showMessage("Token Added to Client: $token")
        MyFitnessClient.TOKEN = token
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
                    val accountName = bnd.getString(AccountManager.KEY_ACCOUNT_NAME)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                    showMessage(e.message)
                }
            },
            null
        )
    }

    private fun getAccount(): Account? {
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
                        addTokenToClient(authtoken)
                        loggedIn.postValue(true)
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
            AccountGeneral.ACCOUNT_TYPE,
            AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS,
            null,
            activity,
            null,
            null,
            { future ->
                var bnd: Bundle? = null

                try {
                    bnd = future.result
                    val authtoken = bnd!!.getString(AccountManager.KEY_AUTHTOKEN)

                    if (authtoken != null) {
                        addTokenToClient(authtoken)
                        loggedInAccountName = bnd!!.getString(AccountManager.KEY_ACCOUNT_NAME)
                        loggedIn.postValue(true)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            null
        )
    }

    fun logout() {
        val account = getAccount()

        if (account != null) {
            removeAccount(account)
            loggedIn.postValue(false)
        }
    }

    private fun removeAccount(account: Account) {
        mAccountManager.removeAccount(
            account, activity, { future ->
                val bnd = future.result
                val result = bnd.getBoolean(AccountManager.KEY_BOOLEAN_RESULT)
            }, null
        )
    }

    private fun showMessage(msg: String?) {
        if (TextUtils.isEmpty(msg)) return
        activity.runOnUiThread {
            Toast.makeText(activity.baseContext, msg, Toast.LENGTH_SHORT).show()
        }
    }

    fun invalidateAuthToken() {
        val account: Account? = getAccount()

        if (account != null) {
            mAccountManager.getAuthToken(
                account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, null, activity, { future ->
                    val bnd = future.result
                    val authtoken = bnd.getString(AccountManager.KEY_AUTHTOKEN)
                    if (authtoken != null) {
                        mAccountManager.invalidateAuthToken(account.type, authtoken)
                    }
                }, null
            )

        }

    }
}