package com.example.fooddelivery.view.auth.user

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.fooddelivery.R
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.navigation.AuthRouteScreen
import com.example.fooddelivery.navigation.Graph
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun LoginWithPhoneNumber(
    navController: NavController
) {
    var phonenumber by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    var isLoading by remember {
        mutableStateOf(false)
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier.fillMaxSize().clickable(
            indication = null, // Remove the grey ripple effect
            interactionSource = MutableInteractionSource()
        ) {
            focusManager.clearFocus()
            keyboardController?.hide()
        },
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo_AppFood",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )
                Row(
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    NormalTextComponents(
                        value = stringResource(R.string.food_sign_up_login),
                        nomalFontsize = 45.sp,
                        nomalFontWeight = FontWeight.Bold,
                        nomalColor = Color.Black,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    NormalTextComponents(
                        value = stringResource(R.string.App_sign_up_login),
                        nomalFontsize = 45.sp,
                        nomalFontWeight = FontWeight.Normal,
                        nomalColor = Color.Black,
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                OutlinedTextField(
                    value = phonenumber, onValueChange = {
                        phonenumber = it
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    label = {
                        Text(
                            text = "Số điện thoại", style = TextStyle(
                                fontSize = 16.sp
                            )
                        )
                    },
                    textStyle = MaterialTheme.typography.titleMedium,
                    singleLine = true,
                    leadingIcon = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.flag),
                                contentDescription = "flagVN",
                                modifier = Modifier.size(36.dp,30.dp),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                text = "+84", style = MaterialTheme.typography.titleMedium.copy(
                                    color = Color.Gray
                                )
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {
                        isLoading = true
                        onSendOTP(
                            context = context,
                            phoneNumber = phonenumber,
                            navController = navController
                        ) {
                            Log.d("LoginPhone", "sending otp")
                            isLoading = false
                            navController.navigate(route = AuthRouteScreen.OtpScreen.route,
                                navOptions = NavOptions
                                    .Builder()
                                    .setLaunchSingleTop(true)
                                    .build())
                        }
                    },
                    modifier = Modifier
                        .size(width = 200.dp, height = 50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (phonenumber.length >= 6) Color.Red else Color.Gray,
                    ),
                    enabled = if (phonenumber.length >= 6) true else false
                ) {
                    Text(text = "Gửi")
                }
            }
        }
    }
}

fun onSendOTP(
    phoneNumber: String,
    context: Context,
    navController: NavController,
    onCodeSend: () -> Unit
) {
    FirebaseAuth.getInstance().setLanguageCode("vn")
    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.d("LoginPhone", "onVerificationCompleted:$credential")
            signInWithPhoneAuthCredential(
                credential = credential,
                context = context,
                navController = navController
            )
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Log.w("LoginPhone", "onVerificationFailed", e)
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {

            Log.d("LoginPhone", "onCodeSent:$verificationId")

            storedVerificationId = verificationId
            onCodeSend()
        }
    }

    val otpions = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance()).setPhoneNumber(
        "+84$phoneNumber"
    ).setTimeout(60L, TimeUnit.SECONDS).setActivity(context as Activity)
        .setCallbacks(callbacks).build()

    PhoneAuthProvider.verifyPhoneNumber(otpions)
}

fun signInWithPhoneAuthCredential(
    credential: PhoneAuthCredential,
    context: Context,
    navController: NavController
) {
    FirebaseAuth.getInstance().signInWithCredential(credential)
        .addOnCompleteListener(context as Activity) { task ->
            if (task.isSuccessful) {
                navController.navigate(route = Graph.HOMEGRAPH) {
                    popUpTo(AuthRouteScreen.LoginUser.route) { inclusive = true }
                    launchSingleTop = true
                }
            } else {
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(context, "Wrong OTP", Toast.LENGTH_SHORT).show()
                }
            }
        }
}

fun verifyPhoneNumberWithCod(
    context: Context,
    verifitionId: String,
    code: String,
    navController: NavController
) {
    val credential = PhoneAuthProvider.getCredential(verifitionId, code)
    signInWithPhoneAuthCredential(credential, context, navController)
}

var storedVerificationId = ""
/*
@Preview(
    showSystemUi = true
)
@Composable
fun LoginWithPhoneNumberPreview() {
    LoginWithPhoneNumber()
}*/
