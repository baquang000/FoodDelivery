package com.example.fooddelivery.data.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.data.model.Food
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FavoriteViewModel : ViewModel() {
    var favoriteFood by mutableStateOf<List<Food>>(emptyList())
    var isLoading by mutableStateOf(false)
    var loadError by mutableStateOf<String?>(null)
    var favoriteStatus by mutableStateOf<Map<Int, Boolean>>(emptyMap())

    fun saveFavoriteFood(id: Int?, isFavorited: Boolean) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null && id != null) {
            val database = FirebaseDatabase.getInstance()
            val favRef = database.getReference("favoriteFood").child(userId)
            favRef.child(id.toString()).setValue(isFavorited)
                .addOnSuccessListener {
                    favoriteStatus = favoriteStatus.toMutableMap().apply {
                        this[id] = isFavorited
                    }
                    Log.d("Success", "Favorite food saved successfully")
                }
                .addOnFailureListener { e ->
                    Log.e("Error", "Failed to save favorite food", e)
                }
        } else {
            Log.e("Error", "User ID or food ID is null. Save favorite food failed.")
        }
    }

    // Retrieve favorite food data from Firebase
    fun getFavoriteFood() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            isLoading = true
            viewModelScope.launch {
                try {
                    val favList = getFavoriteFoodIds(userId)
                    val foods = getFoodsByIds(favList)

                    favoriteFood = foods
                } catch (e: Exception) {
                    loadError = e.message
                } finally {
                    isLoading = false
                }
            }
        }
    }

    fun loadFavoriteStatus(id: Int) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            viewModelScope.launch {
                val favRef =
                    FirebaseDatabase.getInstance().getReference("favoriteFood").child(userId)
                        .child(id.toString())
                val snapshot = favRef.get().await()
                val isFavorited = snapshot.getValue(Boolean::class.java) ?: false
                favoriteStatus = favoriteStatus.toMutableMap().apply {
                    this[id] = isFavorited
                }
            }
        }
    }

    // Helper function to retrieve favorite food IDs from Firebase
    private suspend fun getFavoriteFoodIds(userId: String): List<Int> {
        val favRef = FirebaseDatabase.getInstance().getReference("favoriteFood").child(userId)
        val snapshot = favRef.get().await()
        val favoriteId = mutableListOf<Int>()
        for (childSnapshot in snapshot.children) {
            val id = childSnapshot.key?.toIntOrNull()
            val isFavorited = childSnapshot.getValue(Boolean::class.java)
            if (id != null && isFavorited == true) {
                favoriteId.add(id)
            }
        }
        return favoriteId
    }

    // Helper function to retrieve food details by IDs from Firebase
    private suspend fun getFoodsByIds(ids: List<Int>): List<Food> {
        val foods = mutableListOf<Food>()
        for (id in ids) {
            val foodRef = FirebaseDatabase.getInstance().getReference("Foods").child(id.toString())
            val snapshot = foodRef.get().await()
            val food = snapshot.getValue(Food::class.java)
            if (food != null) {
                foods.add(food)
            }
        }
        return foods
    }

}

