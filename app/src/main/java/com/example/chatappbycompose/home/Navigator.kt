package com.example.chatappbycompose.home

import com.example.chatappbycompose.model.Room

interface Navigator {
    fun navigateToAddRoomScreen()
    fun navigateToChatScreen(room:Room)
}