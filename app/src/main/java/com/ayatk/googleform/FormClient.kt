package com.ayatk.googleform

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object FormClient {
  val service: FormService = Retrofit
    .Builder()
    .client(OkHttpClient.Builder().build())
    .baseUrl("https://docs.google.com/")
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(FormService::class.java)
}
