package com.example.chatappbycompose.model

data class Room(
    var roomId: String? = null,
    val name: String? = null,
    val description: String? = null,
    val categoryID: String? = null
){

    companion object {
        const val COLLECTION_NAME = "Room"
    }
}
