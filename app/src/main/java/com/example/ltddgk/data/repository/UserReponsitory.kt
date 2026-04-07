package com.example.ltddgk.data.repository

import android.net.Uri
import com.example.ltddgk.data.model.User
import com.example.ltddgk.until.FirebaseUtils
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.util.UUID

// Trong file UserRepository.kt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class UserRepository {
    private val collection = FirebaseUtils.db.collection(FirebaseUtils.COLLECTION_USER)

    fun getUsers() = callbackFlow<List<User>> {
        val listener = collection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            val list = snapshot?.documents?.mapNotNull {
                it.toObject(User::class.java)?.copy(id = it.id)
            } ?: emptyList()

            trySend(list)
        }
        awaitClose { listener.remove() }
    }.flowOn(Dispatchers.IO) // 🔥 Ép chạy trên luồng phụ để không đứng máy ảo

    // Thêm onComplete cho delete để UI cập nhật trạng thái
    fun deleteUser(id: String, onComplete: (Boolean) -> Unit = {}) {
        collection.document(id).delete()
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }
}