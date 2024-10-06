package com.example.fooddelivery.untils

import com.example.fooddelivery.data.model.ErrorResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object MoshiGlobal {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val errorAdapter = moshi.adapter(ErrorResponse::class.java)

    fun errorResponse(error: String): String {
        val errorResponse = errorAdapter.fromJson(error)
        return errorResponse?.message ?: "Unknown error"
    }
}