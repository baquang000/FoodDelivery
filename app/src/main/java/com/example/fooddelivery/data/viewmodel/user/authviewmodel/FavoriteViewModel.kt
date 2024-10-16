package com.example.fooddelivery.data.viewmodel.user.authviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.CreateFavorite
import com.example.fooddelivery.data.model.GetFavorite
import com.example.fooddelivery.data.viewmodel.ID
import com.example.fooddelivery.data.viewmodel.Token
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
        if (ID != 0) {
            _isLoadFavorite.value = true
            try {
                _favoriteStateFlow.value =
                    RetrofitClient.userFavoriteAPIService.getUserFavorite(ID)
            } catch (e: Exception) {
                Log.e(tag, e.message.toString())
            } finally {
                _isLoadFavorite.value = false
            }
        }
    }

    suspend fun createFavorite(idShop: Int) {
        _isLoadFavorite.value = true
        if (ID != 0) {
            val favorite = CreateFavorite(
                idShop = idShop,
                idUser = ID
            )
            try {
                RetrofitClient.userFavoriteAPIService.createUserFavorite(Token,favorite)
            } catch (e: Exception) {
                Log.e(tag, e.message.toString())
            } finally {
                _isLoadFavorite.value = false
            }
        }
    }

    suspend fun deleteFavorite(idShop: Int) {
        _favoriteStateFlow.value = _favoriteStateFlow.value.filter { it.idShop != idShop }
        if (ID != 0) {
            _isLoadFavorite.value = true
            try {
                RetrofitClient.userFavoriteAPIService.deleteUserFavorite(Token,ID, idShop)
            } catch (e: Exception) {
                Log.e(tag, e.message.toString())
            } finally {
                _isLoadFavorite.value = false
            }
        }
    }
}

