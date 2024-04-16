package com.example.fooddelivery.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fooddelivery.R
import com.example.fooddelivery.components.ButtonComponents
import com.example.fooddelivery.data.viewmodel.SignupViewModel

@Composable
fun HomeScreen(navController: NavController, signupViewModel: SignupViewModel = viewModel()) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = stringResource(R.string.home))
            ButtonComponents(
                value = stringResource(R.string.log_out),
                isEnable = true
            ) {
                signupViewModel.logout()
            }
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        navController = rememberNavController()
    )
}