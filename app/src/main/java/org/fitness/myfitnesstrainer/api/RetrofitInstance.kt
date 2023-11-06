package org.fitness.myfitnesstrainer.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.Interceptor.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.fitness.myfitnesstrainer.main.MainApp
import org.fitness.myfitnesstrainer.service.MyFitnessService
import org.fitness.myfitnesstrainer.service.MyFitnessServiceImp
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.IOException


val app: MainApp? = MainApp().getInstance()
val cacheSize = (5 * 1024 * 1024).toLong()
val myCache = app?.let { Cache(it.cacheDir, cacheSize) }

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val sharedPreferences = app?.getInstance()?.getSharedPreferences("MyFitnessTrainerPrefs", AppCompatActivity.MODE_PRIVATE)
        val token: String? = sharedPreferences?.getString("access_token", "55")
        Timber.i("Token from Shared Prefs %s", token)

        val newRequest: Request = chain.request().newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }
}

fun hasNetwork(context: Context): Boolean? {
    var isConnected: Boolean? = false // Initial Value
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnected)
        isConnected = true
    return isConnected
}

//"http://localhost:4000"
//"https://myfitnesstrainer-backend.onrender.com"
object RetrofitInstance {
    var TOKEN = ""

    val okHttpClient = OkHttpClient.Builder()
    .cache(myCache)
    .addInterceptor { chain ->
        var request = chain.request()
        if (app != null) {
            request = if (app.getContext()?.let { hasNetwork(it) }!!)
                request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
            else
                request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
        }
        chain.proceed(request)
    }
    .addInterceptor { chain ->
        val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${TOKEN}").build()
        chain.proceed(request)
    }
    .build()

    val api: ApiServices by lazy {
        Retrofit.Builder()
            .baseUrl("https://myfitnesstrainer-backend.onrender.com")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServices::class.java)
    }

    val service: MyFitnessService = MyFitnessServiceImp(api)
}

sealed class NetworkResult<T : Any> {
    class Success<T: Any>(val code: Int,val data: T) : NetworkResult<T>()
    class Error<T: Any>(val code: Int, val errorMsg: String?) : NetworkResult<T>()
    class Exception<T: Any>(val e: Throwable) : NetworkResult<T>()
}

interface ApiHandler {
    suspend fun <T : Any> handleApi(
        execute: suspend () -> retrofit2.Response<T>
    ): NetworkResult<T> {
        return try {
            val response = execute()
            //Log.d("response","$response")
            val body = response.body()
            //Log.d("response","$body")
            if (response.isSuccessful && body != null) {
                NetworkResult.Success(response.code(), body)
            } else {
                NetworkResult.Error(
                    code = response.code(),
                    errorMsg = response.errorBody().toString()
                )
            }
        }catch (e: HttpException){
            NetworkResult.Error(e.code(), e.message())
        }catch (e:Throwable){
            NetworkResult.Exception(e)
        }

    }
}