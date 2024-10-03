package com.example.fooddelivery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.fooddelivery.navigation.nav_graph.RootNavGraph
import com.example.fooddelivery.ui.theme.FoodDeliveryTheme
import com.example.fooddelivery.untils.SocketManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodDeliveryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
        SocketManager.initializeSocket()
        SocketManager.connectSocket()
    }

    override fun onDestroy() {
        SocketManager.disconnectSocket()
        super.onDestroy()
    }
}

@Composable
fun MyApp() {
    RootNavGraph()
}

