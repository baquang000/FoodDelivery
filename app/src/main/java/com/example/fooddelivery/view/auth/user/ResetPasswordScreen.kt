package com.example.fooddelivery.view.auth.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fooddelivery.R
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.data.viewmodel.user.authviewmodel.ResetPassViewModel

@Composable
fun ResetPasswordScreen(
    navController: NavController,
    resetPassViewModel: ResetPassViewModel = ResetPassViewModel()
) {
    var email by remember {
        mutableStateOf("")
    }
    var error by remember {
        mutableStateOf(false)
    }

    var isOpenDialog by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
    ) {
        HeadingResetPass(navController = navController)
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
                .padding(vertical = 12.dp)
        )
        Button(
            onClick = {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    error = true
                }else{
                    resetPassViewModel.sendLinkResetPassword(email = email)
                    isOpenDialog = true
                    email = ""
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = email.isNotEmpty()
        ) {
            Text(text = "Gửi email")
        }
        if (isOpenDialog) {
            AlertDialog(
                onDismissRequest = { isOpenDialog = false },
                title = {
                    NormalTextComponents(
                        value = stringResource(R.string.success),
                        nomalColor = Color.Black
                    )
                },
                text = {
                    Text(text = "Gửi link thành công")
                },
                confirmButton = {
                    Button(onClick = {
                        isOpenDialog = false
                    }) {
                        Text(text = stringResource(R.string.confirm))
                    }
                },
            )
        }
    }
}


@Composable
fun HeadingResetPass(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(onClick = {
            navController.navigateUp()
        }) {
            Icon(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = stringResource(
                    id = R.string.arrow
                ),
                modifier = Modifier.size(width = 24.dp, height = 24.dp)
            )
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