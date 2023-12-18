package org.fitness.myfitnesstrainer

import android.accounts.AccountManager
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import org.fitness.myfitnesstrainer.auth.AccountGeneral
import org.fitness.myfitnesstrainer.auth.AuthenticatorActivity
import timber.log.Timber


class MyFitnessActivity : ComponentActivity() {
    private lateinit var startForResult : ActivityResultLauncher<Intent>
    private lateinit var mAccountManager: AccountManager
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAccountManager = AccountManager.get(this);


        getTokenForAccountCreateIfNeeded(AccountGeneral.ACCOUNT_TYPE, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS)

//        setContent {
//            MyFitnessTrainerTheme {
//                Surface {
//                }
//
////                val navController = rememberNavController()
////                Scaffold(
////                    bottomBar = { BottomNavBar(navController) }
////                ) {
////                    BottomNavRoutes(navController = navController) }
//            }
//        }
    }

    private fun getTokenForAccountCreateIfNeeded(accountType: String, authTokenType: String) {
        val future = mAccountManager.getAuthTokenByFeatures(accountType,
            authTokenType,
            null,
            this,
            null,
            null,
            { future ->
                var bnd: Bundle? = null
                try {
                    bnd = future.result
                    val authtoken = bnd!!.getString(AccountManager.KEY_AUTHTOKEN)
                    showMessage(if (authtoken != null) "SUCCESS!\ntoken: $authtoken" else "FAIL")
                } catch (e: Exception) {
                    e.printStackTrace()
                    showMessage(e.message)
                }
            },
            null)
    }

    private fun showMessage(msg: String?) {
        if (TextUtils.isEmpty(msg)) return
        runOnUiThread { Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show() }
    }

    fun loginActivity() {
        val signInIntent = AuthenticatorActivity().intent
        startForResult.launch(signInIntent)
    }
}
