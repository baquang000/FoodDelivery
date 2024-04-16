package com.example.fooddelivery

import android.app.Application
import com.google.firebase.FirebaseApp

class FoodDelivery : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}