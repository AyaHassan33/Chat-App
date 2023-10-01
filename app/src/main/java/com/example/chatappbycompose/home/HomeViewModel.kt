package com.example.chatappbycompose.home

import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    var navigator : Navigator ?=null


    fun navigateToAddRoomScreen(){
        navigator?.navigateToAddRoomScreen()
    }
}