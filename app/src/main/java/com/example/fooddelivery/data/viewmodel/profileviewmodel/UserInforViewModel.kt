package com.example.fooddelivery.data.viewmodel.profileviewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class UserInforViewModel:ViewModel (){
    var name by mutableStateOf("")
    var numberphone by mutableStateOf("")
    var address by mutableStateOf("")
    var isSaving by mutableStateOf(false)
    var saveResult by mutableStateOf<Boolean?>(null)
    var isLoading by mutableStateOf(false)
    var loadError by mutableStateOf<String?>(null)

    fun saveUserData() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            isSaving = true
            val database = FirebaseDatabase.getInstance()
            val usersRef = database.getReference("users")
            val userData = mapOf(
                "name" to name,
                "numberphone" to numberphone,
                "address" to address
            )

            usersRef.child(userId).setValue(userData).addOnCompleteListener { task ->
                isSaving = false
                saveResult = task.isSuccessful
            }
        } else {
            saveResult = false
        }
    }
    fun getUserData() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            isLoading = true
            val database = FirebaseDatabase.getInstance()
            val usersRef = database.getReference("users").child(userId)

            usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        name = snapshot.child("name").getValue(String::class.java) ?: ""
                        numberphone = snapshot.child("numberphone").getValue(String::class.java) ?: ""
                        address = snapshot.child("address").getValue(String::class.java) ?: ""
                    }
                    isLoading = false
                }

                override fun onCancelled(error: DatabaseError) {
                    loadError = error.message
                    isLoading = false
                }
            })
        }
    }

}