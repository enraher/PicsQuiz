package com.pics.quiz.base

import android.content.Context
import android.os.Handler
import com.pics.quiz.BuildConfig
import com.pics.quiz.repositories.PreferencesManager
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.HttpsURLConnection

/**
 * Created by enraher on 23/01/20.
 */

abstract class BaseRepository {

    fun <T> createWebservice(context: Context, clazz: Class<T>): T {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.addInterceptor { chain ->
            val token = PreferencesManager(context).authToken

            val request = chain.request().newBuilder()
                .addHeader(AUTHORIZATION, token)
                .build()
            chain.proceed(request)
        }
        okHttpClientBuilder.addInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        )
        okHttpClientBuilder.addInterceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            if (response.code() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                val mainHandler = Handler(context.mainLooper)
                mainHandler.post { userSessionExpired(context) }
            }

            response
        }

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientBuilder.build())
            .build()
            .create(clazz)
    }

    fun getErrorMessage(responseBody: ResponseBody?): String? {
        return try {
            responseBody?.string()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun userSessionExpired(context: Context) {
        if (PreferencesManager(context).authToken.isEmpty()) return
        PreferencesManager(context).clearSessionData()
        //TODO process expired session alert
    }

    companion object {
        private const val AUTHORIZATION = "Authorization"
    }
}