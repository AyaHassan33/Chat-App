package com.example.chatappbycompose.splash

import androidx.lifecycle.ViewModel
import com.example.chatappbycompose.database.getUserFromFirestoreDB
import com.example.chatappbycompose.model.AppUserModel
import com.example.chatappbycompose.model.DataUtils
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashViewModel : ViewModel() {

    var navigator : Navigator?=null
    val auth = Firebase.auth
    fun isCurrentUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun navigate() {
        if (isCurrentUserLoggedIn()) {
            // get User Data From Firestore

            getUserFromFirestoreDB(auth.currentUser?.uid!!, onSuccessListener = {
                DataUtils.appUser = it.toObject(AppUserModel::class.java)
                DataUtils.firebaseUser = auth.currentUser
                // Navigate To Home
                navigator?.navigateToHomeScreen()
            }, onFailureListener = {
                // Navigate To LogIn
                navigator?.navigateToLoginScreen()
            })




        } else {
            // navigate to login Screen
            navigator?.navigateToLoginScreen()

        }

    }

}