package com.example.fooddelivery.view.auth

import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fooddelivery.R
import com.example.fooddelivery.components.ButtonComponents
import com.example.fooddelivery.components.DrawLineAndTextComponents
import com.example.fooddelivery.components.MyPasswordTextFieldComponents
import com.example.fooddelivery.components.MyTextFieldComponents
import com.example.fooddelivery.components.NormalTextComponents
import com.example.fooddelivery.data.model.LoginUIEvent
import com.example.fooddelivery.data.viewmodel.authviewmodel.LoginViewModel
import com.example.fooddelivery.navigation.AuthRouteScreen
import com.example.fooddelivery.navigation.Graph
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest
import java.util.UUID

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current
    val errorMessage by loginViewModel::errormessage
    val isFailer by loginViewModel::isFailer
    val flowNavigationHome by loginViewModel.navigationHome.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val onclick: () -> Unit = {

        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val disgest = md.digest(bytes)
        val nonce = disgest.fold("") { str, it -> str + "%02x".format(it) }

        val credentialManager = CredentialManager.create(context)
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(R.string.WEB_ID))
            .setNonce(nonce)
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption).build()

        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context
                )
                val credential = result.credential
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val googleIdToken = googleIdTokenCredential.idToken
                Log.d("LoginScreen", "LoginScreen: $googleIdToken")
                loginViewModel.googleLoginSuccess()

            } catch (e: GetCredentialException) {
                Log.d("LoginScreen", "LoginScreen: ${e.message}")
            } catch (e: GoogleIdTokenParsingException) {
                Log.d("LoginScreen", "LoginScreen: ${e.message}")
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo_AppFood",
                modifier = Modifier
                    .padding(top = 48.dp)
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
            if (isFailer) {
                NormalTextComponents(
                    value = errorMessage.toString(),
                    nomalFontsize = 16.sp,
                    nomalFontWeight = FontWeight.Normal,
                    nomalColor = Color.Red
                )
            }
            MyTextFieldComponents(
                lableText = stringResource(id = R.string.email),
                errorStatus = loginViewModel.loginUIState.value.emailError,
            ) {
                loginViewModel.onEvent(LoginUIEvent.EmailChange(it))
            }
            MyPasswordTextFieldComponents(
                lableText = stringResource(id = R.string.Pass_Word),
                errorStatus = loginViewModel.loginUIState.value.passwordError
            ) {
                loginViewModel.onEvent(LoginUIEvent.PasswordChange(it))
            }
            NormalTextComponents(
                value = stringResource(R.string.ban_quen_mat_khau),
                nomalFontsize = 16.sp,
                nomalFontWeight = FontWeight.Normal,
                nomalColor = Color.Black,
                modifier = Modifier.padding(top = 32.dp).clickable {
                    navController.navigate(route = AuthRouteScreen.ResetPass.route)
                }
            )
            ButtonComponents(
                value = stringResource(id = R.string.Dang_nhap),
                isEnable = loginViewModel.allValicationPass.value
            ) {
                loginViewModel.onEvent(LoginUIEvent.LoginButtonClicked)
            }
            if (flowNavigationHome) {
                navController.navigate(route = Graph.HOMEGRAPH) {
                    popUpTo(AuthRouteScreen.Login.route) { inclusive = true }
                }
            }
            DrawLineAndTextComponents()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 32.dp),
                horizontalArrangement = Arrangement.Center
            )
            {
                LoginFacebookButton(onAuthComplete = {
                    navController.navigate(route = Graph.HOMEGRAPH) {
                        popUpTo(AuthRouteScreen.Login.route) { inclusive = true }
                    }
                }, onAuthError = {
                    Log.e("LoginScreen", "Facebook login error", it)
                }, modifier = Modifier.weight(1f))

                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "logo_google",
                    modifier = Modifier
                        .clickable {
                            onclick()
                        }
                        .weight(1f)
                        .height(50.dp)
                )
                LoginTwitterButton(
                    onAuthComplete = {
                        navController.navigate(route = Graph.HOMEGRAPH) {
                            popUpTo(AuthRouteScreen.Login.route) { inclusive = true }
                        }
                    },
                    onAuthError = {
                        Log.e("LoginScreen", "X login error", it)
                    },
                    context = context,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                NormalTextComponents(
                    value = stringResource(R.string.ban_khong_co_tai_khoan),
                    nomalFontsize = 16.sp,
                    nomalFontWeight = FontWeight.Normal,
                    nomalColor = Color.Black,
                    modifier = Modifier.padding(end = 2.dp)
                )
                NormalTextComponents(
                    value = stringResource(id = R.string.Dang_Ky),
                    nomalFontsize = 16.sp,
                    nomalFontWeight = FontWeight.Normal,
                    nomalColor = Color.Red,
                    modifier = Modifier.clickable {
                        navController.navigate(AuthRouteScreen.SignUp.route)
                    }
                )
            }
        }
        if (loginViewModel.loginInProgress.value) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun LoginFacebookButton(
    onAuthComplete: () -> Unit,
    onAuthError: (Exception) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val loginManager = LoginManager.getInstance()
    val callbackManager = remember { CallbackManager.Factory.create() }
    val launcher = rememberLauncherForActivityResult(
        loginManager.createLogInActivityResultContract(callbackManager, null)
    ) {

    }

    DisposableEffect(Unit) {
        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onCancel() {
                // do nothing
            }

            override fun onError(error: FacebookException) {
                onAuthError(error)
            }

            override fun onSuccess(result: LoginResult) {
                scope.launch {
                    val token = result.accessToken.token
                    val credential = FacebookAuthProvider.getCredential(token)
                    val authResult = Firebase.auth.signInWithCredential(credential).await()
                    if (authResult.user != null) {
                        onAuthComplete()
                    } else {
                        onAuthError(IllegalStateException("Unable to sign in with Facebook"))
                    }
                }
            }
        })
        onDispose {
            loginManager.unregisterCallback(callbackManager)
        }
    }
    Image(
        painter = painterResource(id = R.drawable.facebook),
        contentDescription = "logo_facebook",
        modifier = modifier
            .clickable {
                launcher.launch(listOf("email", "public_profile"))
            }
            .height(50.dp)
    )
}

@Composable
fun LoginTwitterButton(
    onAuthComplete: () -> Unit,
    onAuthError: (Exception) -> Unit,
    context: Context,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val auth = Firebase.auth

    DisposableEffect(Unit) {
        onDispose { }
    }

    Image(
        painter = painterResource(id = R.drawable.twitter),
        contentDescription = "logo_twitter",
        modifier = modifier
 /*           .clickable {
                scope.launch {
                    try {
                        val provider = OAuthProvider.newBuilder("twitter.com")
                        provider.addCustomParameter("lang", "fr")
                        val pendingResultTask = auth.pendingAuthResult
                        if (pendingResultTask != null) {
                            // There's something already here! Finish the sign-in for your user.
                            pendingResultTask
                                .addOnSuccessListener {
                                    onAuthComplete()
                                }
                                .addOnFailureListener {
                                    onAuthError(it)
                                }
                        } else {
                            // There's no pending result so you need to start the sign-in flow.
                            auth
                                .startActivityForSignInWithProvider(
                                    context as ComponentActivity,
                                    provider.build()
                                )
                                .addOnSuccessListener {
                                    onAuthComplete()
                                }
                                .addOnFailureListener {
                                    onAuthError(it)
                                }
                        }
                    } catch (e: Exception) {
                        onAuthError(e)
                    }
                }
            }*/
            .height(50.dp)
    )
}

@Preview(
    showBackground = true
)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}