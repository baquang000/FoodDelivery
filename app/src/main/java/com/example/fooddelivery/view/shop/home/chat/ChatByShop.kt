package com.example.fooddelivery.view.shop.home.chat

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.fooddelivery.R
import com.example.fooddelivery.data.model.Message
import com.example.fooddelivery.data.viewmodel.shop.ChatByShopViewModel
import com.example.fooddelivery.navigation.ID_ARGUMENT_KEY
import com.example.fooddelivery.navigation.TITLE_ARGUMENT_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableInteractionSource")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreenByShop(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry,
    chatViewModel: ChatByShopViewModel = viewModel()
) {
    val idUser = navBackStackEntry.arguments?.getInt(ID_ARGUMENT_KEY) ?: 0
    val title = navBackStackEntry.arguments?.getString(TITLE_ARGUMENT_KEY) ?: ""
    val keyboardController = LocalSoftwareKeyboardController.current
    val localFocusManager = LocalFocusManager.current
    LaunchedEffect(key1 = Unit) {
        chatViewModel.getMessageByShop(idUser)
        chatViewModel.setIdUser(idUser)
    }
    val message by chatViewModel.message.collectAsStateWithLifecycle()
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    title,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 40.dp)
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.LightGray.copy(alpha = 0.3f)
            ),
            navigationIcon = {
                Icon(painter = painterResource(id = R.drawable.arrow),
                    contentDescription = stringResource(
                        id = R.string.arrow
                    ),
                    tint = Color.Red,
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .size(24.dp)
                        .clickable {
                            navController.navigateUp()
                        })
            },
        )
    }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .clickable(
                    indication = null, // Remove the grey ripple effect
                    interactionSource = MutableInteractionSource() // Required when setting indication to null
                ) {
                    localFocusManager.clearFocus()
                    keyboardController?.hide()
                }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxHeight()
            ) {
                items(message) { item ->
                    ChatItemByShop(modifier = Modifier, item)
                }
            }

            ChatBoxByShop(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                chatViewModel = chatViewModel,
                idUser = idUser
            )
        }
    }
}

@Composable
fun ChatBoxByShop(modifier: Modifier, chatViewModel: ChatByShopViewModel, idUser: Int) {
    var text by remember {
        mutableStateOf("")
    }
    val localFocusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    Row(
        modifier = modifier
            .clip(
                RoundedCornerShape(48f)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            value = text, onValueChange = {
                text = it
            }, shape = RoundedCornerShape(48.dp),
            colors = TextFieldDefaults.colors(
                disabledContainerColor = Color.LightGray.copy(alpha = 0.3f),
                unfocusedContainerColor = Color.LightGray,
                focusedContainerColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                focusedIndicatorColor = Color.Blue,
            )
        )
        Button(
            onClick = {
                coroutineScope.launch(Dispatchers.IO) {
                    chatViewModel.sendMessageUserByShop(idUser = idUser, content = text)
                    text = ""
                }
                localFocusManager.clearFocus()
            },
            shape = CircleShape,
            enabled = text.isNotBlank()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.paper_plane),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}


@Composable
fun ChatItemByShop(modifier: Modifier = Modifier, item: Message) {
    if (!item.fromMe) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Spacer(modifier = Modifier.weight(1f)) // Pushes content to the end
            Box(
                modifier = modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 48f,
                            topEnd = 48f,
                            bottomStart = 48f,
                            bottomEnd = 0f
                        )
                    )
                    .background(color = Color.LightGray.copy(0.2f))
                    .padding(16.dp)
            ) {
                Text(
                    text = item.content,
                )
            }
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Box(
                modifier = modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 48f,
                            topEnd = 48f,
                            bottomStart = 0f,
                            bottomEnd = 48f
                        )
                    )
                    .background(color = Color.LightGray.copy(0.2f))
                    .padding(16.dp)
            ) {
                Text(
                    text = item.content,
                )
            }
            Spacer(modifier = Modifier.weight(1f)) // Pushes content to the start
        }
    }

}

//@Preview(
//    showSystemUi = true
//)
//@Composable
//fun ChatPrevew() {
//    ChatScreen()
//
//}