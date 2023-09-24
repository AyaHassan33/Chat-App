package com.example.chatappbycompose.database

import com.example.chatappbycompose.model.AppUserModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun addUserToFirestoreDB(appUser : AppUserModel , onSuccessListener: OnSuccessListener<Void> , onFailureListener: OnFailureListener){
    val dp = Firebase.firestore
    val collection = dp.collection(AppUserModel.COLLECTION_NAME)
    collection.document(appUser.id!!)
        .set(appUser)
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}
fun getUserFromFirestoreDB(uid : String ,onSuccessListener: OnSuccessListener<DocumentSnapshot> , onFailureListener: OnFailureListener){
    val dp = Firebase.firestore
    val collection =dp.collection(AppUserModel.COLLECTION_NAME)
    collection.document(uid)
        .get()
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}