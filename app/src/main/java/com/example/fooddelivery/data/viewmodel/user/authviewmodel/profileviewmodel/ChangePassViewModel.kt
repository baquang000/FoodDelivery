package com.example.fooddelivery.data.viewmodel.user.authviewmodel.profileviewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class ChangePassViewModel : ViewModel() {
    var currentPass by mutableStateOf("")
    var newPass by mutableStateOf("")
    var reNewPass by mutableStateOf("")
    var isCurPassNull by mutableStateOf(false)
    var isNewPassequalRePass by mutableStateOf(true)
    var isClickButton by mutableStateOf(false)
    var changePassSuccess by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    fun checkRulesPassWord() {
        isClickButton = true
        isCurPassNull = currentPass.isEmpty()
        isNewPassequalRePass = newPass == reNewPass
        if (!isCurPassNull && isNewPassequalRePass && isClickButton) {
            changePass(currentPass, newPass)
        }
    }

    private fun changePass(currentPass: String, newPass: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val email = user?.email
        if (user != null && email != null) {
            val credential = EmailAuthProvider.getCredential(email, currentPass)
            user.reauthenticate(credential).addOnCompleteListener { authtask ->
                if (authtask.isSuccessful) {
                    user.updatePassword(newPass).addOnCompleteListener { updatetask ->
                        if (updatetask.isSuccessful) {
                            changePassSuccess = true
                            errorMessage = "Thay đổi mật khẩu thành công"
                        } else {
                            changePassSuccess = false
                            errorMessage =
                                updatetask.exception?.message ?: "Error changing password"
                        }
                    }
                } else {
                    changePassSuccess = false
                    errorMessage = authtask.exception?.message ?: "Re-authentication failed"
                }
            }
        } else {
            changePassSuccess = false
            errorMessage = "User not logged in"
        }
    }
}