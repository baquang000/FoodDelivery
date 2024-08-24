package com.example.fooddelivery.data.viewmodel.authviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ResetPassViewModel : ViewModel() {
    private val tag = ResetPassViewModel::class.simpleName
    fun sendLinkResetPassword(email: String) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(tag, "sendLinkResetPassword: success")
            } else {
                Log.e(tag, task.exception.toString())
            }
        }
    }
}