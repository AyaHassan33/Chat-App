package com.example.chatappbycompose.chat

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatappbycompose.database.addMessageToFirestoreDB
import com.example.chatappbycompose.database.getMessagesFromFirestoreDB
import com.example.chatappbycompose.model.DataUtils
import com.example.chatappbycompose.model.Message
import com.example.chatappbycompose.model.Room
import com.google.firebase.firestore.DocumentChange
import java.util.Date

class ChatViewModel : ViewModel() {
    var navigator:Navigator?=null
    var room: Room? = null
    val messagesListState = mutableStateOf<List<Message>>(listOf())
    val messageFieldState = mutableStateOf("")
    fun navigatorUp(){
        navigator?.navigatorUp()
    }

    fun addMessageToFireStore(){
        if (messageFieldState.value.isEmpty()||messageFieldState.value.isBlank())
            return
        val message =Message(
            content = messageFieldState.value,
            dateTime =Date().time,
            senderId = DataUtils.appUser?.id,
            senderName = DataUtils.appUser?.firstName,
            roomId = room?.roomId
        )
        addMessageToFirestoreDB(message=message, roomId = room?.roomId!!,
            onSuccessListener = {
                messageFieldState.value=""
            },
            onFailureListener = { Log.e("Tag", it.localizedMessage)})
    }
    fun getMessagesFromFirestore(){
        getMessagesFromFirestoreDB(roomId = room?.roomId!!, listener = { snapshots,e->
            if (e != null) {
                Log.e("Tag", "${e.message}")
                return@getMessagesFromFirestoreDB
            }
            val mutableList = mutableListOf<Message>()
            for (doc in snapshots!!.documentChanges) {
                when (doc.type) {
                    DocumentChange.Type.ADDED -> {
                        mutableList.add(doc.document.toObject(Message::class.java))
                    }
//                    DocumentChange.Type.MODIFIED -> Log.d("tag", "Modified city: ${doc.document.data}")
//                    DocumentChange.Type.REMOVED -> Log.d("tag", "Removed city: ${doc.document.data}")
                    else -> {}
                }
            }
            val newList = mutableListOf<Message>()
            newList.addAll(mutableList)
            newList.addAll(messagesListState.value)

            messagesListState.value = newList.toList()
        })
    }

}