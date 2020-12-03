package com.example.quiz

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://opentdb.com/"

object RetrofitClient {
    private var interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private var client = OkHttpClient.Builder()
        .addInterceptor(interceptor)

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(client.build())
        .build()

    var api: RetrofitApi = retrofit.create(RetrofitApi::class.java)
}

interface RetrofitApi {
    @GET("/api.php")
    fun getQuizAsync(
        @Query("amount") amount: Int,
        @Query("type") type: String,
        @Query("difficulty") difficulty: String
    ): Deferred<Quiz>
}