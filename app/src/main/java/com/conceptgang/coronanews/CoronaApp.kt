package com.conceptgang.coronanews

import android.app.Application
import com.conceptgang.coronanews.data.remote.CoronaClient
import com.conceptgang.coronanews.ui.repository.CoronaRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import timber.log.Timber.DebugTree
import java.util.concurrent.TimeUnit


class CoronaApp : Application() {

    private val BASE_URL = "https://newsapi.pythonanywhere.com/corona/api/v1/"

    private lateinit var _repository: CoronaRepository
    val repository: CoronaRepository get() = _repository


    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.uprootAll()
            Timber.plant(DebugTree())
        }

        val logging = HttpLoggingInterceptor()
        logging.level = (HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient
            .Builder()
            .connectTimeout(120, TimeUnit.MINUTES)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .addNetworkInterceptor { chain ->
                val request = chain.request()
                val newRequest = request.newBuilder().addHeader("Authorization", "Token 6ea60efb6b0f9be50bbf89336c2c03b1f4685be2")
                chain.proceed(newRequest.build())
            }
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val coronaclient = retrofit.create(CoronaClient::class.java)

        _repository = CoronaRepository(coronaclient)


    }
}