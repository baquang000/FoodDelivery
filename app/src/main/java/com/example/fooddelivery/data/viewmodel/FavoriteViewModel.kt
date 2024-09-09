package com.example.fooddelivery.data.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.data.model.Shop
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FavoriteViewModel : ViewModel() {
    // list shop
    private val _shopFavoriteStateFlow = MutableStateFlow<List<Shop>>(emptyList())
    val shopFovoriteStateFlow = _shopFavoriteStateFlow.asStateFlow()
    var isLoading by mutableStateOf(false)
    var loadError by mutableStateOf<String?>(null)
    private var favoriteShop by mutableStateOf<Map<String, Boolean>>(emptyMap())
    private val tag = ViewModel::class.java.simpleName
    private val _favoriteShopStateFlow = MutableStateFlow(false)
    val favoriteShopStateFlow = _favoriteShopStateFlow.asStateFlow()

    fun saveFavoriteFood(id: String, isFavorite: Boolean) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            FirebaseDatabase.getInstance().getReference("favoriteShop").child(userId)
                .child(id).setValue(isFavorite).addOnSuccessListener {
                    favoriteShop = favoriteShop.toMutableMap().apply {
                        this[id] = isFavorite
                    }
                    Log.d(tag, "Favorite shop saved successfully")
                    loadFavoriteShop(id = id)
                    getFavoriteFood()
                }
                .addOnFailureListener {
                    Log.e(tag, "Failed to save favorite shop: ${it.message}")
                }
        }
    }


    // Retrieve favorite shop data from Firebase
    fun getFavoriteFood() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            isLoading = true
            viewModelScope.launch {
                try {
                    val shopListIds = getFavoriteShopIds(userId)
                    val shops = getShopsByIds(shopListIds)
                    _shopFavoriteStateFlow.value = shops
                    Log.d(tag, "get _shopFavoriteStateFlow.value successfully")
                } catch (e: Exception) {
                    loadError = e.message
                } finally {
                    isLoading = false
                }
            }
        }
    }

    fun loadFavoriteShop(id: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            viewModelScope.launch {
                val favRef =
                    FirebaseDatabase.getInstance().getReference("favoriteShop").child(userId)
                        .child(id)
                try {
                    val snapshot = favRef.get().await()
                    val isFavorited = snapshot.getValue(Boolean::class.java) ?: false
                    _favoriteShopStateFlow.value = isFavorited
                    Log.d(tag, "Loading shop favorite successfully")
                } catch (e: Exception) {
                    Log.e(tag, "Error loading shop favorite: ${e.message}")
                }
            }
        }
    }

    // Helper function to retrieve favorite food IDs from Firebase
    private suspend fun getFavoriteShopIds(userId: String): List<String> {
        val favRef = FirebaseDatabase.getInstance().getReference("favoriteShop").child(userId)
        val snapshot = favRef.get().await()
        val favoriteId = mutableListOf<String>()
        for (childSnapshot in snapshot.children) {
            val id = childSnapshot.key
            val isFavorited = childSnapshot.getValue(Boolean::class.java)
            if (id != null && isFavorited == true) {
                favoriteId.add(id)
            }
        }
        Log.d(tag, "get favoriteId successfully")
        return favoriteId
    }

    // Helper function to retrieve food details by IDs from Firebase
    private suspend fun getShopsByIds(ids: List<String>): List<Shop> {
        val shops = mutableListOf<Shop>()
        for (id in ids) {
            val foodRef = FirebaseDatabase.getInstance().getReference("adminprofile").child(id)
            val snapshot = foodRef.get().await()
            val shop = snapshot.getValue(Shop::class.java)
            if (shop != null) {
                shops.add(shop)
            }
        }
        Log.d(tag, "get shops successfully")
        return shops
    }

}

