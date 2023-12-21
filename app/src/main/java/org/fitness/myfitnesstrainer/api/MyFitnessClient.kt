package org.fitness.myfitnesstrainer.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.fitness.myfitnesstrainer.MyFitnessApp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val app: MyFitnessApp? = MyFitnessApp().getInstance()
const val cacheSize = (5 * 1024 * 1024).toLong()
val myCache = app?.let { Cache(it.cacheDir, cacheSize) }

fun hasNetwork(context: Context): Boolean? {
    var isConnected: Boolean? = false // Initial Value
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnected)
        isConnected = true
    return isConnected
}

//"http://localhost:4000"
//"https://myfitnesstrainer-backend.onrender.com"
object MyFitnessClient {
    var TOKEN = ""

    private val okHttpClient = OkHttpClient.Builder()
        .cache(myCache)
        .addInterceptor { chain ->
            var request = chain.request()
            if (app != null) {
                request = if (app.getContext()?.let { hasNetwork(it) }!!)
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                    ).build()
            }
            chain.proceed(request)
        }
        .addInterceptor { chain ->
            val request =
                chain.request().newBuilder().addHeader("Authorization", "Bearer $TOKEN").build()
            chain.proceed(request)
        }
        .build()

    private val api: ApiServices by lazy {
        Retrofit.Builder()
            .baseUrl("https://myfitnesstrainer-backend.onrender.com")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServices::class.java)
    }

    val service: MyFitnessService = MyFitnessServiceImp(api)
}

