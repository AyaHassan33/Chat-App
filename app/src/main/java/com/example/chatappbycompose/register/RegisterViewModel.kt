package com.example.chatappbycompose.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatappbycompose.database.addUserToFirestoreDB
import com.example.chatappbycompose.model.AppUserModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterViewModel :ViewModel() {
    val firstNameState = mutableStateOf("")
    val firstNameError = mutableStateOf("")
    val emailState = mutableStateOf("")
    val emailError = mutableStateOf("")
    val passwordState = mutableStateOf("")
    val passwordError = mutableStateOf("")
    val showLoading = mutableStateOf(false)
    val message= mutableStateOf("")
    val auth = Firebase.auth

    fun validateFields(): Boolean{
        if (firstNameState.value.isEmpty()||firstNameState.value.isBlank()){
            firstNameError.value="First name required "
            return false
        }else{
            firstNameError.value=""
        }
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
        auth.createUserWithEmailAndPassword(emailState.value,passwordState.value)
            .addOnCompleteListener {
                showLoading.value=false
                if(it.isSuccessful){
                    // navigate to home Screen

                    //Add user to cloud firestore
                    addUserToFirestore(it.result.user?.uid)
                }else{
                    // show error dialog
                    message.value =it.exception?.localizedMessage ?: ""

                }
            }
    }

    fun addUserToFirestore(uid : String ?){
        showLoading.value=true
        addUserToFirestoreDB(
            AppUserModel
                (id = uid ,firstName = firstNameState.value, email = emailState.value),
            onSuccessListener = {
                message.value ="Successful Registration"
                showLoading.value = false
            }, onFailureListener = {
                showLoading.value = false
                message.value = it.localizedMessage ?:""
            })
    }
}