package com.example.chatappbycompose.addRoom

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatappbycompose.model.Category

class AddRoomViewModel : ViewModel(){
    var titleState = mutableStateOf("")
    var titleErrorState = mutableStateOf("")
    var descriptionState = mutableStateOf("")
    var descriptionErrorState = mutableStateOf("")
    val categoriesList = Category.getCategoriesList()
    var selectedItem = mutableStateOf(categoriesList[0])
    var isExpanded = mutableStateOf(false)
    var navigator : Navigator?=null

    fun navigatorUp(){
        navigator?.navigatorUp()
    }
}