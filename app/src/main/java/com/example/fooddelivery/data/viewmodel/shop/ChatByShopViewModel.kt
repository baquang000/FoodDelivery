package com.example.fooddelivery.data.viewmodel.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.CreateMessage
import com.example.fooddelivery.data.model.GetUser
import com.example.fooddelivery.data.model.Message
import com.example.fooddelivery.data.viewmodel.ID
import com.example.fooddelivery.data.viewmodel.Token
import com.example.fooddelivery.untils.SocketManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Suppress("UNREACHABLE_CODE")
class ChatByShopViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val message = _messages.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _isMessage = MutableStateFlow(false)

    private val _idUser = MutableStateFlow(0)

    private val _userHistory = MutableStateFlow<List<GetUser>>(emptyList())
    val userHistory = _userHistory.asStateFlow()

    private val _loadingUser = MutableStateFlow(false)
    val loadingUser = _loadingUser.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getHistoryByShop()
        }
        SocketManager.onMessage { status ->
            _isMessage.value = status
            if (_isMessage.value) {
                viewModelScope.launch(Dispatchers.IO) {
                    getMessageByShop(_idUser.value)
                    _isMessage.value = false
                }
            }
        }
    }

    private suspend fun getHistoryByShop() {
        _loadingUser.value = true
        try {
            _userHistory.value =
                RetrofitClient.chatAPIService.getHistoryByShop(idShop = ID)
        } catch (e: Exception) {
            throw e
            e.printStackTrace()
        } finally {
            _loadingUser.value = false
        }
    }

    suspend fun getMessageByShop(idUser: Int) {
        _loading.value = true
        try {
            _messages.value =
                RetrofitClient.chatAPIService.getMessages(idUser = idUser, idShop = ID)
        } catch (e: Exception) {
            throw e
            e.printStackTrace()
        } finally {
            _loading.value = false
        }
    }

    suspend fun sendMessageUserByShop(idUser: Int, content: String) {
        val message = CreateMessage(
            idUser = idUser,
            idShop = ID,
            content = content,
            fromMe = false
        )
        try {
            RetrofitClient.chatAPIService.sendMessage(Token,message)
        } catch (e: Exception) {
            throw e
            e.printStackTrace()
        } finally {
            SocketManager.emitMessage(true)
            getMessageByShop(idUser)
        }
    }

    fun setIdUser(id: Int) {
        _idUser.value = id
    }
}