package com.example.fooddelivery.data.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ProfileViewModel:ViewModel() {
    fun logout(){
        FirebaseAuth.getInstance().signOut()
        ID = 0
    }
}