package com.example.fooddelivery.untils

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketManager {

    private lateinit var socket: Socket

    fun initializeSocket() {
        try {
            socket = IO.socket("http://192.168.10.105:3000") // Your server URL
        } catch (e: URISyntaxException) {
            Log.e("SocketManager", "Socket Initialization Error: ${e.localizedMessage}")
        }
    }

    fun connectSocket() {
        socket.connect()
    }

    fun disconnectSocket() {
        socket.disconnect()
    }

    fun emitOrder(isOrder: Boolean) {
        if (isOrder) {
            socket.emit("isOrder", true)
        }
    }

    fun onOrderReceived(listener: (Boolean) -> Unit) {
        socket.on("isOrder") { args ->
            if (args != null && args.isNotEmpty()) {
                val isOrder = args[0] as Boolean
                // Call the listener to update the StateFlow
                listener(isOrder)
            }
        }
    }

    fun emitUpdateStatusOrder(isStatus : Boolean){
        if (isStatus) {
            socket.emit("isStatus", true)
        }
    }

    fun onUpdateStatusOrder(listener: (Boolean) -> Unit) {
        socket.on("isStatus") { args ->
            if (args != null && args.isNotEmpty()) {
                val isOrder = args[0] as Boolean
                // Call the listener to update the StateFlow
                listener(isOrder)
            }
        }
    }
}