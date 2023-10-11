package com.example.clipboardmanager.util

import com.example.clipboardmanager.data.ClipboardItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

val auth = FirebaseAuth.getInstance()
val currentUser = auth.currentUser
val currentUserId = currentUser?.uid

val database = FirebaseDatabase.getInstance()
//val clipboardItemsRef = database.getReference("clipboardItems").child(currentUser!!.uid)
val clipboardItemsRef = database.getReference("clipboardItems")




fun saveClipboardItem(clipboardItem: ClipboardItem) {
    currentUserId?.let { uid ->
        val timestamp = System.currentTimeMillis()
        val newItemRef = database.getReference("clipboardItems/$uid/$timestamp")
        newItemRef.setValue(clipboardItem)
    }
}




fun DataSnapshot.toClipboardItem(): ClipboardItem? {
    return try {
        val text = child("text").getValue(String::class.java)
        val timestamp = child("timestamp").getValue(Long::class.java)
        ClipboardItem(text.orEmpty(), timestamp ?: 0)
    } catch (e: Exception) {
        null
    }
}


fun fetchClipboardItems(onSuccess: (List<ClipboardItem>) -> Unit) {
    val clipboardItemsRef = database.getReference("clipboardItems").child(currentUser!!.uid)

    clipboardItemsRef.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val clipboardItems = dataSnapshot.children.mapNotNull { snapshot ->
                snapshot.toClipboardItem()
            }
            onSuccess(clipboardItems)
        }
        override fun onCancelled(error: DatabaseError) {
            // Handle the error here
        }
    })
}





