package com.example.retrofitbasics

import com.google.gson.GsonBuilder
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface GreetingService {
    @GET("hello/world")
    fun request(): Observable<GreetingResponse>
}

data class GreetingResponse(
    val message: String
)

class GreetingDataStore {
    fun request(): Observable<GreetingResponse> {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8882/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(GreetingService::class.java)
            .request()
    }
}