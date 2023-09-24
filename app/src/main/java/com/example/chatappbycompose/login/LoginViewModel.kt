package com.example.chatappbycompose.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatappbycompose.database.addUserToFirestoreDB
import com.example.chatappbycompose.database.getUserFromFirestoreDB
import com.example.chatappbycompose.model.AppUserModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel :ViewModel() {
    val emailState = mutableStateOf("")
    val emailError = mutableStateOf("")
    val passwordState = mutableStateOf("")
    val passwordError = mutableStateOf("")
    val showLoading = mutableStateOf(false)
    val message= mutableStateOf("")
    val auth = Firebase.auth
    var navigator :Navigator ?=null

    fun validateFields(): Boolean{
        if (emailState.value.isEmpty()||emailState.value.isBlank()){
            emailError.value="Email required "
            return false
        }else{
            emailError.value=""
        }
        if (passwordState.value.isEmpty()||passwordState.value.isBlank()){
            passwordError.value="Password required "
            return false
        }else{
            passwordError.value=""
        }
        return true
    }
    fun sendAuthDataToFirebase(){
        if (validateFields()){
            showLoading.value=true
            // register user to firebase Auth
            registerUserToAuth()

        }
    }

    fun registerUserToAuth(){
        auth.signInWithEmailAndPassword(emailState.value,passwordState.value)
            .addOnCompleteListener {
                showLoading.value=false
                if(it.isSuccessful){
                    // navigate to home Screen

                    //Add user to cloud firestore
                    getUserFromFirstore(it.result.user?.uid)
                }else{
                    // show error dialog
                    message.value =it.exception?.localizedMessage ?: ""

                }
            }
    }
    fun navigateToRegisterScreen(){
        navigator?.openRegisterActivity()
    }

    fun getUserFromFirstore(uid : String ?){
        showLoading.value=true
        getUserFromFirestoreDB(
            uid!!,
            onSuccessListener = {
                val appUser =it.toObject(AppUserModel::class.java)
                // callback
                navigator?.openHomeActivity()
                showLoading.value = false
            }, onFailureListener = {
                showLoading.value = false
                message.value = it.localizedMessage ?:""
            })
    }
}