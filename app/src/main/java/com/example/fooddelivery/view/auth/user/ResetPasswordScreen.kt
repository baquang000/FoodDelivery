package com.example.fooddelivery.view.auth.user

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fooddelivery.R
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.data.viewmodel.ResetPassViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun ResetPasswordScreen(
    navController: NavController,
    resetPassViewModel: ResetPassViewModel = viewModel()
) {
    var email by remember {
        mutableStateOf("")
    }
    var error by remember {
        mutableStateOf(false)
    }
    val isLoad by resetPassViewModel.isLoad.collectAsState()
    val isSuccess by resetPassViewModel.isSuccess.collectAsState()
    val errorMessage by resetPassViewModel.errorMessage.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.forget_pass),
                        color = Color.White,
                        modifier = Modifier.padding(start = 70.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Red.copy(alpha = 0.8f)
                ),
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow),
                        contentDescription = stringResource(
                            id = R.string.arrow
                        ),
                        tint = Color.White,
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(24.dp)
                            .clickable {
                                navController.navigateUp()
                            }
                    )
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            if (isLoad) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
                    .clickable(
                        indication = null, // Remove the grey ripple effect
                        interactionSource = MutableInteractionSource()
                    ) {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    },
            ) {
                if (error) {
                    Text(
                        text = "Email không hợp lệ",
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = MaterialTheme.colorScheme.error
                        )
                    )
                }
                OutlinedTextField(
                    value = email, onValueChange = {
                        email = it
                    },
                    label = {
                        Text(text = "Nhập email")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    )
                )
                Button(
                    onClick = {
                        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            error = true
                        } else {
                            coroutineScope.launch {
                                resetPassViewModel.sendLinkResetPassword(email = email)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = email.isNotEmpty()
                ) {
                    Text(text = "Gửi email")
                }
                if (isSuccess == true) {
                    Text(
                        text = "Gửi email thành công", fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Green,
                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 12.dp)
                    )
                    email = ""
                } else if (isSuccess == false) {
                    NormalTextComponents(
                        value = errorMessage.toString(),
                        nomalFontsize = 16.sp,
                        nomalFontWeight = FontWeight.Normal,
                        nomalColor = Color.Red,
                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 12.dp)
                    )
                }
            }
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun ResetPasswordScreenPreview() {
    ResetPasswordScreen(navController = NavController(LocalContext.current))
}