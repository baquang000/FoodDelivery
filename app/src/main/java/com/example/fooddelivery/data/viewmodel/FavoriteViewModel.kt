package com.example.fooddelivery.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.CreateFavorite
import com.example.fooddelivery.data.model.GetFavorite
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel : ViewModel() {
    //favorite
    private val _favoriteStateFlow = MutableStateFlow<List<GetFavorite>>(emptyList())
    val favorite = _favoriteStateFlow.asStateFlow()
    private val _isLoadFavorite = MutableStateFlow(false)
    val isLoadFavorite = _isLoadFavorite.asStateFlow()
    private val tag = ViewModel::class.java.simpleName

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getFavoriteWithApi()
        }
    }

    suspend fun getFavoriteWithApi() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            _isLoadFavorite.value = true
            try {
                _favoriteStateFlow.value =
                    RetrofitClient.userFavoriteAPIService.getUserFavorite(userId)
            } catch (e: Exception) {
                Log.e(tag, e.message.toString())
            } finally {
                _isLoadFavorite.value = false
            }
        }
    }

    suspend fun createFavorite(idShop:String) {
        _isLoadFavorite.value = true
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val favorite = CreateFavorite(
                idShop = idShop,
                idUser = userId
            )
            try {
                RetrofitClient.userFavoriteAPIService.createUserFavorite(favorite)
            } catch (e: Exception) {
                Log.e(tag, e.message.toString())
            } finally {
                _isLoadFavorite.value = false
            }
        }
    }

    suspend fun deleteFavorite(idShop: String) {
        _favoriteStateFlow.value = _favoriteStateFlow.value.filter { it.idShop != idShop }
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            _isLoadFavorite.value = true
            try {
                RetrofitClient.userFavoriteAPIService.deleteUserFavorite(userId, idShop)
            } catch (e: Exception) {
                Log.e(tag, e.message.toString())
            } finally {
                _isLoadFavorite.value = false
            }
        }
    }
}

