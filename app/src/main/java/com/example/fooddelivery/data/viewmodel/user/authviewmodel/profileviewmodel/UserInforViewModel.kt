package com.example.fooddelivery.data.viewmodel.user.authviewmodel.profileviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.UserInfor
import com.example.fooddelivery.data.viewmodel.ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class UserInforViewModel : ViewModel() {

    private val _userInfor = MutableStateFlow<UserInfor?>(null)
    val userInfor = _userInfor.asStateFlow()
    private val _isLoadUserInfor = MutableStateFlow(false)
    val isLoadUserInfor = _isLoadUserInfor.asStateFlow()
    private val tag = ViewModel::class.java.simpleName

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getUser(id = ID)
        }
    }

    private suspend fun getUser(id: String) {
        _isLoadUserInfor.value = true
        try {
            _userInfor.value = RetrofitClient.userAPIService.getUser(id)
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        } finally {
            _isLoadUserInfor.value = false
        }

    }

    suspend fun saveUserData(
        name: String,
        numberPhone: String,
        address: String,
        email: String,
        dateOfBirth: String,
        id: String
    ) {
        val user = UserInfor(
            name = name,
            numberPhone = numberPhone,
            address = address,
            email = email,
            dateOfBirth = dateOfBirth,
            idUser = id
        )
        try {
            viewModelScope.launch(Dispatchers.IO) {
                RetrofitClient.userAPIService.updateUser(id, user)
            }
        } catch (e: Exception) {
            Log.e(tag, e.message.toString())
        }
    }

}