package com.example.fooddelivery.data.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.api.RetrofitClient
import com.example.fooddelivery.data.model.CreateMessage
import com.example.fooddelivery.data.model.Message
import com.example.fooddelivery.data.model.Shop
import com.example.fooddelivery.untils.SocketManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Suppress("UNREACHABLE_CODE")
class ChatViewModel : ViewModel() {
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val message = _messages.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _isMessage = MutableStateFlow(false)


    private val _shopHistory = MutableStateFlow<List<Shop>>(emptyList())
    val shopHistory = _shopHistory.asStateFlow()

    private val _loadingUser = MutableStateFlow(false)
    val loadingUser = _loadingUser.asStateFlow()

    private val _idShop = MutableStateFlow(0)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getHistoryByUser()
        }
        SocketManager.onMessage { status ->
            _isMessage.value = status
            if (_isMessage.value) {
                viewModelScope.launch(Dispatchers.IO) {
                    getMessage(_idShop.value)
                    _isMessage.value = false
                }
            }
        }
    }


    private suspend fun getHistoryByUser() {
        _loadingUser.value = true
        try {
            _shopHistory.value =
                RetrofitClient.chatAPIService.getHistoryByUser(idUser = ID)
        } catch (e: Exception) {
            throw e
            e.printStackTrace()
        } finally {
            _loadingUser.value = false
        }
    }

    suspend fun getMessage(idShop: Int) {
        _loading.value = true
        try {
            _messages.value =
                RetrofitClient.chatAPIService.getMessages(idUser = ID, idShop = idShop)
        } catch (e: Exception) {
            throw e
            e.printStackTrace()
        } finally {
            _loading.value = false
        }
    }

    suspend fun sendMessageUser(idShop: Int, content: String) {
        val message = CreateMessage(
            idUser = ID,
            idShop = idShop,
            content = content,
            fromMe = true
        )
        try {
            RetrofitClient.chatAPIService.sendMessage(message)
        } catch (e: Exception) {
            throw e
            e.printStackTrace()
        } finally {
            SocketManager.emitMessage(true)
            getMessage(idShop)
        }
    }

    fun setIdShop(id: Int) {
        _idShop.value = id
    }
}