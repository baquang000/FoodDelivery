package com.example.fooddelivery.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.10.105:3000"
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val okHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory()).build()
    val foodAPIService: FoodAPIService by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).client(
                okHttpClient
            ).build().create(FoodAPIService::class.java)
    }
    val shopAPIService: ShopAPIService by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).client(
                okHttpClient
            ).build().create(ShopAPIService::class.java)
    }
    val userAPIService: UserAPIService by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).client(
                okHttpClient
            ).build().create(UserAPIService::class.java)
    }
    val orderAPIService: OrderAPIService by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).client(
                okHttpClient
            ).build().create(OrderAPIService::class.java)
    }
    val orderDetailAPIService: OrderDetailAPIService by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).client(
                okHttpClient
            ).build().create(OrderDetailAPIService::class.java)
    }
    val userFavoriteAPIService: UserFavoriteAPIService by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).client(
                okHttpClient
            ).build().create(UserFavoriteAPIService::class.java)
    }
}