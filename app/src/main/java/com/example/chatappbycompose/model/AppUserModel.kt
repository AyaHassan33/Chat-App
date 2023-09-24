package com.example.chatappbycompose.model

data class AppUserModel(
    val id : String ? =null,
    val firstName:String ?=null,
    val email : String ?=null
){
    companion object{
       const val COLLECTION_NAME ="Users"
    }
}
