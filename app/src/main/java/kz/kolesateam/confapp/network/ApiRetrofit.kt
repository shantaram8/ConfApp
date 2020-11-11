package kz.kolesateam.confapp.network

import kz.kolesateam.confapp.events.data.ApiClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

private const val API_BASE_URL = "http://37.143.8.68:2020"

val apiRetrofit: Retrofit = Retrofit.Builder()
    .baseUrl(API_BASE_URL)
    .addConverterFactory(JacksonConverterFactory.create())
    .build()

val apiClient: ApiClient = apiRetrofit.create(ApiClient::class.java)
