package com.example.ltddgk.until

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

object FirebaseUtils {

    val db = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance()

    const val COLLECTION_USER = "users" // 🔥 THÊM DÒNG NÀY
    const val STORAGE_IMAGES = "images"
}