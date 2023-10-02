package com.example.chatappbycompose.database

import com.example.chatappbycompose.model.AppUserModel
import com.example.chatappbycompose.model.Room
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// DRY <-> Don't Repeat Yourself

fun getCollectionRef(collectionName: String): CollectionReference {
    val db = Firebase.firestore
    return db.collection(collectionName)
}

fun addUserToFirestoreDB(appUser : AppUserModel , onSuccessListener: OnSuccessListener<Void> , onFailureListener: OnFailureListener){
    /*val dp = Firebase.firestore
    val collection = dp.collection(AppUserModel.COLLECTION_NAME)*/
    //collection.document(appUser.id!!)
    getCollectionRef(AppUserModel.COLLECTION_NAME).document(appUser.id!!)
        .set(appUser)
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}
fun getUserFromFirestoreDB(uid : String ,onSuccessListener: OnSuccessListener<DocumentSnapshot> , onFailureListener: OnFailureListener){
    /*val dp = Firebase.firestore
    val collection =dp.collection(AppUserModel.COLLECTION_NAME)*/
    getCollectionRef(AppUserModel.COLLECTION_NAME).document(uid)
        .get()
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}
fun addRoomToFireStoreDB(
    room: Room,
    onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener
){
    val documentReference = getCollectionRef(Room.COLLECTION_NAME).document()
    room.roomId = documentReference.id
    documentReference
        .set(room)
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)

}