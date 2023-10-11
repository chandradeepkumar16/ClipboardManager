package com.example.clipboardmanager.util

import android.util.Log
import com.example.clipboardmanager.data.ClipboardItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

val auth = FirebaseAuth.getInstance()
val currentUser = auth.currentUser
val uid = currentUser!!.uid

val database = FirebaseDatabase.getInstance()

fun saveClipboardItem(clipboardItem: ClipboardItem) {

    val clipboardItemsRef = database.getReference("clipboardItems").child(currentUser!!.uid)

    clipboardItemsRef.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val clipboardItems = dataSnapshot.children.mapNotNull { snapshot ->
                snapshot.toClipboardItem()
            }

            if (clipboardItems.any { it.text == clipboardItem.text }) {
                Log.d("check", "Already saved in the database ")
            } else {
                val newItemRef = clipboardItemsRef.push()
                newItemRef.setValue(clipboardItem)
            }
        }

        override fun onCancelled(error: DatabaseError) {
        }
    })
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
        }
    })
}





